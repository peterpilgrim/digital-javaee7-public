package uk.co.xenonique.digital.product.control;

/**
 * The type NavigatableController
 *
 * @author Peter Pilgrim (peter)
 */
public interface NavigatableController {
    String computeEditView(int index);

    SmartNavigation getNavigation();
}
