package pl.balcerzak.chatback.rabbitMQ;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.balcerzak.chatback.ChatMessage;

@RestController
@CrossOrigin("*")
public class PublisherMq {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*@PostMapping("/addMessage")
    public String get(@RequestParam String user,
                      @RequestParam String message,
                      @RequestParam String date) {
        String messageToSend = "Name: " + user +
                                " Message: " + message +
                                " Date: " + date;
        rabbitTemplate.convertAndSend("topic", messageToSend);
        return "sent" ;
    }*/

    @PostMapping("/addMessage")
    public void post(@RequestBody ChatMessage message) {
        Gson gson = new Gson();
        rabbitTemplate.convertAndSend("topic", gson.toJson(message));
    }
}
