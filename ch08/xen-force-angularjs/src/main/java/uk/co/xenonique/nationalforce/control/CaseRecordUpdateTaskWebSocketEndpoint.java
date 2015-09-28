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

import uk.co.xenonique.nationalforce.boundary.CaseRecordTaskService;
import uk.co.xenonique.nationalforce.entity.CaseRecord;
import uk.co.xenonique.nationalforce.entity.Task;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

/**
 * The type CaseRecordUpdateTaskWebSocketEndpoint
 *
 * @author Peter Pilgrim
 */
@ServerEndpoint("/update-task-status")
@Stateless
public class CaseRecordUpdateTaskWebSocketEndpoint {
    @Inject
    CaseRecordTaskService service;

    static JsonGeneratorFactory jsonGeneratorFactory = Json.createGeneratorFactory(
        new HashMap<String, Object>() {{
            put(JsonGenerator.PRETTY_PRINTING, true);
        }});

    @OnMessage
    public String updateTaskStatus(String message) {
        System.out.printf("--++++-- %s.updateTaskStatus(message:%s)\n", getClass().getSimpleName(), message);
        System.out.flush();
        final StringReader stringReader = new StringReader(message);
        final JsonReader reader = Json.createReader(stringReader);
        final JsonObject obj = reader.readObject();
        if ( !obj.containsKey("caseRecordId")) {
            return "json object does not have `caseRecordId' key";
        }
        if ( !obj.containsKey("taskId")) {
            return "json object does not have `taskId' key";
        }
        if ( !obj.containsKey("completed")) {
            return "json object does not have `completed' key";
        }

        final int projectId = obj.getInt("caseRecordId");
        final int taskId = obj.getInt("taskId");
        final boolean completed = obj.getBoolean("completed");
        final List<CaseRecord> projects = service.findCaseById(projectId);

        if ( !projects.isEmpty()) {
//            for ( Task task: projects.get(0).getTasks()) {
//                if ( task.getId() == taskId) {
//                    task.setCompleted(completed);
//                    service.saveCaseRecord(task.getCaseRecord());
//                }
//            }
            projects.get(0).getTasks().stream()
                .filter(task -> task.getId() == taskId).
                forEach(task -> {
                    task.setCompleted(completed);
                    service.saveCaseRecord(task.getCaseRecord());
                });
            return "OK";
        }
        return "NOT FOUND";
    }

    @OnOpen
    public void open( Session session ) {
        System.out.printf("%s.open( session=%s )\n",
                getClass().getSimpleName(), session );
    }

    @OnClose
    public void close( Session session ) {
        System.out.printf("%s.close( session=%s )\n",
                getClass().getSimpleName(), session );
    }

    @OnError
    public void error( Session session, Throwable error ){
        System.err.printf("%s.onError( session=%s, error=%s )\n",
                getClass().getSimpleName(), session, error);
        error.printStackTrace(System.err);
    }
}
