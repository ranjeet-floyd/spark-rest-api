/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spark.rest.api.bean;

/**
 *
 * @author ranjeet
 */
public class SparkPropertiesConst {

    public final static String spark_jars = "spark_jars";
    public final static String is_spark_driver_supervise = "spark.driver.supervise";
    public final static String spark_app_name = "spark.app.name";
    public final static String is_spark_eventLog_enabled = "spark.eventLog.enabled";
    public final static String spark_submit_deployMode = "spark.submit.deployMode";
    public final static String spark_master = "spark.master";
    
    public final static String  spark_submit_deployMode_cluster = "cluster";
    public final static String  spark_submit_deployMode_client = "client";
}
