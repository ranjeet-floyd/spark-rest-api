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
public class EnvironmentVariablesBean {
    public String key;
    public String value;

    public EnvironmentVariablesBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public EnvironmentVariablesBean() {
    }
    
    

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}