package robert.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NOTE")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Note extends BasicEntity {

    private String text;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private Date creationDate;

    public Note(String text) {
        this.text = stripNote(text);
    }

    private String stripNote(String text) {
        return StringUtils.strip(text, "\"");
    }

}
