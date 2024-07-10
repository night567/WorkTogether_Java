package cn.edu.szu.user.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class SpringRabbitListener {
    @Autowired
    private JavaMailSender javaMailSender;
    @RabbitListener(queues = "fanout.queue1")
    public void listenerWorkQueue(String msg) throws InterruptedException {//装的什么接收什么
        String str[] = msg.split(";");
        System.out.println("开始发送");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2839468956@qq.com");
        message.setTo(str[0]);
        message.setSubject("验证码");
        message.setText("您的验证码是：" + str[1]);
        javaMailSender.send(message);
        System.out.println("发送成功");
    }
    @RabbitListener(queues = "error_queue")
    public void ErrorQueue(String msg) throws InterruptedException {//装的什么接收什么
        System.out.println("邮箱服务器有误，请及时处理");
//        TODO  存储日志到数据库，通知管理员

    }
}
