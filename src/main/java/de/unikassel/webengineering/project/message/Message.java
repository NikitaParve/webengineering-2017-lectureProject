package de.unikassel.webengineering.project.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.unikassel.webengineering.project.user.User;

import javax.persistence.*;

import java.util.Date;

/**
 * Datenmodell für Nachrichten (werden innerhalb von Chats genutzt)
 *
 * @author Luan Hajzeraj on 08.07.2017.
 */

@Entity(name = "Message_")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "AUTHOR_ID")
    private User author;

    @OneToOne
    @JoinColumn(name = "TO_USER_ID")
    private User toUser;

    private Date timestamp;
    private String message;

    private boolean isRead = false;

    public Message() {

    }

    @JsonIgnore
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    @PrePersist
    public void prePersistent() {
        timestamp = new Date();
    }
}
