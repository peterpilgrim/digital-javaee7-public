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

package uk.co.xenonique.digital.flows.control;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type DigitalFlow
 *
 * @author Peter Pilgrim
 */
@Named
@FlowScoped("digitalFlow")
public class DigitalFlow implements Serializable {

    public String debugClassName() {
        return this.getClass().getSimpleName();
    }

    public String gotoPage1() {
        return "digitalFlow.xhtml";
    }

    public String gotoPage2() {
        return "digitalFlow-p2.xhtml";
    }

    public String gotoPage3() {
        return "digitalFlow-p3.xhtml";
    }

    public String gotoPage4() {
        return "digitalFlow-p4.xhtml";
    }

    public String gotoEndFlow() {
        return "/digitalFlow-return.xhtml";
    }

}
