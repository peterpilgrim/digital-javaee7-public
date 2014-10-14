package uk.co.xenonique.digital.instant.control;

import java.util.*;

/**
 * The type SmartNavigation
 *
 * @author Peter Pilgrim
 */
public class SmartNavigation {

    public void setVisitedRange(int n, int p) {
        for ( int k=0; k< elements.size(); ++k ) {
            elements.get(k).setVisited((k >= n && k <= p));
        }
    }

    private List<NavElement> elements = new ArrayList<>();

    public SmartNavigation(List<String> names) {
        for ( String name: names) {
            elements.add(new NavElement(name)) ;
        }
    }

    public SmartNavigation(Collection<NavElement> elements) {
        for ( NavElement element: elements) {
            this.elements.add(element) ;
        }
    }

    public int size() {
        return elements.size();
    }

    public List<NavElement> toList() {
        return new ArrayList<>(elements);
    }

    public NavElement get(int i) {
        return elements.get(i);
    }

    public List<NavElement> getElements() {
        return elements;
    }
}
