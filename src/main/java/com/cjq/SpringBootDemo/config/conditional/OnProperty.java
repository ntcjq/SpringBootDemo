package com.cjq.SpringBootDemo.config.conditional;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "conditional", name = "onProperty", havingValue = "open", matchIfMissing = true)
public class OnProperty implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("====================OnProperty run");

    }
}
