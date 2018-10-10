package robert.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NOTE")
public class Note extends BasicEntity {

    private String text;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private Date creationDate;

    public Note() {
    }

    public Note(String text) {
        this.text = stripNote(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = stripNote(text);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    private String stripNote(String text) {
        return StringUtils.strip(text, "\"");
    }

    @Override
    public String toString() {
        return "Note{" +
                "text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
