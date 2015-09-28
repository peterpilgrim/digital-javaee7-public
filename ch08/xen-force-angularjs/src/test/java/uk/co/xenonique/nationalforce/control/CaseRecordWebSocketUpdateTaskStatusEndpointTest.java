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

package uk.co.xenonique.nationalforce.control;

import static org.junit.Assert.*;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.runner.RunWith;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import uk.co.xenonique.nationalforce.StringHelper;
import uk.co.xenonique.nationalforce.boundary.CaseRecordTaskService;
import uk.co.xenonique.nationalforce.entity.CaseRecord;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.net.URI;
import java.util.List;

import static uk.co.xenonique.nationalforce.FixtureUtils.createCaseRecordAndTasks;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Verifies the operation of the CaseRecordWebSocketUpdateTaskStatusEndpointTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class CaseRecordWebSocketUpdateTaskStatusEndpointTest {
    public final static String WS_BASE_APPLICATION_URL = "ws://localhost:8181/xen-national-force";

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "xen-national-force.war")
                .addPackage(CaseRecord.class.getPackage())
                .addPackage(CaseRecordUpdateTaskWebSocketEndpoint.class.getPackage())
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
    public void invokeEndpointChangeProjectTaskStatus() throws Exception {
        CaseRecord case1 = createCaseRecordAndTasks(3);
        service.saveCaseRecord(case1);

        Thread.sleep(500);

        assertFalse(case1.getTasks().get(0).isCompleted());
        StringWriter swriter = new StringWriter();
        JsonGenerator generator = Json.createGenerator(swriter);
        case1.getTasks().get(0).setCompleted(true);
        CaseRecordHelper.writeTaskAsJson(generator, case1.getTasks().get(0), "taskId").close();

        System.out.printf("****  SENDING JSON OVER TO WEBSOCKET ****\n" +
                ">>>>  swriter=%s\n", swriter.toString());

        System.out.printf("****  START  ****\n");
        URI uri = URI.create(WS_BASE_APPLICATION_URL+"/update-task-status");
        System.out.printf("uri=%s\n", uri ) ;
        WebSocketClientTesterEndpoint client = new WebSocketClientTesterEndpoint(
                uri.toString(), swriter.toString());

        client.makeConnection();
        System.out.printf("****  WAIT   ****\n");

        String text = client.getReceivedMessage( 3000 );
//        assertEquals("ECHO: "+message,actual);
        System.out.printf(">>>> text=%s\n", text);
        assertNotNull(text);
        List<CaseRecord> caseRecords = service.findCaseById(case1.getId());
        assertTrue(caseRecords.get(0).getTasks().get(0).isCompleted());
        assertThat(text, is("OK"));
        System.out.printf("****  DONE   ****\n");
    }}
