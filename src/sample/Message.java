package sample;

import java.io.Serializable;

/**
 * Serializable là cơ chế ghi trạng thái đối tượng Message vào byte stream và sau đó có thể phục hồi lại,
 * dùng để truyền các Message giữa các socket với nhau.
 */
public class Message implements Serializable {
    private String messageContent;
    private String sender;
    private String receiver;

    public Message() {}

    public Message(String sender, String receiver, String messageContent) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
