package uk.co.xenonique.nationalforce.control;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.*;

import static uk.co.xenonique.nationalforce.control.BasicStateMachine.*;

/**
 * Verifies the operation of the BasicStateMachineTest
 *
 * @author Peter Pilgrim
 */
public class BasicStateMachineTest {

    @Test
    public void flow_path_from_start_to_accepted() {
        final BasicStateMachine machine = new BasicStateMachine();
        machine.start();
        assertThat(machine.getCurrentState(), is(FSM_START));
        assertThat(machine.getCurrentState().isEndState(), is(false));
        assertThat(machine.getProbableNestStates(), is(new HashSet<CaseState>(Arrays.asList(FSM_REVIEWING))));

        machine.moveNextState();

        assertThat(machine.getCurrentState(), is(FSM_REVIEWING));
        assertThat(machine.getCurrentState().isEndState(), is(false));
        assertThat(machine.getProbableNestStates(), is(new HashSet<CaseState>(Arrays.asList(FSM_DECISION))));

        machine.moveNextState();
        assertThat(machine.getCurrentState(), is(FSM_DECISION));
        assertThat(machine.getCurrentState().isEndState(), is(false));
        assertThat(machine.getProbableNestStates(), is(new HashSet<CaseState>(Arrays.asList(FSM_ACCEPTED, FSM_REJECTED))));

        machine.moveSelectedNextState((fsm, state) -> FSM_ACCEPTED);

        assertThat(machine.getCurrentState(), is(FSM_ACCEPTED));
        assertThat(machine.getCurrentState().isEndState(), is(true));
        assertThat(machine.getProbableNestStates().isEmpty(), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void flow_path_from_start_to_illegal_state_navigation() {
        final BasicStateMachine machine = new BasicStateMachine();
        machine.start();
        assertThat(machine.getCurrentState(), is(FSM_START));

        machine.moveNextState();
        assertThat(machine.getCurrentState(), is(FSM_REVIEWING));

        machine.moveNextState();
        assertThat(machine.getCurrentState(), is(FSM_DECISION));

        machine.moveSelectedNextState((fsm, state) -> FSM_REVIEWING);
    }

    @Test
    public void flow_path_from_start_to_rejected_to_reviewing_again() {
        final BasicStateMachine machine = new BasicStateMachine();
        machine.start();
        assertThat(machine.getCurrentState(), is(FSM_START));

        machine.moveNextState();
        assertThat(machine.getCurrentState(), is(FSM_REVIEWING));

        machine.moveNextState();
        assertThat(machine.getCurrentState(), is(FSM_DECISION));

        machine.moveSelectedNextState((fsm, state) -> FSM_REJECTED);
        assertThat(machine.getCurrentState(), is(FSM_REJECTED));
        assertThat(machine.getCurrentState().isEndState(), is(false));
        assertThat(machine.getProbableNestStates().isEmpty(), is(false));

        machine.moveSelectedNextState((fsm, state) -> FSM_REVIEWING);
        assertThat(machine.getCurrentState(), is(FSM_REVIEWING));
    }


    @Test
    public void flow_path_from_start_to_rejected_to_terminating() {
        BasicStateMachine machine = new BasicStateMachine();
        machine.start();
        assertThat(machine.getCurrentState(), is(FSM_START));

        machine.moveNextState();
        assertThat(machine.getCurrentState(), is(FSM_REVIEWING));

        machine.moveNextState();
        assertThat(machine.getCurrentState(), is(FSM_DECISION));

        machine.moveSelectedNextState((fsm, state) -> FSM_REJECTED);
        assertThat(machine.getCurrentState(), is(FSM_REJECTED));
        assertThat(machine.getCurrentState().isEndState(), is(false));
        assertThat(machine.getProbableNestStates().isEmpty(), is(false));

        machine.moveSelectedNextState((fsm, state) -> FSM_END);
        assertThat(machine.getCurrentState(), is(FSM_END));
        assertThat(machine.getCurrentState().isEndState(), is(true));
        assertThat(machine.getProbableNestStates().isEmpty(), is(true));
    }
}
