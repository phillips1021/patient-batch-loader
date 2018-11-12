package com.pluralsight.springbatch.patientbatchloader.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
@EnableBatchProcessing
public class BatchConfiguration  implements BatchConfigurer {

    private JobRepository jobRepository;

    private JobExplorer jobExplorer;

    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value="batchTransactionManager")
    private PlatformTransactionManager batchTransactionManager;

    @Autowired
    @Qualifier(value="batchDataSource")
    private DataSource batchDataSource;

    @Override
    public JobRepository getJobRepository() throws Exception {
        return jobRepository;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return batchTransactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        return jobExplorer;
    }

    protected JobLauncher createJobLauncher() throws Exception {

        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

        jobLauncher.setJobRepository(jobRepository);

        jobLauncher.setJobRepository(jobRepository);

        jobLauncher.afterPropertiesSet();

        return jobLauncher;

    }

    protected JobRepository createJobRepository() throws Exception {

        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();

        factory.setDataSource(batchDataSource);

        factory.setTransactionManager( getTransactionManager() );

        factory.afterPropertiesSet();

        return factory.getObject();


    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {

        this.jobRepository = createJobRepository();

        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();

        jobExplorerFactoryBean.setDataSource( batchDataSource);

        jobExplorerFactoryBean.afterPropertiesSet();

        this.jobExplorer = jobExplorerFactoryBean.getObject();

        this.jobLauncher = createJobLauncher();

    }
}
