package uk.co.xenonique.digital.instant.control;

/**
 * The type NavigatableController
 *
 * @author Peter Pilgrim (peter)
 */
public interface NavigatableController {
    String computeEditView(int index);

    SmartNavigation getNavigation();
}
