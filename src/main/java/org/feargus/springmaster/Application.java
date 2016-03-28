package org.feargus.springmaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
// , SecurityAutoConfiguration.class })
@ComponentScan
// @SpringBootApplication //same as other three put together
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
	SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
	log.info("STARTING RUN....");
    }
}
