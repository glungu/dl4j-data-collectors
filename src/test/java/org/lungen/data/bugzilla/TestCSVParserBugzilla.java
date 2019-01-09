package org.lungen.data.bugzilla;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import static org.lungen.data.bugzilla.CSVParser.*;


/**
 * TestCSVParserBugzilla
 *
 * @author lungen.tech@gmail.com
 */
public class TestCSVParserBugzilla {

    @Test
    public void testParseOneLine() {
        Map<String, Integer> indexMap = new HashMap<>();
        String headerStr = "id,status,resolution,severity,priority,creation_time,creator,assigned_to,component,resolution_dates,summary,description,is_open,creator_detail,assigned_to_detail,blocks,last_change_time,is_cc_accessible,keywords,cf_ext_bug_reference,cc,url,groups,see_also,whiteboard,qa_contact,cf_current_status,depends_on,dupe_of,cf_expected_fix_date,estimated_time,cf_build_name,remaining_time,cf_testlink_tc,qa_contact_detail,update_token,classification,alias,op_sys,cf_status_whiteboard,cc_detail,cf_responsible,platform,cf_responsible_detail,flags,version,deadline,actual_time,is_creator_accessible,product,is_confirmed,target_milestone";
        String[] headers = headerStr.split(",");
        IntStream.range(0, headers.length).forEach(i -> indexMap.put(headers[i], i));
        Assert.assertEquals(52, indexMap.size());

        // 52 fields
        String id = "42367";
        String status = "RESOLVED";
        String resolution = "FIXED";
        String severity = "normal";
        String priority = "P3";
        String creationTime = "2018-11-01T10:25:43Z";
        String creator = "john.doe@company.com";
        String assignedTo = "mike.smith@company.com";
        String component = "test-custom";
        String resolutionDates = "['2018-11-01T13:24:26Z']";
        String summary = "[TEST] [CUSTOM] Some description of the bug (FEAT-1234567)";
        String description = "\"{'is_private': False, 'count': 0, 'attachment_id': 38970, 'creator': 'john.doe@company.com', 'time': '2018-11-01T10:25:43Z', 'bug_id': 42367, 'tags': [], 'text': 'Created attachment 12345\\nLog\\n\\nBuild Name: 20181101_030138_TEST\\nSome description of the bug (FEAT-1234567)\\n\\nrequest=\\'{\"\"correct\"\":true,\"\"deliveryDateTime\"\":\"\"2018-11-01T12:48:13\"\",\"\"event\"\":{\"\"eventId\"\":\"\"CustomNotification\"\"},\"\"expirationDate\"\":\"\"2018-11-02T12:46:13\"\",\"\"notificationId\"\":\"\"test#3231848749336657920\"\",\"\"targets\"\":[{\"\"targetId\"\":\"\"gr_17867\"\"}],\"\"templateParameters\"\":[{\"\"n\"\":\"\"Custom day\"\",\"\"v\"\":{\"\"t\"\":3,\"\"v\"\":0}},{\"\"n\"\":\"\"Custom notification number\"\",\"\"v\"\":{\"\"t\"\":3,\"\"v\"\":1}}]}\\'}\\n', 'id': 341579, 'creation_time': '2018-11-01T10:25:43Z'}\"";
        String isOpen = "False";
        String creatorDetail = "\"{'email': 'johndoe@company.com', 'real_name': 'John Doe', 'name': 'johndoe@company.com', 'id': 840}\"";
        String assignedToDetail = "\"{'email': 'mike.smith@company.com', 'real_name': 'Mike A. Smith', 'name': 'mike.smith@company.com', 'id': 257}\"";
        String blocks = "[]";
        String lastChangeTime = "2018-11-06T16:34:40Z";
        String isCCAccessible = "True";
        String keywords = "[]";
        String cfExtBugReference = "";
        String cc = "['mike.smith@company.com']";
        String url = "";
        String groups = "\"['Group Customers', 'Group Dev']\"";
        String seeAlso = "[]";
        String whiteboard = "";
        String qaContact = "CompanyTestLeaders@company.com";
        String cfCurrentStatus = "";
        String dependsOn = "[]";
        String dupeOf = "";
        String cfExpectedFixDate = "";
        String estimatedTime = "0";
        String cfBuildName = "20181101_030138_TEST";
        String remainingTime = "0";
        String cfTestlinkTc = "";
        String qaContactDetail = "\"{'email': 'CompanyTestLeaders@company.com', 'real_name': 'QA mail list. Includes V-n-V TAC and Integration', 'name': 'CompanyTestLeaders@company.com', 'id': 58}\"";
        String updateToken = "1541567346-iFpoPBCxQtlfoWiFtWD2jRZ6H0hPPkeWxYVebUxpuGk";
        String classification = "Unclassified";
        String alias = "[]";
        String opSys = "Linux";
        String cfStatusWhiteboard = "";
        String ccDetail = "\"[{'email': 'mike.smith@company.com', 'real_name': 'Mike A. Smith', 'name': 'mike.smith@company.com', 'id': 257}]\"";
        String cfResponsible = "";
        String platform = "Other";
        String cfResponsibleDetail = "\"{'email': '', 'real_name': '', 'name': '', 'id': 0}\"";
        String flags = "[]";
        String version = "unspecified";
        String deadline = "";
        String actualTime = "0";
        String isCreatorAccessible = "True";
        String product = "Test";
        String isConfirmed = "True";
        String targetMilestone = "---";

        String line = id + "," +
                status + "," +
                resolution + "," +
                severity + "," +
                priority + "," +
                creationTime + "," +
                creator + "," +
                assignedTo + "," +
                component + "," +
                resolutionDates + "," +
                summary + "," +
                description + "," +
                isOpen + "," +
                creatorDetail + "," +
                assignedToDetail + "," +
                blocks + "," +
                lastChangeTime + "," +
                isCCAccessible + "," +
                keywords + "," +
                cfExtBugReference + "," +
                cc + "," +
                url + "," +
                groups + "," +
                seeAlso + "," +
                whiteboard + "," +
                qaContact + "," +
                cfCurrentStatus + "," +
                dependsOn + "," +
                dupeOf + "," +
                cfExpectedFixDate + "," +
                estimatedTime + "," +
                cfBuildName + "," +
                remainingTime + "," +
                cfTestlinkTc + "," +
                qaContactDetail + "," +
                updateToken + "," +
                classification + "," +
                alias + "," +
                opSys + "," +
                cfStatusWhiteboard + "," +
                ccDetail + "," +
                cfResponsible + "," +
                platform + "," +
                cfResponsibleDetail + "," +
                flags + "," +
                version + "," +
                deadline + "," +
                actualTime + "," +
                isCreatorAccessible + "," +
                product + "," +
                isConfirmed + "," +
                targetMilestone;

        List<String> values = CSVParser.parseCSVLine(line);
//        Assert.assertEquals(52, values.size());
        Assert.assertEquals(id, values.get(indexMap.get("id")));
        Assert.assertEquals(status, values.get(indexMap.get("status")));
        Assert.assertEquals(resolution, values.get(indexMap.get("resolution")));
        Assert.assertEquals(severity, values.get(indexMap.get("severity")));
        Assert.assertEquals(priority, values.get(indexMap.get("priority")));
        Assert.assertEquals(creationTime, values.get(indexMap.get("creation_time")));
        Assert.assertEquals(creator, values.get(indexMap.get("creator")));
        Assert.assertEquals(assignedTo, values.get(indexMap.get("assigned_to")));
        Assert.assertEquals(component, values.get(indexMap.get("component")));
        Assert.assertEquals(resolutionDates, values.get(indexMap.get("resolution_dates")));
        Assert.assertEquals(summary, values.get(indexMap.get("summary")));
        Assert.assertEquals(description, values.get(indexMap.get("description")));
        Assert.assertEquals(isOpen, values.get(indexMap.get("is_open")));
        Assert.assertEquals(creatorDetail, values.get(indexMap.get("creator_detail")));
        Assert.assertEquals(assignedToDetail, values.get(indexMap.get("assigned_to_detail")));
        Assert.assertEquals(blocks, values.get(indexMap.get("blocks")));
        Assert.assertEquals(lastChangeTime, values.get(indexMap.get("last_change_time")));
        Assert.assertEquals(isCCAccessible, values.get(indexMap.get("is_cc_accessible")));
        Assert.assertEquals(keywords, values.get(indexMap.get("keywords")));
        Assert.assertEquals(cfExtBugReference, values.get(indexMap.get("cf_ext_bug_reference")));
        Assert.assertEquals(cc, values.get(indexMap.get("cc")));
        Assert.assertEquals(url, values.get(indexMap.get("url")));
        Assert.assertEquals(groups, values.get(indexMap.get("groups")));
        Assert.assertEquals(seeAlso, values.get(indexMap.get("see_also")));
        Assert.assertEquals(whiteboard, values.get(indexMap.get("whiteboard")));
        Assert.assertEquals(qaContact, values.get(indexMap.get("qa_contact")));
        Assert.assertEquals(cfCurrentStatus, values.get(indexMap.get("cf_current_status")));
        Assert.assertEquals(dependsOn, values.get(indexMap.get("depends_on")));
        Assert.assertEquals(dupeOf, values.get(indexMap.get("dupe_of")));
        Assert.assertEquals(cfExpectedFixDate, values.get(indexMap.get("cf_expected_fix_date")));
        Assert.assertEquals(estimatedTime, values.get(indexMap.get("estimated_time")));
        Assert.assertEquals(cfBuildName, values.get(indexMap.get("cf_build_name")));
        Assert.assertEquals(remainingTime, values.get(indexMap.get("remaining_time")));
        Assert.assertEquals(cfTestlinkTc, values.get(indexMap.get("cf_testlink_tc")));
        Assert.assertEquals(qaContactDetail, values.get(indexMap.get("qa_contact_detail")));
        Assert.assertEquals(updateToken, values.get(indexMap.get("update_token")));
        Assert.assertEquals(classification, values.get(indexMap.get("classification")));
        Assert.assertEquals(alias, values.get(indexMap.get("alias")));
        Assert.assertEquals(opSys, values.get(indexMap.get("op_sys")));
        Assert.assertEquals(cfStatusWhiteboard, values.get(indexMap.get("cf_status_whiteboard")));
        Assert.assertEquals(ccDetail, values.get(indexMap.get("cc_detail")));
        Assert.assertEquals(cfResponsible, values.get(indexMap.get("cf_responsible")));
        Assert.assertEquals(platform, values.get(indexMap.get("platform")));
        Assert.assertEquals(cfResponsibleDetail, values.get(indexMap.get("cf_responsible_detail")));
        Assert.assertEquals(flags, values.get(indexMap.get("flags")));
        Assert.assertEquals(version, values.get(indexMap.get("version")));
        Assert.assertEquals(deadline, values.get(indexMap.get("deadline")));
        Assert.assertEquals(actualTime, values.get(indexMap.get("actual_time")));
        Assert.assertEquals(isCreatorAccessible, values.get(indexMap.get("is_creator_accessible")));
        Assert.assertEquals(product, values.get(indexMap.get("product")));
        Assert.assertEquals(isConfirmed, values.get(indexMap.get("is_confirmed")));
        Assert.assertEquals(targetMilestone, values.get(indexMap.get("target_milestone")));
    }

    @Test
    public void testParseFile() {
        String dir = "C:/DATA/Projects/DataSets/Bugzilla";
        String filePath = dir + "/bugzilla-test0.csv";
        CSVParserBugzilla parser = new CSVParserBugzilla(new File(filePath), true);
        parser.process();

        List<List<String>> parsedLines = parser.getParsedLines();
        Assert.assertEquals(9, parsedLines.size());

        Map<String, Integer> headers = parser.getHeaders();
        List<String> parsed1 = parsedLines.get(0);
        Assert.assertEquals("42365", parsed1.get(headers.get("id")));
        Assert.assertEquals("RESOLVED", parsed1.get(headers.get("status")));
        Assert.assertEquals("FIXED", parsed1.get(headers.get("resolution")));
        Assert.assertEquals("normal", parsed1.get(headers.get("severity")));
        Assert.assertEquals("P3", parsed1.get(headers.get("priority")));
        Assert.assertEquals("john.doe@company.com", parsed1.get(headers.get("creator")));
        Assert.assertEquals("mike.smith@company.com", parsed1.get(headers.get("assigned_to")));
        Assert.assertEquals("test-custom-1", parsed1.get(headers.get("component")));
        Assert.assertEquals("[]", parsed1.get(headers.get("blocks")));
        Assert.assertEquals("[]", parsed1.get(headers.get("depends_on")));
        Assert.assertEquals("", parsed1.get(headers.get("dupe_of")));
        Assert.assertEquals(quote("[TEST][Custom] update failed due to an unknown reason\\nBuild failed"), parsed1.get(headers.get("description")));
        Assert.assertEquals("2018-10-31T18:08:34Z", parsed1.get(headers.get("creation_time")));
        Assert.assertEquals("2018-11-02T07:37:37Z", parsed1.get(headers.get("resolution_time")));
        Assert.assertEquals("37", parsed1.get(headers.get("resolution_duration_hours")));
    }
}
