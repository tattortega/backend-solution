package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@SpringBootTest
class UpdateQuestionByAnswerUseCaseTest {
    @SpyBean
    private UpdateQuestionByAnswerUseCase updateQuestionByAnswerUseCase;
    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private AnswerRepository answerRepository;


    @Test
    @DisplayName("test de pregunta con respuestas asociadas")
    void createQuestionAnswerTest(){
        var questionDTO = new QuestionDTO("14", "1", "quien soy", "OPEN", "DDDD");
        var answer = new Answer();
        answer.setId("3");
        answer.setUserId("5");
        answer.setQuestionId("14");
        answer.setAnswer("soy yo");

        var question = new Question();
        question.setId("15");
        question.setUserId("1");
        question.setQuestion("quien soy");
        question.setType("OPEN");
        question.setCategory("DDDD");

        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));
        Mockito.when(answerRepository.findAllByQuestionId("14")).thenReturn(Flux.just(answer));
        var response = updateQuestionByAnswerUseCase.updateQuestionByAnswer(questionDTO);

        Assertions.assertEquals(Objects.requireNonNull(response.block()).getQuestion(),"quien soy");

    }

    @Test
    @DisplayName("test pregunta sin respuestas asociadas")
    void updateQuestionAnswer(){
        var questionDTO = new QuestionDTO("14", "1", "pregunta sin editar?", "OPEN", "DDDD");
        var answer = new Answer();
        var question = new Question();
        question.setId("14");
        question.setUserId("1");
        question.setQuestion("pregunta editada?");
        question.setType("OPEN");
        question.setCategory("DDDD");


        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));
        Mockito.when(questionRepository.findById(Mockito.any(String.class))).thenReturn(Mono.just(question));
        Mockito.when(answerRepository.findAllByQuestionId("14")).thenReturn(Flux.just(answer));
        var response = updateQuestionByAnswerUseCase.updateQuestionByAnswer(questionDTO);

        Assertions.assertEquals(Objects.requireNonNull(response.block()).getQuestion(),"pregunta editada?");
    }
}