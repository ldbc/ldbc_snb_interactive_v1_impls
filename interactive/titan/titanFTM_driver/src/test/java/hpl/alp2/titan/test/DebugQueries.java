/**
 *
 */
package hpl.alp2.titan.test;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DebugQueries {

    static String actualPath = "C:\\Users\\axelrodm\\Documents\\alp2_ldbc\\titanFTM_driver\\src\\test\\java\\hpl\\alp2\\titan\\test\\validation_params-failed-actual.json";
    static String expectedPath = "C:\\Users\\axelrodm\\Documents\\alp2_ldbc\\titanFTM_driver\\src\\test\\java\\hpl\\alp2\\titan\\test\\validation_params-failed-expected.json";
    String[] columnNames = {"Failed Reason", "Query Number", "Operation", "Expected", "Actual"};
    String[] queryFailedReason = {"Unexpected", "Missing", "Sort order is not the same", "One of the attribute value is not as expected"};


    @Test
    public void testQueries() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("debugQueries.html"));
        Gson gson = new Gson();
        HashMap<Integer, Integer> uniqueKey = new HashMap<>();
        uniqueKey.put(1, 0);
        uniqueKey.put(2, 3);
        uniqueKey.put(3, 0);
        uniqueKey.put(4, 0);
        uniqueKey.put(5, 0);
        uniqueKey.put(6, 0);
        uniqueKey.put(7, 4);
        uniqueKey.put(8, 4);
        uniqueKey.put(9, 3);
        uniqueKey.put(10, 0);
        uniqueKey.put(11, 0);
        uniqueKey.put(12, 0);
        uniqueKey.put(13, 0);
        uniqueKey.put(14, 0);
        Map<Integer, ArrayList<String>> getParameters = new HashMap<>();
        ArrayList<String> queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("firstName");
        queryParams.add("limit");
        getParameters.put(1,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("maxDate");
        queryParams.add("limit");
        getParameters.put(2,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("countryXName");
        queryParams.add("countryYName");
        queryParams.add("startDate");
        queryParams.add("durationDays");
        queryParams.add("limit");
        getParameters.put(3,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("startDate");
        queryParams.add("durationDays");
        queryParams.add("limit");
        getParameters.put(4,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("minDate");
        queryParams.add("limit");
        getParameters.put(5,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("tagName");
        queryParams.add("limit");
        getParameters.put(6,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("limit");
        getParameters.put(7,queryParams);
        //queryParams = new ArrayList<>();
        //queryParams.add("personId");
        //queryParams.add("limit");
        getParameters.put(8,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("maxDate");
        queryParams.add("limit");
        getParameters.put(9,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("month");
        queryParams.add("limit");
        getParameters.put(10,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("countryName");
        queryParams.add("workFromYear");
        queryParams.add("limit");
        getParameters.put(11,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("personId");
        queryParams.add("tagClassName");
        queryParams.add("limit");
        getParameters.put(12,queryParams);
        queryParams = new ArrayList<>();
        queryParams.add("person1Id");
        queryParams.add("person2Id");
        getParameters.put(13,queryParams);
        //queryParams = new ArrayList<>();
        //queryParams.add("person1Id");
        //queryParams.add("person2Id");
        getParameters.put(14,queryParams);

        Map<Integer, ArrayList<String>> getResultParameters = new HashMap<>();
        ArrayList<String> queryResultParams = new ArrayList<>();
        queryResultParams.add("friendId");
        queryResultParams.add("friendLastName");
        queryResultParams.add("distanceFromPerson");
        queryResultParams.add("friendBirthday");
        queryResultParams.add("friendCreationDate");
        queryResultParams.add("friendGender");
        queryResultParams.add("friendBrowserUsed");
        queryResultParams.add("friendLocationIp");
        queryResultParams.add("friendEmails");
        queryResultParams.add("friendLanguages");
        queryResultParams.add("friendCityName");
        queryResultParams.add("friendUniversities");
        queryResultParams.add("friendCompanies");
        getResultParameters.put(1,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personId");
        queryResultParams.add("personFirstName");
        queryResultParams.add("personLastName");
        queryResultParams.add("postOrCommentId");
        queryResultParams.add("postOrCommentContent");
        queryResultParams.add("postOrCommentCreationDate");
        getResultParameters.put(2,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personId");
        queryResultParams.add("personFirstName");
        queryResultParams.add("personLastName");
        queryResultParams.add("xCount");
        queryResultParams.add("yCount");
        queryResultParams.add("count");
        getResultParameters.put(3,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("tagName");
        queryResultParams.add("postCount");
        getResultParameters.put(4,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("forumTitle");
        queryResultParams.add("postCount");
        getResultParameters.put(5,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("tagName");
        queryResultParams.add("postCount");
        getResultParameters.put(6,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personId");
        queryResultParams.add("personFirstName");
        queryResultParams.add("personLastName");
        queryResultParams.add("likeCreationDate");
        queryResultParams.add("commentOrPostId");
        queryResultParams.add("commentOrPostContent");
        queryResultParams.add("minutesLatency");
        queryResultParams.add("isNew");
        getResultParameters.put(7,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personId");
        queryResultParams.add("personFirstName");
        queryResultParams.add("personLastName");
        queryResultParams.add("commentCreationDate");
        queryResultParams.add("commentId");
        queryResultParams.add("commentContent");
        getResultParameters.put(8,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personId");
        queryResultParams.add("personFirstName");
        queryResultParams.add("personLastName");
        queryResultParams.add("commentOrPostId");
        queryResultParams.add("commentOrPostContent");
        queryResultParams.add("commentOrPostCreationDate");
        getResultParameters.put(9,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personId");
        queryResultParams.add("personFirstName");
        queryResultParams.add("personLastName");
        queryResultParams.add("commonInterestScore");
        queryResultParams.add("personGender");
        queryResultParams.add("personCityName");
        getResultParameters.put(10,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personId");
        queryResultParams.add("personFirstName");
        queryResultParams.add("personLastName");
        queryResultParams.add("organizationName");
        queryResultParams.add("organizationWorkFromYear");
        getResultParameters.put(11,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personId");
        queryResultParams.add("personFirstName");
        queryResultParams.add("personLastName");
        queryResultParams.add("tagNames");
        queryResultParams.add("replyCount");
        getResultParameters.put(12,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("shortestPathLength");
        getResultParameters.put(13,queryResultParams);
        queryResultParams = new ArrayList<>();
        queryResultParams.add("personIdsInPath");
        queryResultParams.add("pathWeight");
        getResultParameters.put(14,queryResultParams);

        try (JsonReader readerExpected = new JsonReader(new InputStreamReader(new FileInputStream(new File(expectedPath))))) {
            JsonReader readerActual = new JsonReader(new InputStreamReader(new FileInputStream(new File(actualPath))));
            JsonArray responseExpected = gson.fromJson(readerExpected, JsonArray.class);
            JsonArray responseActual = gson.fromJson(readerActual, JsonArray.class);
            int N = responseExpected.size();
            if (responseExpected != null) {

                for (int i = 0; i < responseExpected.size(); i++) {
                    ArrayList<String> unexpected = new ArrayList<>();
                    ArrayList<String> missing = new ArrayList<>();
                    JsonArray operationExpected = responseExpected.get(i).getAsJsonObject().getAsJsonArray("operation");
                    JsonArray resultExpected = responseExpected.get(i).getAsJsonObject().getAsJsonArray("result");
                    JsonArray resultActual = responseActual.get(i).getAsJsonObject().getAsJsonArray("result");
                    String query = operationExpected.get(0).getAsString();
                    String queryNumber = query.substring(query.indexOf("LdbcQuery") + 9, query.length());
                    //Class clazz = Class.forName(query);
                    //Method method = clazz.getMethod("getParameters", null);
                    ArrayList<String> queryParameters = getParameters.get(Integer.valueOf(queryNumber));//ArrayList<String>) method.invoke(null, null);
                   // Method queryResults = clazz.getMethod("getResultParameters", null);
                    ArrayList<String> queryParametersResults = getResultParameters.get(Integer.valueOf(queryNumber));//ArrayList<String>) queryResults.invoke(null, null);
                    String params = "";
                    for (int j = 0; j < queryParameters.size(); j++) {
                        params += queryParameters.get(j) + "=" + operationExpected.get(j + 1).getAsString() + "\t";
                    }
                    //checking the set level: missing or unexpected
                    int index = uniqueKey.get(Integer.valueOf(queryNumber));
                    for (int k = 0; k < resultActual.size(); k++) {
                        boolean equals = false;
                        JsonArray resultAct = resultActual.get(k).getAsJsonArray();
                        String act = resultAct.get(index).toString();
                        for (int j = 0; j < resultExpected.size(); j++) {
                            JsonArray resultExp = resultExpected.get(j).getAsJsonArray();
                            String exp = resultExp.get(index).toString();
                            if (act.equals(exp)) {
                                equals = true;
                                break;
                            }
                        }
                        if (!equals) { //the result is in resultActual but not in resultExpected = Unexpected
                            String temp = queryParametersResults.get(index) + "=" + resultAct.get(index).toString();
                            unexpected.add(temp);
                        }
                    }
                    for (int k = 0; k < resultExpected.size(); k++) {
                        boolean equals = false;
                        JsonArray resultExp = resultExpected.get(k).getAsJsonArray();
                        String exp = resultExp.get(index).toString();
                        for (int j = 0; j < resultActual.size(); j++) {
                            JsonArray resultAct = resultActual.get(j).getAsJsonArray();
                            String act = resultAct.get(index).toString();
                            if (exp.equals(act)) {
                                equals = true;
                                break;
                            }
                        }
                        if (!equals) { //the result is in resultExpected but not in resultActual = Missing
                            String temp = queryParametersResults.get(index) + "=" + resultExp.get(index).toString();
                            missing.add(temp);
                        }
                    }
                    boolean missingFlag = false;
                    if (!missing.isEmpty()) {
                        writeToHtml(N, bw, i, queryFailedReason[1], queryNumber, params, missing.toString(), null);
                        missingFlag = true;
                    }
                    if (!unexpected.isEmpty()) {
                        if (missingFlag) {
                            writeToHtml(N, bw, i + 1, queryFailedReason[0], "-''-", "-''-", null, unexpected.toString());
                        } else {
                            writeToHtml(N, bw, i, queryFailedReason[0], queryNumber, params, null, unexpected.toString());
                        }
                    }
                    //checking the sort level: sort order is not the same
                    if (missing.isEmpty() && unexpected.isEmpty()) {
                        ArrayList<JsonArray> resultActOrder = new ArrayList<>();
                        ArrayList<JsonArray> resultExpOrder = new ArrayList<>();
                        for (int k = 0; k < resultActual.size(); k++) {
                            JsonArray resultActTemp = new JsonArray();
                            JsonArray resultExpTemp = new JsonArray();
                            JsonArray resultAct = resultActual.get(k).getAsJsonArray();
                            String act = resultAct.get(index).toString();
                            JsonArray resultExp = resultExpected.get(k).getAsJsonArray();
                            String exp = resultExp.get(index).toString();
                            if (!act.equals(exp)) {
                                resultExpTemp.add(new JsonPrimitive(queryParametersResults.get(index) + "=" + exp));
                                resultActTemp.add(new JsonPrimitive(queryParametersResults.get(index) + "=" + act));
                                resultActOrder.add(resultActTemp);
                                resultExpOrder.add(resultExpTemp);
                            }
                        }
                        boolean sort = false;
                        if (!resultActOrder.isEmpty()) {
                            writeToHtml(N, bw, i, queryFailedReason[2], queryNumber, params, resultExpOrder.toString(), resultActOrder.toString());
                            sort = true;
                        }

                        //checking the record level: for key-matching pairs, one of the non-key attributes is not as expected
                        boolean moreThenID = false;
                        for (int k = 0; k < resultActual.size(); k++) {
                            String resAct = "";
                            String resExp = "";
                            JsonArray resultAct = resultActual.get(k).getAsJsonArray();
                            String act = resultAct.get(index).toString();
                            for (int j = 0; j < resultExpected.size(); j++) {
                                JsonArray resultExp = resultExpected.get(j).getAsJsonArray();
                                String exp = resultExp.get(index).toString();
                                if (act.equals(exp)) {
                                    moreThenID = false;
                                    resAct += queryParametersResults.get(index) + "=" + act + "\t";
                                    resExp += queryParametersResults.get(index) + "=" + exp + "\t";
                                    for (int n = 0; n < resultAct.size(); n++) {
                                        if (n != index) {
                                            String tempAct = resultAct.get(n).toString();
                                            String tempExp = resultExp.get(n).toString();
                                            if (!tempAct.equals(tempExp)) {
                                                resAct += queryParametersResults.get(n) + "=" + tempAct + "\t";
                                                resExp += queryParametersResults.get(n) + "=" + tempExp + "\t";
                                                moreThenID = true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (sort) { //if failed reason is sort level and also record level
                                if(moreThenID) {
                                    writeToHtml(N, bw, i + 1, queryFailedReason[3], "-''-", "-''-", resExp, resAct);
                                }
                            } else {
                                writeToHtml(N, bw, i, queryFailedReason[3], queryNumber, params, resExp, resAct);
                            }
                        }
                    }
                }
                bw.close();
            }
        }catch(Exception e){

            e.printStackTrace();
        }
        }



    public void writeToHtml(int N, BufferedWriter bw, int i, String failedReason, String query, String params, String expected, String actual) throws IOException {
        try {
            StringBuilder build = new StringBuilder();
            if (i == 0) {
                build.append("<!DOCTYPE html>");
                build.append("<html lang=\"en\">");
                build.append("<head><title>Debug Queries</title></head>");
                build.append("<body>");
                build.append("<table border=\"1\" cellspacing=\"1\" cellpadding=\"5\">");
                build.append("<tr>");
                for (String column : columnNames)
                    build.append("<td>").append(column).append("</td>");

                build.append("</tr>");
            }
            build.append("<tr>");
            build.append("<td>").append(failedReason).append("</td>");
            build.append("<td>").append(query).append("</td>");
            build.append("<td>").append(params).append("</td>");
            build.append("<td>").append(expected).append("</td>");
            build.append("<td>").append(actual).append("</td>");
            build.append("</tr>");
            String html = build.toString();
            System.out.println(html);
            bw.write(html);
            bw.flush();
            if (i == N) {
                build.append("</table></body>");
                build.append("</html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}