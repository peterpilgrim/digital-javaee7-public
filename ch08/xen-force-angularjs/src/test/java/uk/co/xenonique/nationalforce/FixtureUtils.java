/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013,2014 by Peter Pilgrim, Addiscombe, Surrey, XeNoNiQUe UK
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

package uk.co.xenonique.nationalforce;

import uk.co.xenonique.nationalforce.entity.CaseRecord;
import uk.co.xenonique.nationalforce.entity.Task;
import uk.co.xenonique.nationalforce.init.DemoDataConfigurator;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import static uk.co.xenonique.nationalforce.control.BasicStateMachine.FSM_START;

/**
 * The type FixtureUtils
 *
 * @author Peter Pilgrim (peter)
 */
public final class FixtureUtils {
    private static Random rnd = new Random(System.currentTimeMillis());

    public static CaseRecord createCaseRecordAndTasks( int taskCount ) {
        return createCaseRecordAndTasks(
                "F", "Unit", "Xenonique" + Integer.toString((int) (1000 + rnd.nextDouble() * 9000.0)), taskCount);
    }

    public static CaseRecord createCaseRecordAndTasks( String sex, String firstName, String lastName, int taskCount ) {
        final CaseRecord caseRecord = new CaseRecord();

        caseRecord.setSex(sex);
        caseRecord.setFirstName(firstName);
        caseRecord.setLastName(lastName);

        final Date dateOfBirth = DemoDataConfigurator.getRandomDateOfBirth();
        final Date expirationDate = DemoDataConfigurator.getFutureRandomDate( new Date(), 31, 10 );

        caseRecord.setCountry("Australia");
        caseRecord.setPassportNo(Long.toString((long)(Math.random() * 9E8 + 1E8)));
        caseRecord.setDateOfBirth(dateOfBirth);
        caseRecord.setExpirationDate(expirationDate);
        caseRecord.setCurrentState( FSM_START.toString() );

        for ( int j=0; j<taskCount; ++j) {
            Date targetDate = null;
            if ( j > 0 ) {
                targetDate = DateUtils.asDate( LocalDate.now().plusDays((long) (3 + Math.random() * 14)) );
            }
            final Task task = new Task("task"+(j+1)*2, targetDate, false );
            caseRecord.addTask(task);
            task.setCaseRecord(caseRecord);
        }
        return caseRecord;
    }

}
