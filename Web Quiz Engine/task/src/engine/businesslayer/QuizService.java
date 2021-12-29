package engine.businesslayer;

import engine.exceptions.ForbiddenRequestException;
import engine.exceptions.QuizNotFoundException;
import engine.persistence.HistoryRepository;
import engine.persistence.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private QuizRepository quizRepository;
    private HistoryRepository historyRepository;
    private static int PAGE_SIZE = 10;

    @Autowired
    public QuizService(QuizRepository quizRepository, HistoryRepository historyRepository) {
        this.quizRepository = quizRepository;
        this.historyRepository = historyRepository;
    }

    public Page<Quiz> getPagedQuizzes(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by("id").ascending());
        Page<Quiz> pagedResult = quizRepository.findAll(pageable);
        return pagedResult;
    }

    public void createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public Quiz getQuiz(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("no such quiz");
        }
        return quizRepository.findById(id).get();
    }

    public Response solveQuiz(String userName, Long id, Answer answer) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("no such quiz");
        }
        List<Long> correctAnswer = quiz.get().getAnswer();
        if (correctAnswer.containsAll(answer.getAnswer()) && answer.getAnswer().containsAll(correctAnswer)) {
            History history = historyRepository.findByUsernameAndAndId(userName, id);
            if (history != null) {
                history.setCompletedAt(LocalDateTime.now());
                historyRepository.save(history);
            } else {
                historyRepository.save(new History(userName, id, LocalDateTime.now()));
            }
            return Response.succeed();
        }
        return Response.failed();
    }

    public void deleteQuiz(String username, Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("no such quiz");
        }
        if (quiz.get().getAuthor() != null && !quiz.get().getAuthor().equals(username)) {
            throw new ForbiddenRequestException("not permitted to delete");
        }
        quizRepository.deleteById(id);
    }

    public void updateQuiz(String username, Long id, Quiz quiz) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isEmpty()) {
            throw new QuizNotFoundException("no such quiz");
        }
        if (optionalQuiz.get().getAuthor() != null && !optionalQuiz.get().getAuthor().equals(username)) {
            throw new ForbiddenRequestException("not permitted to update");
        }
        optionalQuiz.ifPresent(quiz1 -> {
            quiz1.setTitle(quiz.getTitle());
            quiz1.setText(quiz.getText());
            quiz1.setOptions(quiz.getOptions());
            quiz1.setAnswer(quiz.getAnswer());
            quizRepository.save(quiz1);
        });
    }

    public Page<History> getUserHistory(String username, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by("completedAt").descending());
        return historyRepository.findAllHistoryWithPagination(pageable, username);
    }
}
