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

import uk.co.xenonique.nationalforce.boundary.CaseRecordTaskService;
import uk.co.xenonique.nationalforce.entity.CaseRecord;
import uk.co.xenonique.nationalforce.entity.Task;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import static javax.ws.rs.core.MediaType.*;

/**
 * The type ProjectRESTServerEndpoint
 *
 * @author Peter Pilgrim (peter)
 */
@Path("/caseworker/")
@Stateless
public class CaseWorkerRESTServerEndpoint {

    static JsonGeneratorFactory jsonGeneratorFactory
        = Json.createGeneratorFactory(
            new HashMap<String, Object>() {{
                put(JsonGenerator.PRETTY_PRINTING, true);
            }});

    @Inject
    CaseRecordTaskService service;

    @GET
    @Path("/item/{id}")
    @Produces(APPLICATION_JSON)
    public String retrieveCase(
        @PathParam("id") int caseId ) {
        if (caseId < 1)
            throw new RuntimeException(
                    "Invalid caseId:["+caseId+"] supplied");

        List<CaseRecord> caseRecords = service.findCaseById( caseId );
        if ( caseRecords.isEmpty() ) {
            throw new RuntimeException(
                    "No project was found with caseId:["+caseId+"]");
        }

        StringWriter swriter = new StringWriter();
        JsonGenerator generator
            = jsonGeneratorFactory.createGenerator(swriter);
        CaseRecordHelper.writeCaseRecordAsJson(generator, caseRecords.get(0)).close();
        return swriter.toString();
    }

    @POST
    @Path("/item")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String createCase( JsonObject json )
            throws Exception {
        CaseRecord caseRecord = new CaseRecord();
        caseRecord.setSex(json.getString("sex"));
        caseRecord.setFirstName(json.getString("firstName"));
        caseRecord.setLastName(json.getString("lastName"));
        caseRecord.setCountry(json.getString("country"));
        caseRecord.setPassportNo(json.getString("passportNo"));
        caseRecord.setDateOfBirth(CaseRecordHelper.FMT2.parse(json.getString("dateOfBirth")));
        caseRecord.setExpirationDate(CaseRecordHelper.FMT2.parse(json.getString("expirationDate")));
        caseRecord.setCurrentState(BasicStateMachine.FSM_START.toString());

        JsonArray tasksArray = json.getJsonArray("tasks");
        if ( tasksArray != null ) {
            for ( int j=0; j<tasksArray.size(); ++j ) {
                JsonObject taskObject = tasksArray.getJsonObject(j);
                Task task = new Task(
                    taskObject.getString("name"),
                    ( taskObject.containsKey("targetDate") ?
                       CaseRecordHelper.FMT.parse(taskObject.getString("targetDate")) :
                       null ),
                    taskObject.getBoolean("completed"));
                caseRecord.addTask(task);
                task.setCaseRecord(caseRecord);
            }
        }

        service.saveCaseRecord(caseRecord);
        StringWriter swriter = new StringWriter();
        JsonGenerator generator =
            jsonGeneratorFactory.createGenerator(swriter);
        CaseRecordHelper.writeCaseRecordAsJson(generator, caseRecord).close();
        return swriter.toString();
    }

    @PUT
    @Path("/item/{caseId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String updateCase(
            @PathParam("caseId") int caseId,
            JsonObject json )
            throws Exception {
        if (caseId < 1)
            throw new RuntimeException(
                    "Invalid caseId:["+caseId+"] supplied");

        final List<CaseRecord> caseRecords = service.findCaseById(caseId);
        if ( caseRecords.isEmpty() ) {
            throw new RuntimeException(
                    "No case record was found with caseId:["+caseId+"]");
        }

        CaseRecord caseRecord = caseRecords.get(0);
        caseRecord.setSex(json.getString("sex"));
        caseRecord.setFirstName(json.getString("firstName"));
        caseRecord.setLastName(json.getString("lastName"));
        caseRecord.setCountry(json.getString("country"));
        caseRecord.setPassportNo(json.getString("passportNo"));
        caseRecord.setDateOfBirth(CaseRecordHelper.FMT2.parse(json.getString("dateOfBirth")));
        caseRecord.setExpirationDate(CaseRecordHelper.FMT2.parse(json.getString("expirationDate")));
        caseRecord.setCurrentState(BasicStateMachine.retrieveCurrentState(
                json.getString("currentState", BasicStateMachine.FSM_START.toString())).toString());

        System.out.printf("caseRecord=%s\n", caseRecord);
        service.saveCaseRecord(caseRecord);
        final StringWriter swriter = new StringWriter();
        final JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        CaseRecordHelper.writeCaseRecordAsJson(generator, caseRecord).close();
        return swriter.toString();
    }

    @POST
    @Path("/item/{caseId}/task")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String createNewTaskOnCase(
            @PathParam("caseId") int caseId,
            JsonObject taskObject )
            throws Exception
    {
        System.out.printf("createNewTaskOnCase( %d, %s )\n", caseId, taskObject);
        if (caseId < 1)
            throw new RuntimeException(
                    "Invalid caseId:["+caseId+"] supplied");

        List<CaseRecord> caseRecords = service.findCaseById(caseId);
        if ( caseRecords.isEmpty() ) {
            throw new RuntimeException(
                    "No case record was found with caseId:["+caseId+"]");
        }

        final CaseRecord caseRecord = caseRecords.get(0);
        final Task task = new Task(
                taskObject.getString("name"),
                ( taskObject.containsKey("targetDate") ?
                        CaseRecordHelper.convertToDate(taskObject.getString("targetDate")) :
                        null ),
                ( taskObject.containsKey("completed")) ?
                    taskObject.getBoolean("completed") : false );
        caseRecord.addTask(task);
        service.saveCaseRecord(caseRecord);
        final StringWriter swriter = new StringWriter();
        JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        CaseRecordHelper.writeCaseRecordAsJson(generator, caseRecord).close();
        return swriter.toString();
    }

    @PUT
    @Path("/item/{caseId}/task/{taskId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String updateTaskOnCase(
            @PathParam("caseId") int caseId,
            @PathParam("taskId") int taskId,
            JsonObject taskObject )
            throws Exception
    {
        System.out.printf("updateTaskOnCase( %d, %d, %s )\n", caseId, taskId, taskObject);
        if (caseId < 1)
            throw new RuntimeException(
                    "Invalid caseId:["+caseId+"] supplied");

        final List<CaseRecord> caseRecords = service.findCaseById(caseId);
        if ( caseRecords.isEmpty() ) {
            throw new RuntimeException(
                    "No case record was found with caseId:["+caseId+"]");
        }

        final CaseRecord caseRecord = caseRecords.get(0);
        for ( Task task: caseRecord.getTasks()) {
            if ( task.getId().equals(taskId )) {
                task.setName( taskObject.getString("name") );
                task.setTargetDate( taskObject.containsKey("targetDate") ?
                        CaseRecordHelper.convertToDate(taskObject.getString("targetDate")) :
                        null );
                task.setCompleted( taskObject.containsKey("completed") ?
                        taskObject.getBoolean("completed") : false );
            }
        }
        service.saveCaseRecord(caseRecord);

        final StringWriter swriter = new StringWriter();
        final JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        CaseRecordHelper.writeCaseRecordAsJson(generator, caseRecord).close();
        return swriter.toString();
    }

    @DELETE
    @Path("/item/{caseId}/task/{taskId}")
    @Consumes( { APPLICATION_JSON, APPLICATION_XML, TEXT_PLAIN })
    @Produces(APPLICATION_JSON)
    public String removeTaskFromCase(
            @PathParam("caseId") int caseId,
            @PathParam("taskId") int taskId,
            JsonObject taskObject )
            throws Exception
    {
        // AngularJS requires additional consumption of XML in order to avoid 415 Unsupported Media Type
        // See this http://stackoverflow.com/questions/17379447/angularjs-and-jersey-rest-delete-operation-fails-with-415-status-code
        System.out.printf("removeTaskFromCase( %d, %d, %s )\n", caseId, taskId, taskObject);
        if (caseId < 1)
            throw new RuntimeException(
                    "Invalid caseId:["+caseId+"] supplied");

        final List<CaseRecord> caseRecords = service.findCaseById(caseId);
        if ( caseRecords.isEmpty() ) {
            throw new RuntimeException(
                    "No case record was found with caseId:["+caseId+"]");
        }

        final CaseRecord caseRecord = caseRecords.get(0);
        for ( Task task: caseRecord.getTasks()) {
            if ( task.getId().equals(taskId )) {
                caseRecord.removeTask(task);
                break;
            }
        }
        service.saveCaseRecord(caseRecord);

        final StringWriter swriter = new StringWriter();
        final JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        CaseRecordHelper.writeCaseRecordAsJson(generator, caseRecord).close();
        return swriter.toString();
    }

    @GET
    @Path("/item/{caseId}/task/{taskId}/delete")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String angularRemoveTaskFromProject(
            @PathParam("caseId") int caseId,
            @PathParam("taskId") int taskId,
            JsonObject taskObject )
            throws Exception
    {
        // AngularJS requires additional consumption of XML in order to avoid 415 Unsupported Media Type
        // See this http://stackoverflow.com/questions/17379447/angularjs-and-jersey-rest-delete-operation-fails-with-415-status-code
        System.out.printf("angularRemoveTaskFromProject( %d, %d, %s )\n", caseId, taskId, taskObject);
        if (caseId < 1)
            throw new RuntimeException(
                    "Invalid caseId:["+caseId+"] supplied");

        final List<CaseRecord> caseRecords = service.findCaseById(caseId);
        if ( caseRecords.isEmpty() ) {
            throw new RuntimeException(
                    "No project was found with caseId:["+caseId+"]");
        }

        final CaseRecord caseRecord = caseRecords.get(0);
        for ( Task task: caseRecord.getTasks()) {
            if ( task.getId().equals(taskId )) {
                caseRecord.removeTask(task);
                break;
            }
        }
        service.saveCaseRecord(caseRecord);

        final StringWriter swriter = new StringWriter();
        final JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        CaseRecordHelper.writeCaseRecordAsJson(generator, caseRecord).close();
        return swriter.toString();
    }

    @Resource(name="concurrent/LongRunningTasksExecutor")
    ManagedExecutorService executor;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public void getCaseRecordList(
        @Suspended final AsyncResponse asyncResponse) {
        System.out.printf("=======> %s.getCaseRecordList() %s asyncResponse=%s\n",
                getClass().getSimpleName(), Thread.currentThread(), asyncResponse );

        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.printf("========>> %s.getCaseRecordList() Executable Task %s asyncResponse=%s\n",
                        getClass().getSimpleName(), Thread.currentThread(), asyncResponse);
                final List<CaseRecord> caseRecords = service.findAllCases();
                final StringWriter swriter = new StringWriter();
                final JsonGenerator generator = jsonGeneratorFactory.createGenerator(swriter);
                try {
                    CaseRecordHelper.generateCaseRecordAsJson(generator, caseRecords).close();
                    System.out.printf("========>> caseRecords.size=%d Sending swriter=[%s]\n", caseRecords.size(), swriter.toString());
                    final Response response = Response.ok(swriter.toString()).build();
                    asyncResponse.resume(response);
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });

        // We add this slight delay to ensure the Arquillian integration unit test receives the result.
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Utility methods

}
