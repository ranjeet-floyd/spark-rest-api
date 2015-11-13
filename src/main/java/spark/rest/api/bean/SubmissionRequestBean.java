/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spark.rest.api.bean;

import java.util.List;

/**
 *
 * @author ranjeet
 */
public class SubmissionRequestBean {

    public String masterUrl;
    public List<String> appArgs;
    public String appResource;
    public String clientSparkVersion;
    public String mainClass;
    public List<EnvironmentVariablesBean> environmentVariables;
    public List<SparkPropertiesBean> sparkProperties;

    public SubmissionRequestBean() {
    }

    public SubmissionRequestBean(String masterUrl, List<String> appArgs, String appResource, String clientSparkVersion, String mainClass, List<EnvironmentVariablesBean> environmentVariables, List<SparkPropertiesBean> sparkProperties) {
        this.masterUrl = masterUrl;
        this.appArgs = appArgs;
        this.appResource = appResource;
        this.clientSparkVersion = clientSparkVersion;
        this.mainClass = mainClass;
        this.environmentVariables = environmentVariables;
        this.sparkProperties = sparkProperties;
    }

    
    
    public String getMasterUrl() {
        return masterUrl;
    }

    public void setMasterUrl(String masterUrl) {
        this.masterUrl = masterUrl;
    }

    public List<String> getAppArgs() {
        return appArgs;
    }

    public void setAppArgs(List<String> appArgs) {
        this.appArgs = appArgs;
    }

    public String getAppResource() {
        return appResource;
    }

    public void setAppResource(String appResource) {
        this.appResource = appResource;
    }

    public String getClientSparkVersion() {
        return clientSparkVersion;
    }

    public void setClientSparkVersion(String clientSparkVersion) {
        this.clientSparkVersion = clientSparkVersion;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public List<EnvironmentVariablesBean> getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariables(List<EnvironmentVariablesBean> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public List<SparkPropertiesBean> getSparkProperties() {
        return sparkProperties;
    }

    public void setSparkProperties(List<SparkPropertiesBean> sparkProperties) {
        this.sparkProperties = sparkProperties;
    }

}
