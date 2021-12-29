package engine.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "title is mandatory")
    private String title;
    @NotBlank(message = "text is mandatory")
    private String text;

    @NotEmpty
    @Size(min = 2, message = "options should contain at least 2 items")
    @ElementCollection
    @Column(name = "option")
    @CollectionTable(name = "quiz_options", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> options = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    @Column(name = "answer")
    @CollectionTable(name = "quiz_answer", joinColumns = @JoinColumn(name = "owner_id"))
    private List<Long> answer = new ArrayList<>();

    @JsonIgnore
    private String author;
}
