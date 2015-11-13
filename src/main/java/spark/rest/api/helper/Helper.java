/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spark.rest.api.helper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author ranjeet
 */
public class Helper {

    InputStream inputStream;

    public String getKeyPropValues(String key) throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "spark-batch-service-config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            return prop.getProperty(key);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return null;
    }

    public Properties getPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "spark-batch-service-config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            return prop;

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return null;
    }
    /**
     * *
     * Get API call using Jersey client
     *
     * @param url : base API url
     * @param path : resource path
     * @return output as
     */
    public static String getApi(String url, String path) {
        Client client = Client.create();
        WebResource webResourc = client.resource(url).path(path);

        ClientResponse response = webResourc.accept("application/json")
                .get(ClientResponse.class);
        String output = response.getEntity(String.class);

        return output;
    }

    /**
     * *
     * Get API call using Jersey client
     *
     * @param <T> : generic data class object
     * @param uri : base API Uri
     * @param path : resource path
     * @param t : class object | post data
     * @return output as
     */
    public static <T> String postApi(String uri, String path, T t) {

        Client client = Client.create();
        WebResource webResourc = client.resource(uri).path(path);

        ClientResponse response = webResourc.accept("application/json")
                .post(ClientResponse.class, t);
        String output = response.getEntity(String.class);

        return output;
    }

}
