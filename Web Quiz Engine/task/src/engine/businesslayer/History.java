package engine.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "History", indexes = {
        @Index(name = "idx_history_username_quiz_id", columnList = "username, quiz_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @Column(name = "username")
    private String username;

    @JsonProperty("id")
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public History(String username, Long quizId, LocalDateTime completedAt) {
        this.username = username;
        this.quizId = quizId;
        this.completedAt = completedAt;
    }
}
