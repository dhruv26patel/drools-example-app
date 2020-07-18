package com.example.droolsexample.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AmazonService {

    private static final Logger logger = LoggerFactory.getLogger(AmazonService.class);

    @Autowired private AmazonS3 amazonS3Client;

    @Autowired private ConfigurableApplicationContext applicationContext;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    public S3Object downloadFileFromS3bucket(String fileName, String bucketName) {
        S3Object object = amazonS3Client.getObject(bucketName, fileName);

        return object;
    }

    @JmsListener(destination = "rules-queue")
    public void receive(String message) throws JMSException {
        logger.info("Received message {}", message);

        downloadFileS3("pets.drl", bucketName);
        removeExistingAndAddNewBean("getKieContainer");
    }

    public void removeExistingAndAddNewBean(String beanId) {

        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        registry.removeBeanDefinition(beanId);

        BeanDefinitionBuilder messageProcessorBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(KieContainer.class);

        registry.registerBeanDefinition(beanId, messageProcessorBuilder.getBeanDefinition());
    }

    private void downloadFileS3(String fileName, String bucketName) {
        final S3Object o = downloadFileFromS3bucket(fileName, bucketName);
        try {
            S3ObjectInputStream s3is = o.getObjectContent();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File("src/main/resources/pets.drl");
            FileOutputStream fos = new FileOutputStream(file);

            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
