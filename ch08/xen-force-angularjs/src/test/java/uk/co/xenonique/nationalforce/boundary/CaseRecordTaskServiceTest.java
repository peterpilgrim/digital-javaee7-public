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

package uk.co.xenonique.nationalforce.boundary;

import uk.co.xenonique.nationalforce.FixtureUtils;
import uk.co.xenonique.nationalforce.StringHelper;
import uk.co.xenonique.nationalforce.control.BasicStateMachine;
import uk.co.xenonique.nationalforce.entity.CaseRecord;
import uk.co.xenonique.nationalforce.entity.Task;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.xenonique.nationalforce.init.DemoDataConfigurator;

import javax.ejb.EJB;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import static uk.co.xenonique.nationalforce.FixtureUtils.*;

/**
 * A unit test ProjectTaskServiceTest to verify the operation of ProjectTaskServiceTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class CaseRecordTaskServiceTest {

    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class)
            .addPackage(CaseRecord.class.getPackage())
            .addPackage(BasicStateMachine.class.getPackage())
            .addPackage(CaseRecordTaskService.class.getPackage())
            .addClasses(StringHelper.class)
            .addAsWebInfResource(
                    "test-persistence.xml",
                    "classes/META-INF/persistence.xml")
            .addAsWebInfResource(
                    EmptyAsset.INSTANCE,
                    ArchivePaths.create("classes/beans.xml"));
        System.out.println(war.toString(true));
        return war;
    }

    @EJB
    CaseRecordTaskService service;


    @Test
    public void save_CaseRecord() {
        final CaseRecord case1 = createCaseRecordAndTasks(3);
        service.saveProject(case1);
        System.out.printf("*****  case1=%s\n", case1);
        assertNotNull(case1.getId());
        assertFalse(case1.getTasks().isEmpty());
        assertThat( case1.getTasks().size(), is(3));
        for ( Task task: case1.getTasks()) {
            assertThat( task.getId(), is(notNullValue()));
            assertThat( task.getCaseRecord().getId(), is( case1.getId()) );
        }
    }

    @Test
    public void update_CaseRecord() {
        final CaseRecord case1 = createCaseRecordAndTasks(3);
        service.saveProject(case1);
        case1.setFirstName("Firebrand");
        case1.getTasks().get(1).setName("Get skimmed milk");
        service.updateProject(case1);
        System.out.printf("*****  case1=%s\n", case1);
    }

    @Test
    public void find_CaseRecord_by_CaseId() {
        final CaseRecord case1 = createCaseRecordAndTasks(3);
        service.saveProject(case1);

        List<CaseRecord> caseRecords = service.findCaseById( case1.getId());
        System.out.printf("*****  caseRecords=%s\n", caseRecords);
        assertThat(caseRecords.size(), is(1));
        assertThat( caseRecords.get(0).getTasks().size(), is(3));
    }

    @Test
    public void find_all_CaseRecords_ordered_by_last_name_then_first_name() {
        service.clearDatabase();

        final CaseRecord case1 = createCaseRecordAndTasks("F", "Alice", "Rose", 3);
        final CaseRecord case2 = createCaseRecordAndTasks("M", "Ben", "Middleton", 2);
        final CaseRecord case3 = createCaseRecordAndTasks("M", "Zachari", "Graceson", 4);
        final CaseRecord case4 = createCaseRecordAndTasks("F", "Caroline", "Graceson", 1);
        service.saveProject(case1);
        service.saveProject(case2);
        service.saveProject(case3);
        service.saveProject(case4);

        List<CaseRecord> caseRecords = service.findAllCases();
        System.out.printf("*****  caseRecords=%s\n", caseRecords);
        assertThat(caseRecords.size(), is(4));

        assertThat(caseRecords.get(0).getSex(), is("F"));
        assertThat(caseRecords.get(0).getLastName(), is("Graceson"));
        assertThat(caseRecords.get(0).getFirstName(), is("Caroline"));
        assertThat(caseRecords.get(0).getTasks().size(), is(1));

        assertThat(caseRecords.get(1).getSex(), is("M"));
        assertThat(caseRecords.get(1).getLastName(), is("Graceson"));
        assertThat(caseRecords.get(1).getFirstName(), is("Zachari"));
        assertThat(caseRecords.get(1).getTasks().size(), is(4));

        assertThat(caseRecords.get(2).getSex(), is("M"));
        assertThat(caseRecords.get(2).getLastName(), is("Middleton"));
        assertThat(caseRecords.get(2).getFirstName(), is("Ben"));
        assertThat(caseRecords.get(2).getTasks().size(), is(2));

        assertThat(caseRecords.get(3).getSex(), is("F"));
        assertThat(caseRecords.get(3).getLastName(), is("Rose"));
        assertThat(caseRecords.get(3).getFirstName(), is("Alice"));
        assertThat(caseRecords.get(3).getTasks().size(), is(3));
    }

    @Test
    public void find_Tasks_by_TaskId() {
        final CaseRecord case1 = createCaseRecordAndTasks(3);
        service.saveProject(case1);

        final List<Task> tasks = service.findTaskByTaskId(case1.getTasks().get(1).getId());
        System.out.printf("*****  case1=%s\n", case1);
        System.out.printf("*****  tasks=%s\n", tasks);
        assertThat(tasks.size(), is(1));
        assertThat(tasks.get(0).getCaseRecord(), is(case1));
    }

    @Test
    public void find_Tasks_by_CaseRecordId() {
        final CaseRecord case1 = createCaseRecordAndTasks(5);
        service.saveProject(case1);

        final List<Task> tasks = service.findTasksByCaseId(case1.getId());
        System.out.printf("*****  case1=%s\n", case1);
        System.out.printf("*****  tasks=%s\n", tasks);
        assertThat(tasks.size(), is(5));
        for ( Task task: tasks ) {
            assertThat( task.getCaseRecord(), is(case1));
        }
    }

    @Test
    public void save_then_remove_CaseRecord() {
        final CaseRecord case1 = createCaseRecordAndTasks(3);
        service.saveProject(case1);
        service.removeProject(case1);
        System.out.printf("*****  case1=%s\n", case1);
    }

    @Test
    public void when_database_is_cleared_find_no_CaseRecords() {
        service.clearDatabase();
        List<CaseRecord> caseRecords = service.findAllCases();
        assertTrue( caseRecords.isEmpty());
    }

    @Test
    public void when_database_is_cleared_find_no_CaseRecords_by_CaseId() {
        service.clearDatabase();
        List<CaseRecord> caseRecords = service.findCaseById(1);
        assertTrue( caseRecords.isEmpty());
    }

    @Test
    public void when_database_is_cleared_find_no_Tasks_by_TaskId() {
        service.clearDatabase();
        List<Task> tasks= service.findTaskByTaskId(1);
        assertTrue( tasks.isEmpty());
    }

    @Test
    public void when_datebase_is_cleared_find_no_Tasks_by_CaseId() {
        service.clearDatabase();
        final List<Task> tasks= service.findTasksByCaseId(1);
        assertTrue( tasks.isEmpty());
    }

    @Test
    public void create_sample_initial_data() {
        try {
            createInitialCaseData();
        }
        catch (Throwable t) {
            System.err.println(t.getMessage());
            throw t;
        }
    }

    public void createInitialCaseData() {
        final CaseRecord caseRecord1 = FixtureUtils.createCaseRecordAndTasks("F", "Penelope", "Horowitz", 0);
        caseRecord1.addTask(new Task("Design concept", DemoDataConfigurator.getFutureRandomDate(), true));
        caseRecord1.addTask(new Task("Write slides", DemoDataConfigurator.getFutureRandomDate(), false));
        caseRecord1.addTask(new Task("Talk to the organizer", DemoDataConfigurator.getFutureRandomDate(), false));
        caseRecord1.addTask(new Task("Prepare demos", DemoDataConfigurator.getFutureRandomDate(), false));

        service.saveProject(caseRecord1);

        final CaseRecord caseRecord2 = FixtureUtils.createCaseRecordAndTasks("M", "Steven", "Weinberg", 0);
        caseRecord2.setSex("F");
        caseRecord2.setFirstName("Jennifer");
        caseRecord2.setLastName("Flander");
        caseRecord2.addTask( new Task("Secretly find out gifts", DemoDataConfigurator.getFutureRandomDate(), true ));
        caseRecord2.addTask( new Task("Buy gifts", DemoDataConfigurator.getFutureRandomDate(), false ));
        caseRecord2.addTask( new Task("Buy birthday card", DemoDataConfigurator.getFutureRandomDate(), true ));
        caseRecord2.addTask(new Task("Organize the party", DemoDataConfigurator.getFutureRandomDate(), false));

        service.saveProject(caseRecord2);

        final Date x = DemoDataConfigurator.getFutureRandomDate(new Date(), 4, 0);
        final Date y = DemoDataConfigurator.getFutureRandomDate(x, 8, 0);
        final Date z = DemoDataConfigurator.getFutureRandomDate(y, 12, 0);

        final CaseRecord caseRecord3 = FixtureUtils.createCaseRecordAndTasks("F", "Brenda Emelda", "Anderson", 0);
        caseRecord3.addTask( new Task("Set up a meeting", x, true ));
        caseRecord3.addTask( new Task("Hold the meeting", y, false ));
        caseRecord3.addTask( new Task("Write the report", z, false ));
        caseRecord3.addTask( new Task("Furnish report to the boss", DemoDataConfigurator.getFutureRandomDate(z, 7, 0), false ));

        service.saveProject(caseRecord3);
    }

}
