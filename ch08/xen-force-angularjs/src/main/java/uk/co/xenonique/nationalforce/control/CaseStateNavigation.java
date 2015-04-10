package uk.co.xenonique.nationalforce.control;

/**
 * The type CaseStateNavigation
 *
 * @author Peter Pilgrim
 */
@FunctionalInterface
public interface CaseStateNavigation {
    CaseState apply( BasicStateMachine fsm, CaseState currentState );
}
