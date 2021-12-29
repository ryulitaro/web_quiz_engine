package engine.businesslayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @NotNull
    private List<Long> answer = new ArrayList<>();

    public List<Long> getAnswer() {
        return answer;
    }
}
