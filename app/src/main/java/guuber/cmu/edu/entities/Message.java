package guuber.cmu.edu.entities;

/**
 * Created by lunwenh on 4/8/2016.
 */
/**
 * This class defines one message
 * */
public class Message {
    private String sender;
    private String receiver;
    private String content;
    private String timeStamp;

    public Message(
            String sender,
            String receiver,
            String content,
            String timeStamp
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
