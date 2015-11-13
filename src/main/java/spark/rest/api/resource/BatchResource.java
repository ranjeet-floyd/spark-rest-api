/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spark.rest.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.rest.api.bean.BatchServiceKey;
import spark.rest.api.bean.ExtractSparkInputBean;
import spark.rest.api.helper.Helper;
import spark.rest.api.helper.SparkRestImp;

/**
 *
 * @author ranjeet
 */
@Path("/core")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BatchResource {

    private static final Logger logger = LoggerFactory.getLogger(BatchResource.class);

    @GET
    @Path("/test")
    public String Hello() {
        logger.info("Test REST services");
        return "Hello ..Ok";
    }

    /**
     * *
     * Batch extraction for Resume. Input location: Path in distributed file
     * system where each resume is stored as one document. * Output location:
     * Path where extracted content for each resume will be stored as individual
     * files. (We can support any other format that is convenient to consumers)
     *
     * @param input : object for inputPath and outputPath
     * @return Request ID using which the status of bulk extraction can be
     * tracked.
     */
    @Path("/extractCVTokens/_batch")
    @POST
    public Response extractCVTokens(final ExtractSparkInputBean input) {
        logger.info("API for /extractCVTokens/_batch");
        String jsonOutput;
        Response.Status status = Response.Status.OK;
        try {
            String mainClass = (new Helper()).getKeyPropValues("RESUME_EXTRACTION_MAIN_CLASS");
            logger.info(BatchServiceKey.MESSAGE, "mainClass :" + mainClass);

            if (mainClass == null || mainClass.isEmpty()) {
                logger.error(BatchServiceKey.EXCEPTION, "config for resume extraction main Class is not configured !!");
                throw new NullPointerException("config for resume extraction main Class is not configured !!");
            }
            if (input != null && input.getInputPath() != null && !input.getInputPath().isEmpty()
                    && input.getOutputPath() != null && !input.getOutputPath().isEmpty()) {

                logger.info(BatchServiceKey.INPUT_FILE, input.getInputPath());
                logger.info(BatchServiceKey.OUTPUT_FILE, input.getOutputPath());

                SparkRestImp sh = new SparkRestImp();

                jsonOutput = sh.submitSparkRequest(input, mainClass);
            } else {

                jsonOutput = "inputPath or/and outputPath is/are missing.!! ";
                status = Response.Status.NOT_ACCEPTABLE;
                logger.warn(BatchServiceKey.WARN_RESPONSE, jsonOutput);
            }

        } catch (Exception ex) {
            logger.error(BatchServiceKey.ERROR_RESPONSE, ex);
            status = Response.Status.INTERNAL_SERVER_ERROR;
            jsonOutput = "Opps...Server Error !!";//ex.getMessage();
        }

        logger.debug(BatchServiceKey.RESPONSE, jsonOutput);
        return Response.status(status).entity(jsonOutput).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * *
     * Batch extraction for JD. Input location: Path in distributed file system
     * where each JD is stored as one document. * Output location: Path where
     * extracted content for each JD will be stored as individual files. (We can
     * support any other format that is convenient to consumers) Each file will
     * have a JSON response representing extraction of single JD
     *
     * @param input
     * @return Request ID using which the status of bulk extraction can be
     * tracked.
     */
    @Path("/extractJDTokens/_batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response extractJDTokens(final ExtractSparkInputBean input) {
        logger.info("API for /extractJDTokens/_batch");
        String jsonOutput;
        Response.Status status = Response.Status.OK;
        try {
            String mainClass = (new Helper()).getKeyPropValues("JD_EXTRACTION_MAIN_CLASS");
            logger.info(BatchServiceKey.MAIN_CLASSS, mainClass);

            if (mainClass == null || mainClass.isEmpty()) {
                String msg = "config for resume extraction main Class is not configured !!";
                logger.error(BatchServiceKey.EXCEPTION, msg);
                throw new NullPointerException("config for JD extraction main Class is not configured !!");
            }

            //check for input from user
            if (input != null && input.getInputPath() != null && !input.getInputPath().isEmpty()
                    && input.getOutputPath() != null && !input.getOutputPath().isEmpty()) {
                logger.info(BatchServiceKey.INPUT_FILE, input.getInputPath());
                logger.info(BatchServiceKey.OUTPUT_FILE, input.getInputPath());

                SparkRestImp sh = new SparkRestImp();
                jsonOutput = sh.submitSparkRequest(input, mainClass);
            } else {
                jsonOutput = "inputPath or/and outputPath is/are missing.!! ";
                status = Response.Status.NOT_ACCEPTABLE;
                logger.warn(BatchServiceKey.WARN_RESPONSE, jsonOutput);
            }

        } catch (Exception ex) {
            logger.error(BatchServiceKey.ERROR_RESPONSE, ex);
            status = Response.Status.INTERNAL_SERVER_ERROR;
            jsonOutput = ex.getMessage();
        }
        logger.debug(BatchServiceKey.RESPONSE, jsonOutput);
        return Response.status(status).entity(jsonOutput).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Tracking spark job status using rsubmissionId:
     *
     * @param submissionId : return by Batch extraction APIs
     * @return
     */
    @Path("status/_batch/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response trackingStatus(@PathParam("id") String submissionId) {
        logger.info("API for /extractCVTokens/_batch");

        String jsonOutput;
        Response.Status status = Response.Status.OK;
        try {
            if (submissionId != null && !submissionId.isEmpty()) {
                logger.info(BatchServiceKey.MESSAGE, "submissionId :" + submissionId);
                SparkRestImp sh = new SparkRestImp();
                jsonOutput = sh.StatusResponse(submissionId);

            } else {
                jsonOutput = "submissionId not be empty or null";
                status = Response.Status.NOT_ACCEPTABLE;
                logger.warn(BatchServiceKey.MESSAGE, jsonOutput);
            }
        } catch (Exception ex) {
            logger.error(BatchServiceKey.EXCEPTION, ex);
            status = Response.Status.INTERNAL_SERVER_ERROR;
            jsonOutput = ex.getMessage();
        }
        logger.debug(BatchServiceKey.RESPONSE, jsonOutput);
        return Response.status(status).entity(jsonOutput).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Kill running spark job using submissionId:
     *
     * @param submissionId : return by Batch extraction APIs
     * @return
     */
//    @Path("kill/_batch/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @GET
//    public Response KillSubmittedJob(@PathParam("id") String submissionId) {
//        String jsonOutput;
//        Response.Status status = Response.Status.OK;
//        try {
//            if (submissionId != null && !submissionId.isEmpty()) {
//                SparkHelper sh = new SparkHelper();
//                jsonOutput = sh.KillSubmittedApplication(submissionId);
//            } else {
//                jsonOutput = "submissionId not be empty or null";
//                status = Response.Status.NOT_ACCEPTABLE;
//            }
//        } catch (Exception ex) {
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            jsonOutput = ex.getMessage();
//        }
//        return Response.status(status).entity(jsonOutput).header("Access-Control-Allow-Origin", "*").build();
//    }
}
