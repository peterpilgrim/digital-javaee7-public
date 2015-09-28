/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package uk.co.xenonique.nationalforce.control;

import java.util.*;

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
        final CaseState nextState = lambda.apply(this,currentState);
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
        final Set<CaseState> states = getProbableNestStates();
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

    public static CaseState retrieveCurrentState(String stateName) {
        stateName = stateName.trim();
        if ( stateName.startsWith("STATE:[") )
            stateName = stateName.substring(7);
        if ( stateName.endsWith("]"))
            stateName = stateName.substring(0, stateName.length() - 1);
        if ( "Start".equals(stateName))
            return FSM_START;
        else if ( "End".equals(stateName))
            return FSM_END;
        else if ( "Reviewing".equals(stateName))
            return FSM_REVIEWING;
        else if ( "Decision".equals(stateName))
            return FSM_DECISION;
        else if ( "Accepted".equals(stateName))
            return FSM_ACCEPTED;
        else if ( "Rejected".equals(stateName))
            return FSM_REJECTED;

        throw new RuntimeException("Could not find matching state for name: "+stateName);
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
            return "STATE:["+name+"]";
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
