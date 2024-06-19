package cn.edu.szu.user;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test(){
        String exchangeName = "amq.fanout";
        String msg = "hello every one";
        rabbitTemplate.convertAndSend(exchangeName,"test",msg);
    }
}
