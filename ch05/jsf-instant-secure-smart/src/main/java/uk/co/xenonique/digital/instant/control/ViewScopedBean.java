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

package uk.co.xenonique.digital.instant.control;

/**
 * The type ViewScopedBean
 *
 * @author Peter Pilgrim (peter)
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Peter Pilgrim
 * @author John Yeary
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class ViewScopedBean implements Serializable {

    private static final long serialVersionUID = 3152248670694285690L;
    private List<String> names;

    public ViewScopedBean() {
        names = new ArrayList<String>();
    }

    @PostConstruct
    private void initialize() {
        names.add("John");
        names.add("Ethan");
        names.add("Sean");
        names.add("Patty");
        names.add("Java");
        names.add("Duke");
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void delete(String name) {
        names.remove(name);
    }
}