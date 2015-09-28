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
 * The type NavElement
 *
 * @author Peter Pilgrim
 */
public class NavElement {
    private String name;
    private String title;
    private String style;
    private String editLink;
    private boolean visited;

    public NavElement(String name) {
        this(name, "", "index.xhtml");
    }

    public NavElement(String name, String style, String editLink) {
        this(name, name, "", editLink, false);
    }

    public NavElement(String name, String title, String style, String editLink) {
        this(name, title, style, editLink, false);
    }

    public NavElement(String name, String title, String style, String editLink, boolean visited) {
        this.name = name;
        this.title = title;
        this.style = style;
        this.editLink = editLink;
        this.visited = visited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getEditLink() {
        return editLink;
    }

    public void setEditLink(String editLink) {
        this.editLink = editLink;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

