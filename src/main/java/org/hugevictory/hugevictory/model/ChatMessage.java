package org.hugevictory.hugevictory.model;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String target;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        DIRECTED
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}