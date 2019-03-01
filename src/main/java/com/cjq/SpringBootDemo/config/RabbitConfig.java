package com.cjq.SpringBootDemo.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: cuijq
 */
@Configuration
public class RabbitConfig {


    @Bean("queue")
    public Queue queue(){
        return new Queue("hello");
    }

    @Bean("queue1")
    public Queue queue1(){
        return new Queue("Cui.queue1");
    }

    @Bean("queue2")
    public Queue queue2(){
        return new Queue("Cui.queue2");
    }


    @Bean
    public DirectExchange directExchange(){
        return  new DirectExchange("Cui.DirectExchange");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
          return  new FanoutExchange("Cui.FanoutExchange");
    }

    @Bean
    public TopicExchange topicExchange(){
        return  new TopicExchange("Cui.TopicExchange");
    }


    @Bean
    public Binding fanoutBinding111(@Qualifier("queue")Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("helloRouting");
    }

    @Bean
    public Binding fanoutBinding(@Qualifier("queue1")Queue queue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding topicBinding(@Qualifier("queue1")Queue queue, TopicExchange topicExchange){
        //“#”表示0个或若干个关键字，“*”表示一个关键字（注意：一定要有不能为0个）。如“log.*”能与“log.warn”匹配，无法与“log.warn.timeout”匹配；但是“log.#”能与上述两者匹配。
        return BindingBuilder.bind(queue).to(topicExchange).with("Cui.#");
    }

    @Bean
    public Binding topicBinding2(@Qualifier("queue2")Queue queue, TopicExchange topicExchange){
        //“#”表示0个或若干个关键字，“*”表示一个关键字（注意：一定要有不能为0个）。如“log.*”能与“log.warn”匹配，无法与“log.warn.timeout”匹配；但是“log.#”能与上述两者匹配。
        return BindingBuilder.bind(queue).to(topicExchange).with("Cui.jia.*");
    }

}
