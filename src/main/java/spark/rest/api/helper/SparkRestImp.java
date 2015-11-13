/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spark.rest.api.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.rest.api.bean.BatchServiceKey;
import spark.rest.api.bean.ExtractSparkInputBean;


/**
 *
 * @author ranjeet
 */
public class SparkRestImp {

    private static final Logger logger = LoggerFactory.getLogger(SparkRestImp.class);
    private final String BASE_URL;
    Helper helper = new Helper();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SparkRestImp() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //cluster rest url
        BASE_URL = helper.getKeyPropValues("BASE_URL"); //"http://192.168.2.152:6066/";
        logger.info(BatchServiceKey.BASE_URL, BASE_URL);
    }

    /**
     * *
     * Submit spark job using rest API
     *
     * @param input : from user and main class for resume or JD to run
     * @param mainClass : Pass either resume or JD main class
     * @return JSON string for REST API call
     * @throws java.io.IOException     */
    public String submitSparkRequest(ExtractSparkInputBean input, String mainClass) throws IOException, JSONException {

        String inputFile = input.getInputPath();
        String outFile = input.getOutputPath();
        logger.info(BatchServiceKey.MESSAGE, inputFile);
        logger.info(BatchServiceKey.MESSAGE, outFile);

        //get config file  spark-batch-service-config setting
        Properties properties = helper.getPropValues();

        JSONObject obj = new JSONObject();
        obj.put("action", "CreateSubmissionRequest");
        JSONArray appArgs = new JSONArray();
        appArgs.put(outFile);
        appArgs.put(inputFile);

        obj.put("appArgs", appArgs);
        obj.put("appResource", properties.getProperty("APPLICATION_JAR_PATH"));
        obj.put("clientSparkVersion", properties.getProperty("CLIENT_SPARK_VERSION")); //"1.5.1"
        obj.put("mainClass", mainClass);

        //spark prop
        JSONObject sparkProperties = new JSONObject();
        sparkProperties.put("spark.jars", properties.getProperty("DEPENDENCY_JAR_PATH"));
        sparkProperties.put("spark.driver.supervise", properties.getProperty("SPARK_DRIVER_SUPERVISE"));
        sparkProperties.put("spark.app.name", mainClass);
        sparkProperties.put("spark.eventLog.enabled", properties.getProperty("SPARK_EVENTLOG_ENABLED"));
        sparkProperties.put("spark.executor.memory", properties.getProperty("SPARK_EXECUTOR_MEMORY"));
        sparkProperties.put("spark.driver.cores", properties.getProperty("SPARK_DRIVER_CORES"));
        sparkProperties.put("spark.driver.memory", properties.getProperty("SPARK_DRIVER_MEMORY"));
        sparkProperties.put("spark.submit.deployMode", properties.getProperty("SPARK_SUBMIT_DEPLOYMODE"));//"cluster"
        sparkProperties.put("spark.master", properties.getProperty("SPARK_MASTER"));//"spark://192.168.2.152:6066");
        obj.put("sparkProperties", sparkProperties);

        //added env variable
        JSONObject environmentVariables = new JSONObject();
        environmentVariables.put("SPARK_ENV_LOADED", properties.getProperty("SPARK_ENV_LOADED"));
        obj.put("environmentVariables", environmentVariables);

        String jsonText = obj.toString();
        logger.info(BatchServiceKey.MESSAGE, jsonText);

        //path to for spark submit job
        String path = "/v1/submissions/create";
        String str = Helper.<String>postApi(BASE_URL, path, jsonText);
       
        logger.info(BatchServiceKey.MESSAGE, "CreateSubmissionResponse json : " + str);
        return str;

    }

    /**
     * Kill submitted Spark job using driver Id
     *
     * @param submissionId : submitted spark job submissionId
     * @return JSON
     * @throws java.io.IOException
     */
    public String KillSubmittedApplication(String submissionId) throws IOException {
        logger.info(BatchServiceKey.MESSAGE, "got submissionId :" + submissionId);
        String path = "/v1/submissions/kill/" + submissionId;
        logger.info(BatchServiceKey.MESSAGE, path);

        String jsonResponse = Helper.postApi(BASE_URL, path, "");
        logger.info(BatchServiceKey.MESSAGE, "KillSubmittedApplication jsonResponse : " + jsonResponse);

        return jsonResponse;

    }

    /**
     * *
     * Get status of spark job using driver id
     *
     * @param submissionId :submitted spark job submissionId
     * @return JSON
     * @throws java.io.IOException
     */
    public String StatusResponse(String submissionId) throws IOException {
        logger.info(BatchServiceKey.MESSAGE, "got submissionId : " + submissionId);
        String path = "/v1/submissions/status/" + submissionId;
        logger.info(BatchServiceKey.MESSAGE, "path" + path);

        String jsonResponse = Helper.getApi(BASE_URL, path);
        
        logger.info(BatchServiceKey.MESSAGE, "SubmissionStatusResponse json :" + jsonResponse);

        return jsonResponse;
    }

}
