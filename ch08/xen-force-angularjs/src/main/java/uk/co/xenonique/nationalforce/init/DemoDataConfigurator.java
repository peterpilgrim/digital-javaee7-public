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

package uk.co.xenonique.nationalforce.init;

import uk.co.xenonique.nationalforce.DateUtils;
import uk.co.xenonique.nationalforce.boundary.CaseRecordTaskService;
import uk.co.xenonique.nationalforce.entity.CaseRecord;
import uk.co.xenonique.nationalforce.entity.Task;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.time.LocalDate;
import java.util.Date;

import static uk.co.xenonique.nationalforce.control.BasicStateMachine.FSM_START;
/**
 * The type DemoDataConfigurator
 *
 * @author Peter Pilgrim
 */
@Startup
@Singleton
public class DemoDataConfigurator {
    @EJB
    CaseRecordTaskService caseRecordTaskService;

    @EJB
    PopulationHelper populationHelper;

    @Resource
    TimerService timerService;

    private boolean initialized = false;

    public static Date getFutureRandomDate() {
        return getFutureRandomDate(new Date(), 7, 0);
    }

    public static Date getFutureRandomDate( Date now, int daysIntoFuture, int yearsInToFuture) {
        final LocalDate localDate = LocalDate.now()
                .plusDays((long) (Math.random() * daysIntoFuture))
                .plusYears((long) (Math.random() * yearsInToFuture)).plusDays(1);
        return DateUtils.asDate(localDate);
    }

    public static Date getRandomDateOfBirth() {
        final LocalDate localDate = LocalDate.now()
                .minusDays(1)
                .minusDays((long) (Math.random() * 365))
                .minusYears((long) (Math.random() * 90 + 18));
        return DateUtils.asDate(localDate);
    }

    @PostConstruct
    public void initialise() {
        // FIXME: GlassFish 4.1 / Payara - Get around the issues of initialisation. It appears the transaction manager and entity manager are not ready by the time a singleton starts up! We use a EJB timer workaround.
        System.out.printf("***** %s.initialise() caseRecordTaskService=%s, timerService=%s\n", getClass().getSimpleName(), caseRecordTaskService, timerService);
        final ScheduleExpression expression = new ScheduleExpression().second("*/5").minute("*").hour("*");
        timerService.createCalendarTimer(expression);
    }

    @Timeout
    public void createInitialProjectData(Timer timer) {
        if (initialized) {
            return;
        }
        initialized = true;

        System.out.printf("***** %s.createInitialProjectData() caseRecordTaskService=%s, initialized=%s, timer=%s\n",
                getClass().getSimpleName(), caseRecordTaskService, initialized, timer);

        populationHelper.saveCountries();

        createPersonFemale();
        createPersonMale();

        for (int j=0; j<5; ++j)
            createRandomPerson();
    }

    private void createPersonMale() {
        final Date dateOfBirth = getRandomDateOfBirth();
        final Date expirationDate = getFutureRandomDate( new Date(), 31, 10 );

        final CaseRecord caseRecord1 = new CaseRecord();
        caseRecord1.setFirstName("Frederick");
        caseRecord1.setLastName("Hervogstein");
        caseRecord1.setSex("M");
        caseRecord1.setCountry("AUS");
        caseRecord1.setPassportNo("819360419312");
        caseRecord1.setDateOfBirth(dateOfBirth);
        caseRecord1.setExpirationDate(expirationDate);
        caseRecord1.setCurrentState( FSM_START.toString() );

        final Date p = getFutureRandomDate( new Date(), 4, 0 );
        final Date q = getFutureRandomDate( p, 8, 0 );
        final Date r = getFutureRandomDate( q, 12, 0 );

        caseRecord1.addTask(new Task("Allocate", getFutureRandomDate(new Date(), 10, 0), true ));
        caseRecord1.addTask(new Task("Criminal index check", p, false ));
        caseRecord1.addTask(new Task("List of business fraud index", q, false));
        caseRecord1.addTask(new Task("Review case", r, false));

        caseRecordTaskService.saveCaseRecord(caseRecord1);

    }

    private void createPersonFemale() {
        final Date dateOfBirth = getRandomDateOfBirth();
        final Date expirationDate = getFutureRandomDate( new Date(), 31, 10 );

        final CaseRecord caseRecord1 = new CaseRecord();
        caseRecord1.setFirstName("Sabrina Helena");
        caseRecord1.setLastName("Weinberg-Hols");
        caseRecord1.setSex("F");
        caseRecord1.setCountry("AUS");
        caseRecord1.setPassportNo("1991738612");
        caseRecord1.setDateOfBirth(dateOfBirth);
        caseRecord1.setExpirationDate(expirationDate);
        caseRecord1.setCurrentState( FSM_START.toString() );

        final Date p = getFutureRandomDate( new Date(), 4, 0 );
        final Date q = getFutureRandomDate( p, 8, 0 );
        final Date r = getFutureRandomDate( q, 12, 0 );

        caseRecord1.addTask(new Task("Allocate", getFutureRandomDate(new Date(), 10, 0), true ));
        caseRecord1.addTask(new Task("Criminal index check", p, false ));
        caseRecord1.addTask(new Task("Review case", r, false));

        caseRecordTaskService.saveCaseRecord(caseRecord1);
    }


    private void createRandomPerson() {
        final Date dateOfBirth = getRandomDateOfBirth();
        final Date expirationDate = getFutureRandomDate( new Date(), 31, 10 );

        final CaseRecord caseRecord1 = new CaseRecord();
        String sex = Math.random() >= 0.5 ? "F" : "M";
        caseRecord1.setSex(sex);
        if ( "F".equals(sex))
            caseRecord1.setFirstName( populationHelper.getRandomGirlsName());
        else
            caseRecord1.setFirstName( populationHelper.getRandomBoysName());
        caseRecord1.setLastName(populationHelper.getRandomSurname());
        caseRecord1.setCountry(populationHelper.getRandomPassportCountry().getCode());
        caseRecord1.setPassportNo(Long.toString((long)(Math.random()*9.9e9 + 1e10)));
        caseRecord1.setDateOfBirth(dateOfBirth);
        caseRecord1.setExpirationDate(expirationDate);
        caseRecord1.setCurrentState( FSM_START.toString() );

        final Date p = getFutureRandomDate( new Date(), 7, 0 );
        final Date q = getFutureRandomDate( p, 14, 0 );
        final Date r = getFutureRandomDate( q, 21, 0 );

        caseRecord1.addTask(new Task("Allocate", getFutureRandomDate(new Date(), 10, 0), true ));
        caseRecord1.addTask(new Task("Review case", r, false));

        caseRecordTaskService.saveCaseRecord(caseRecord1);
    }
}
