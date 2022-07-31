package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import java.util.Objects;


@SpringBootTest
class UpdateUseCaseTest {
    @MockBean
    private QuestionRepository questionRepository;
    @SpyBean
    private UpdateUseCase updateUseCase;

    @Test
    @DisplayName("Editar pregunta CRUD use case")
    void editarQuestionTest(){

        var question = new QuestionDTO("14", "1", "quien soy", "OPEN", "DDDD");
        var questionResponse = new Question();
        questionResponse.setId("14");
        questionResponse.setUserId("1");
        questionResponse.setQuestion("esta es una nueva respuesta");
        questionResponse.setType("OPEN");
        questionResponse.setCategory("DDDD");

        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(questionResponse));

        var spy = updateUseCase.modificarQuestion(question);

        Assertions.assertEquals(Objects.requireNonNull(spy.block()).getQuestion(),"esta es una nueva respuesta");
    }
}