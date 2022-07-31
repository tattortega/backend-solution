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
class GetUseCaseTest {
    @MockBean
    private QuestionRepository questionRepository;

    @SpyBean
    private GetUseCase getUseCase;


    @Test
    @DisplayName("consultar preguntas CRUD use case")
    void getUseCase(){
        var questionDTO = new QuestionDTO("14", "1", "quien soy", "OPEN", "DDDD");
        var question = new Question();
        question.setId("14");
        question.setQuestion("quien soy");
        question.setUserId("1");
        question.setType("OPEN");
        question.setCategory("DDDD");


        Mockito.when(questionRepository.findById(Mockito.any(String.class))).thenReturn(Mono.just(question));

        var datosRespuesta = getUseCase.apply("14");
        Assertions.assertEquals(Objects.requireNonNull(datosRespuesta.block()).getQuestion(),"quien soy");

    }
}