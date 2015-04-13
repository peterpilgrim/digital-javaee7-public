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

package uk.co.xenonique.nationalforce.entity;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import uk.co.xenonique.nationalforce.DateUtils;
import uk.co.xenonique.nationalforce.StringHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.xenonique.nationalforce.boundary.CaseRecordTaskService;
import uk.co.xenonique.nationalforce.control.BasicStateMachine;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static uk.co.xenonique.nationalforce.control.BasicStateMachine.FSM_START;

/**
 * A unit test PersistCaseRecordAndTasksTest to verify the operation of PersistCaseRecordAndTasksTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class PersistCaseRecordAndTasksTest {

//    @Deployment
//    public static JavaArchive createDeployment() {
//        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
//            .addPackage(StringHelper.class.getPackage())
//            .addClasses(CaseRecord.class, Task.class)
//            .addClass(BasicStateMachine.class)
//            .addAsResource(
//                    "test-persistence.xml",
//                    "META-INF/persistence.xml")
//            .addAsManifestResource(
//                    EmptyAsset.INSTANCE,
//                    ArchivePaths.create("beans.xml"));
//        System.out.println(jar.toString(true));
//        return jar;
//    }


    @Deployment
    public static WebArchive createDeployment() {

//        File[] lib = ShrinkWrapMaven.resolver()
//                .resolve("org.jboss.weld.servlet:weld-servlet:1.1.9.Final")
//                .withTransitivity().as(File.class);
        final WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addClasses(CaseRecord.class, Task.class, BasicStateMachine.class)
                .addClasses(StringHelper.class, DateUtils.class)
                .addAsWebInfResource(
                        "test-persistence.xml",
                        "classes/META-INF/persistence.xml")
                .addAsWebInfResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
        System.out.println(war.toString(true));
        return war;
    }


    @PersistenceContext(unitName = "XenNationalForce")
    EntityManager em;

    @Resource
    UserTransaction utx;

    @Test
    public void persist_CaseRecord_without_Task_elements() throws Exception {
        assertThat( em, is(notNullValue()));
        assertThat( utx, is(notNullValue()));

        final CaseRecord caseRecord1 = new CaseRecord();
        caseRecord1.setFirstName("Anne Marie");
        caseRecord1.setLastName("Dubois");
        caseRecord1.setSex("F");
        caseRecord1.setCountry("France");
        caseRecord1.setPassportNo("740370119595784");
        caseRecord1.setDateOfBirth(DateUtils.asDate(LocalDate.now().minusYears(33)));
        caseRecord1.setExpirationDate(DateUtils.asDate(LocalDate.now().plusYears(5)));
        caseRecord1.setCurrentState(FSM_START.toString());

        utx.begin();
        try {
            em.persist(caseRecord1);
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
        utx.commit();
        System.out.printf("****** caseRecord1=%s\n", caseRecord1);
        assertThat(caseRecord1.getId(), is(notNullValue()));
    }


    @Test
    public void persist_CaseRecord_with_Task_elements() throws Exception {
        assertThat( em, is(notNullValue()));
        assertThat( utx, is(notNullValue()));

        final CaseRecord caseRecord1 = new CaseRecord();
        caseRecord1.setFirstName("Anne Marie");
        caseRecord1.setLastName("Dubois");
        caseRecord1.setSex("F");
        caseRecord1.setCountry("France");
        caseRecord1.setPassportNo("740370119595784");
        caseRecord1.setDateOfBirth(DateUtils.asDate(LocalDate.now().minusYears(33)));
        caseRecord1.setExpirationDate(DateUtils.asDate(LocalDate.now().plusYears(5)));
        caseRecord1.setCurrentState(FSM_START.toString());

        final int N =3;
        for ( int j=0; j<N; ++j) {
            Date targetDate = null;
            if ( j > 0 ) {
                targetDate = new Date(System.currentTimeMillis() + (long) (Math.random() * 86400 * 5 * 1000 ));
            }
            Task task = new Task("task"+j, targetDate, false );
            caseRecord1.addTask(task);
            task.setCaseRecord(caseRecord1);
        }
        utx.begin();
        em.persist(caseRecord1);
        utx.commit();
        System.out.printf("*****  caseRecord1=%s\n", caseRecord1);

        assertThat(caseRecord1.getId(), is(notNullValue()));
        assertThat(caseRecord1.getTasks().isEmpty(), is(false));
        assertThat( caseRecord1.getTasks().size(), is(N));
        for ( Task task: caseRecord1.getTasks()) {
            assertThat( task.getId(), is(notNullValue()));
            assertThat( task.getCaseRecord().getId(), is( caseRecord1.getId()) );
        }
    }
}
