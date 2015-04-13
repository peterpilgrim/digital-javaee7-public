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

package uk.co.xenonique.nationalforce.control;

import uk.co.xenonique.nationalforce.StringHelper;
import uk.co.xenonique.nationalforce.boundary.CaseRecordTaskService;
import uk.co.xenonique.nationalforce.entity.CaseRecord;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static uk.co.xenonique.nationalforce.FixtureUtils.createCaseRecordAndTasks;
import static javax.ws.rs.core.MediaType.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A unit test ProjectRESTServerEndpointTest to verify the operation of ProjectRESTServerEndpointTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class CaseRecordRESTServerEndpointTest {

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage(CaseRecord.class.getPackage())
                .addPackage(CaseWorkerRESTServerEndpoint.class.getPackage())
                .addPackage(CaseRecordTaskService.class.getPackage())
                .addClasses(StringHelper.class)
                .addAsWebInfResource(
                        "test-persistence.xml",
                        "classes/META-INF/persistence.xml")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("classes/beans.xml"));
        System.out.println(war.toString(true));
        return war;
    }

    @EJB
    CaseRecordTaskService service;

    private static final JsonBuilderFactory bf = Json.createBuilderFactory(null);

    @Test
    public void invoke_POST_create_CaseRecord_with_Tasks() throws Exception {

        JsonObject input = bf.createObjectBuilder()
            .add("sex", "F")
            .add("firstName", "Caroline Isabella")
            .add("lastName", "Hamerhof")
            .add("country", "Canada")
            .add("passportNo", "123456789012")
            .add("dateOfBirth", "1997-05-16")
            .add("expirationDate", "2025-01-10")
            .add("currentSate", "Start")
            .add("tasks",
                bf.createArrayBuilder()
                    .add(
                            bf.createObjectBuilder()
                                    .add("name", "Find the Common Denominator")
                                    .add("targetDate", "23-July-2018")
                                    .add("completed", JsonValue.FALSE)
                                    .build())
                    .add(
                            bf.createObjectBuilder()
                                    .add("name", "Decide on the Metrics")
                                    .add("targetDate", "24-July-2018")
                                    .add("completed", JsonValue.FALSE)
                                    .build())
                    .build()
            )
            .build();

        WebTarget target = ClientBuilder.newClient()
            .target("http://localhost:8181/xen-national-force/rest/caseworker/item");

        Response response = target.request().post(
            Entity.entity(
                input, APPLICATION_JSON_TYPE) );
        assertThat(response.getStatus(), is(200));

        String text = response.readEntity( String.class );
        System.out.printf(">>------ text = %s\n", text);
        assertNotNull(text);
    }

    @Test
    public void invoke_GET_retrieve_CaseRecord_by_CaseId() throws Exception {
        CaseRecord proj1 = createCaseRecordAndTasks(3);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<CaseRecord> caseRecords = service.findAllCases();
        Thread.sleep(1000);

        WebTarget target = ClientBuilder.newClient()
                .target("http://localhost:8181/xen-national-force/rest/caseworker/item/" + proj1.getId());
        Response response = target.request().get();
        assertThat(response.getStatus(), is(200));
        String text = response.readEntity( String.class );
        System.out.printf(">>====== text = %s\n", text);
        assertNotNull(text);
    }


    @Test
    public void invoke_asynchronous_GET_request_demo() throws Exception {
        CaseRecord proj1 = createCaseRecordAndTasks(3);
        CaseRecord proj2 = createCaseRecordAndTasks(2);
        CaseRecord proj3 = createCaseRecordAndTasks(1);
        service.saveProject(proj1);
        service.saveProject(proj2);
        service.saveProject(proj3);

        // Force a flush to the database?!
        List<CaseRecord> caseRecords = service.findAllCases();
        Thread.sleep(1000);

        WebTarget target = ClientBuilder.newClient()
            .target("http://localhost:8181/xen-national-force/rest/caseworker/list");
        Future<Response> future =
                target.request().async().get();
        assertNotNull(future);
        Response response = future.get(3, TimeUnit.SECONDS );
        assertThat(response.getStatus(), is(200));
        String text = response.readEntity( String.class );
        System.out.printf(">>====== text = %s\n", text);
        assertNotNull(text);
    }

    @Test
    public void invoke_POST_add_new_Task_to_CaseRecord() throws Exception {
        CaseRecord proj1 = createCaseRecordAndTasks(1);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<CaseRecord> caseRecords = service.findAllCases();
        Thread.sleep(1000);

        JsonObject input = bf.createObjectBuilder()
            .add("name", "Digital Holographic-SLR Camera")
            .add("targetDate", "16-January-2018")
            .add("completed", JsonValue.FALSE)
            .build();

        WebTarget target = ClientBuilder.newClient()
            .target("http://localhost:8181/xen-national-force/rest/caseworker/item/" + proj1.getId() + "/task");
        Response response = target.request().post(
            Entity.entity(
            input, APPLICATION_JSON_TYPE) );
        assertThat(response.getStatus(), is(200));
        String text = response.readEntity( String.class );
        System.out.printf(">>====== text = %s\n", text);
        assertNotNull(text);
    }

    @Test
    public void invoke_PUT_update_Task_on_CaseRecord() throws Exception {
        CaseRecord proj1 = createCaseRecordAndTasks(0);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<CaseRecord> caseRecords = service.findAllCases();
        Thread.sleep(1000);

        final Calendar cal = Calendar.getInstance();
        cal.setTime( new Date() );
        cal.add(Calendar.MONTH, 1);
        final Date date1 = cal.getTime();
        cal.add(Calendar.MONTH, 2);
        final Date date2 = cal.getTime();
        final SimpleDateFormat outgoingDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        final SimpleDateFormat incomingDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        final JsonObject input1 = bf.createObjectBuilder()
                .add("name", "Fireside Chat")
                .add("targetDate", outgoingDateFormatter.format(date1))
                .add("completed", JsonValue.FALSE)
                .build();

        WebTarget target = ClientBuilder.newClient()
                .target("http://localhost:8181/xen-national-force/rest/caseworker/item/"+proj1.getId()+"/task");
        Response response = target.request().post(
                Entity.entity(
                        input1, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));
        final JsonObject output1 = response.readEntity(JsonObject.class);
        System.out.printf(">>====== output1 = %s\n", output1);
        assertNotNull(output1);

        assertThat(output1.getJsonArray("tasks").size(), is(1));
        final JsonObject json1 = output1.getJsonArray("tasks").getJsonObject(0);
        final int taskId1 = json1.getInt("id");
        assertTrue(taskId1 > 0);
        assertThat("Fireside Chat", is(json1.getString("name")));

        assertThat(incomingDateFormatter.format(date1), is(json1.getString("targetDate")));
        assertFalse( json1.getBoolean("completed")) ;
        final JsonObject input2 = bf.createObjectBuilder()
                .add("id", taskId1 )
                .add("name", "JavaOne USA")
                .add("targetDate", outgoingDateFormatter.format(date2))
                .add("completed", JsonValue.TRUE)
                .build();

        target = ClientBuilder.newClient()
                .target("http://localhost:8181/xen-national-force/rest/caseworker/item/" + proj1.getId() + "/task/" + taskId1);
        response = target.request().put(
                Entity.entity(
                        input2, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));
        JsonObject output2 = response.readEntity( JsonObject.class );
        System.out.printf(">>====== output2 = %s\n", output2);
        assertNotNull(output2);

        assertThat(output2.getJsonArray("tasks").size(), is(1));
        final JsonObject json2 = output2.getJsonArray("tasks").getJsonObject(0);
        final int taskId2 = json2.getInt("id");
        assertTrue(taskId2 > 0);
        assertThat( "JavaOne USA", is(json2.getString("name")));
        assertThat( incomingDateFormatter.format(date2), is(json2.getString("targetDate")));
        assertTrue( json2.getBoolean("completed"));
    }

    @Test
    public void invoke_DELETE_remove_Task_from_CaseRecord() throws Exception {
        CaseRecord proj1 = createCaseRecordAndTasks(0);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<CaseRecord> caseRecords = service.findAllCases();
        Thread.sleep(1000);

        JsonObject input1 = bf.createObjectBuilder()
                .add("name", "Make money now")
                .add("targetDate", "14-Jul-2015")
                .add("completed", JsonValue.FALSE)
                .build();

        WebTarget target = ClientBuilder.newClient()
                .target("http://localhost:8181/xen-national-force/rest/caseworker/item/"+proj1.getId()+"/task");
        Response response = target.request().post(
                Entity.entity(
                        input1, APPLICATION_JSON_TYPE));
        assertNotNull(response);
        JsonObject output1 = response.readEntity( JsonObject.class );
        System.out.printf(">>====== output1 = %s\n", output1);
        assertNotNull(output1);

        JsonObject json = output1.getJsonArray("tasks").getJsonObject(0);
        int taskId = json.getInt("id");
        assertTrue( taskId > 0 );
        assertThat( "Make money now", is(json.getString("name")));
        assertThat( "2015-07-14", is(json.getString("targetDate")));
        assertFalse( json.getBoolean("completed")) ;

        target = ClientBuilder.newClient()
                .target("http://localhost:8181/xen-national-force/rest/caseworker/item/" + proj1.getId() + "/task/" + taskId);
        response = target.request().delete();
        assertThat( response.getStatus(), is(200));

        JsonObject output2 = response.readEntity( JsonObject.class );
        assertThat( output2.getJsonArray("tasks").size(), is(0));
    }

    @Test
    public void invoke_PUT_CaseRecord() throws Exception {
        CaseRecord proj1 = createCaseRecordAndTasks(1);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<CaseRecord> caseRecords = service.findAllCases();
        Thread.sleep(1000);

        JsonObject input1 = bf.createObjectBuilder()
                .add("id", proj1.getId())
                .add("sex", "M")
                .add("firstName", "Christopher Charles")
                .add("lastName", "Nozi Ngomo")
                .add("country", "Nigeria")
                .add("passportNo", "123456123456")
                .add("dateOfBirth", "1998-05-16")
                .add("expirationDate", "2026-01-10")
                .add("currentSate", "Start")
                /* We don't need the Task JSON object here */
                .build();

        WebTarget target = ClientBuilder.newClient()
                .target("http://localhost:8181/xen-national-force/rest/caseworker/item/" + proj1.getId());
        Response response = target.request().put(
                Entity.entity(
                        input1, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));

        JsonObject output1 = response.readEntity(JsonObject.class);
        assertThat( "M", is(output1.getString("sex")));
        assertThat( "Christopher Charles", is(output1.getString("firstName")));
        assertThat( "Nozi Ngomo", is(output1.getString("lastName")));
        assertThat(output1.getJsonArray("tasks").size(), is(1));
    }

}