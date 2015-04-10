package uk.co.xenonique.nationalforce.control;

import java.util.*;
import java.util.function.Supplier;

/**
 * The type BasicStateMachine
 *
 * @author Peter Pilgrim
 */
public class BasicStateMachine {
    public final static CaseState FSM_START         = new StartState();
    public final static CaseState FSM_END           = new EndState();
    public final static CaseState FSM_REVIEWING     = new ReviewingState();
    public final static CaseState FSM_DECISION      = new DecisionState();
    public final static CaseState FSM_ACCEPTED      = new AcceptedState();
    public final static CaseState FSM_REJECTED      = new RejectedState();

    private CaseState currentState;

    public final static Set<CaseState> EMPTY_STATES_SET = new HashSet<>();

    private Map<CaseState, Set<CaseState>> stateToStatesMap = new HashMap<>();

    public BasicStateMachine() {
        stateToStatesMap.put(FSM_END, EMPTY_STATES_SET );
        stateToStatesMap.put(FSM_START, new HashSet<>( Arrays.asList( FSM_REVIEWING) ));
        stateToStatesMap.put(FSM_REVIEWING, new HashSet<>( Arrays.asList( FSM_DECISION) ));
        stateToStatesMap.put(FSM_DECISION, new HashSet<>( Arrays.asList( FSM_ACCEPTED, FSM_REJECTED) ));
        stateToStatesMap.put(FSM_ACCEPTED, EMPTY_STATES_SET);
        stateToStatesMap.put(FSM_REJECTED, new HashSet<>( Arrays.asList( FSM_REVIEWING, FSM_END) ));
    }

    public void start() {
        setCurrentState(FSM_START);
    }

    public CaseState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CaseState currentState) {
        this.currentState = currentState;
    }

    public void moveSelectedNextState( CaseStateNavigation lambda ) {
        CaseState nextState = lambda.apply(this,currentState);
        if ( !getProbableNestStates().contains(nextState) ) {
            throw new IllegalStateException("Illegal possible destination navigation from current state: "+currentState+" for the next state: "+nextState);
        }
        else {
            setCurrentState(nextState);
        }
    }

    public Set<CaseState> getProbableNestStates() {
        if ( currentState == null) {
            throw new IllegalStateException("Finite state machine not started.");
        }
        if ( stateToStatesMap.containsKey(currentState))
            return stateToStatesMap.get(currentState);
        else
            return EMPTY_STATES_SET;
    }

    public boolean moveNextState() {
        Set<CaseState> states = getProbableNestStates();
        if ( states.isEmpty())
            return false;
        else {
            if ( states.size() > 1 )
                throw new IllegalStateException("there are two more destination states for current state: "+currentState);
            else {
                setCurrentState(states.iterator().next());
                return true;
            }
        }
    }

    public abstract static class AbstractState implements CaseState {
        final private String name;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        AbstractState() {
            name = getClass().getSimpleName().replace("State","");
        }

        @Override
        public boolean isEndState() {
            return false;
        }

        @Override
        public String toString() {
            return "STATE: "+name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AbstractState)) return false;
            AbstractState that = (AbstractState) o;
            return !(name != null ? !name.equals(that.name) : that.name != null);
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }

    public static class StartState extends AbstractState {
    }

    public static class EndState extends AbstractState {
        @Override
        public boolean isEndState() {
            return true;
        }
    }

    public static class ReviewingState extends AbstractState {
    }

    public static class DecisionState extends AbstractState {
    }

    public static class AcceptedState extends AbstractState {
        @Override
        public boolean isEndState() {
            return true;
        }
    }

    public static class RejectedState extends AbstractState {
    }
}
