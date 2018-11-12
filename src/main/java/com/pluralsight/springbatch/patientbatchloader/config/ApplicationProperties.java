package com.pluralsight.springbatch.patientbatchloader.config;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Patient Batch Loader.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Batch batch = new Batch();

    public Batch getBatch() {

        return batch;

    }

    private static class Batch {

        private String inputPath = "/Users/brucephillips/git/patient-batch-loader/data";

        public String getInputPath() {
            return inputPath;
        }

        public void setInputPath(String inputPath) {
            this.inputPath = inputPath;
        }
    }


}
