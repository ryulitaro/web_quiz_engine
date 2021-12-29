package engine.businesslayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {
    private boolean success;
    private String feedback;

    public static Response succeed() {
        return new Response(true, "Congratulations, you're right!");
    }

    public static Response failed() {
        return new Response(false, "Wrong answer! Please, try again.");
    }
}
