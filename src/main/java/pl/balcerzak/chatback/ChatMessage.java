package pl.balcerzak.chatback;

import lombok.Data;

@Data
public class ChatMessage {
    private String value;
    private String user;
    private String date;

    public ChatMessage(String value, String user, String date) {
        this.value = value;
        this.user = user;
        this.date = date;
    }

    public ChatMessage() {
    }
}
