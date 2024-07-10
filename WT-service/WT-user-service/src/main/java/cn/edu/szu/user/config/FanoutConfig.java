package cn.edu.szu.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("amq.fanout");
    }
    @Bean("error_direct")
    public  DirectExchange errorMessageExchange(){
        return new DirectExchange("error_direct");
    }
    @Bean("error_queue")
    public Queue errorQueue(){
        return new Queue("error_queue",true);
    }
    @Bean
    public Binding bindingerror(DirectExchange error_direct, Queue error_queue){
        return  BindingBuilder.bind(error_queue).to(error_direct).with("error");
    }
    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate,"error_direct","error");
    }
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    @Bean
    public Binding fanoutBinding(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder
                .bind(fanoutQueue1)
                .to(fanoutExchange);
    }

//    @Bean
//    public Queue fanoutQueue2(){
//        return new Queue("amq.queue2");
//    }
//
//    @Bean
//    public Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange){
//        return BindingBuilder
//                .bind(fanoutQueue2)
//                .to(fanoutExchange);
//    }

}
