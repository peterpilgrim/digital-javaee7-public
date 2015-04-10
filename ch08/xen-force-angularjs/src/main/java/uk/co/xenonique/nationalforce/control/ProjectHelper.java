package uk.co.xenonique.nationalforce.control;

import uk.co.xenonique.nationalforce.entity.CaseRecord;
import uk.co.xenonique.nationalforce.entity.Task;

import javax.json.stream.JsonGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The type ProjectHelper
 *
 * @author Peter Pilgrim
 */
public class ProjectHelper {
    static SimpleDateFormat FMT =
        new SimpleDateFormat("dd-MMM-yyyy");
    static SimpleDateFormat FMT2 =
            new SimpleDateFormat("yyyy-MM-dd");

    public static JsonGenerator generateProjectsAsJson( JsonGenerator generator, List<CaseRecord> caseRecords) {
        generator.writeStartArray();
        for ( CaseRecord caseRecord : caseRecords) {
            writeProjectAsJson(generator, caseRecord);
        }
        generator.writeEnd().close();
        return generator;
    }

    public static JsonGenerator writeProjectAsJson( JsonGenerator generator, CaseRecord caseRecord) {
        generator.writeStartObject()
            .write("id", caseRecord.getId())
            .write("name", caseRecord.getName());
        if ( caseRecord.getHeadline() != null )
            generator.write("headline", caseRecord.getHeadline());
        if ( caseRecord.getDescription() != null )
            generator.write("description", caseRecord.getDescription());
        generator.writeStartArray("tasks");

        for ( Task task: caseRecord.getTasks()) {
            writeTaskAsJson(generator,task);
        }
        generator.writeEnd().writeEnd();
        return generator;
    }

    public static JsonGenerator writeTaskAsJson( JsonGenerator generator, Task task ) {
        generator.writeStartObject()
            .write("id", task.getId())
            .write("name", task.getName())
            .write("targetDate",
                    task.getTargetDate() == null ? "" :
                            FMT2.format(task.getTargetDate()))
            .write("completed", task.isCompleted())
            .write("projectId",
                    task.getCaseRecord() != null ? task.getCaseRecord().getId() : 0 )
            .writeEnd();
        return generator;
    }

    public static boolean convertToBoolean( String value ) {
        return convertToBoolean(value, false);
    }

    public static boolean convertToBoolean( String value, boolean defaultValue ) {
        if ( value == null )
            return defaultValue;
        value = value.trim();
        if ( value.length() == 0 )
            return defaultValue;
        if ( "true".equalsIgnoreCase(value) ||
                "yes".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value) )
            return true;
        if ( "false".equalsIgnoreCase(value) ||
                "no".equalsIgnoreCase(value) || "0".equalsIgnoreCase(value) )
            return false;
        return defaultValue;
    }

    public static Date convertToDate( String value ) {
        if ( value == null )
            return null;
        value = value.trim();
        if ( value.length() == 0 )
            return null;

        Date date = null;
        try {
            date = FMT.parse(value);
        }
        catch (ParseException pe1) {
            try {
                date = FMT2.parse(value);
            }
            catch (ParseException pe2) {
                throw new RuntimeException(
                        "unable to parse date ["+value+"]");
            }
        }

        return date;
    }
}
