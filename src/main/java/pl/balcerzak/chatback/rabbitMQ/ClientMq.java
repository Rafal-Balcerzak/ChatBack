package pl.balcerzak.chatback.rabbitMQ;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
public class ClientMq {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    private final FanoutExchange fanoutExchange = new FanoutExchange("MyExchange");
    private Map<String, Queue> usersQueueMap = new HashMap<>();

    @GetMapping("/receiveMessage")
    public String get(@RequestParam String user) {

        if (usersQueueMap.get(user) == null) {
            usersQueueMap.put(user, new Queue(user));
            bindeQueue(usersQueueMap.get(user));
        }
        Object message = rabbitTemplate.receiveAndConvert(user);

        if (message == null) {
            return "Brak wiadomośći w kolejce BACKEND";
        }
        return Objects.requireNonNull(message).toString();

    }

    public void bindeQueue(Queue queue) {
        amqpAdmin.declareExchange(fanoutExchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
    }

    /*@RabbitListener(queues = "topic")
    public void rabbitListener(String message) {
        System.out.println(message);
    }*/
}
