package com.lib.collections.core.classes;

import java.util.Date;

/**
 * Created by nikzz on 27/10/17.
 */
public class Message {
    private String messageId;
    private String message;
    private Date createDate;

    /**
     *
     */
    public Message(){
        this(null, null, null);
    }

    public Message(String messageId, String message){
        this(messageId, message,null);
    }

    /**
     * @param messageId
     * @param message
     * @param createDate
     */
    public Message(String messageId, String message, Date createDate) {
        this.messageId = messageId;
        this.message = message;
        this.createDate = createDate;
    }

    /**
     * @return
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!getMessageId().equals(message1.getMessageId())) return false;
        if (!getMessage().equals(message1.getMessage())) return false;
        return getCreateDate().equals(message1.getCreateDate());
    }

    @Override
    public int hashCode() {
        int result = getMessageId().hashCode();
        result = 31 * result + getMessage().hashCode();
        result = 31 * result + getCreateDate().hashCode();
        return result;
    }
}
