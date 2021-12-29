package engine.presentation;

import engine.businesslayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("")
    public Quiz createNewQuiz(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody Quiz quiz) {
        String username = userDetails.getUsername();
        quiz.setAuthor(username);
        quizService.createQuiz(quiz);
        return quiz;
    }

    @GetMapping("")
    public Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        return quizService.getPagedQuizzes(page);
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("/{id}/solve")
    public Response solveQuiz(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @Valid @RequestBody Answer answer) {
        String username = userDetails.getUsername();
        return quizService.solveQuiz(username, id, answer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteQuiz(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        String username = userDetails.getUsername();
        quizService.deleteQuiz(username, id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateQuiz(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @Valid @RequestBody Quiz quiz) {
        String username = userDetails.getUsername();
        quizService.updateQuiz(username, id, quiz);
    }

    @GetMapping("/completed")
    public Page<History> getUserHistory(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(defaultValue = "0") Integer page) {
        String username = userDetails.getUsername();
        return quizService.getUserHistory(username, page);
    }
}
