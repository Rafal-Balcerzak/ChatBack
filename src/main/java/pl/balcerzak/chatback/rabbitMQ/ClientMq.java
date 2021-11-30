package pl.balcerzak.chatback.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@CrossOrigin("*")
public class ClientMq {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/receiveMessage")
    public String get() {
        Object message = rabbitTemplate.receiveAndConvert("topic");

        System.out.println(Objects.requireNonNull(message));
        return Objects.requireNonNull(message).toString();

    }

    /*@RabbitListener(queues = "topic")
    public void rabbitListener(String message) {
        System.out.println(message);
    }*/
}
