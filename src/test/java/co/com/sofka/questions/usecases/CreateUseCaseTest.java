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

import static org.mockito.Mockito.when;

@SpringBootTest
class CreateUseCaseTest {
    @SpyBean
    private CreateUseCase createUseCase;
    @MockBean
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("crear preguntas CRUD use case")
    void createQuestion(){

        var QuestionDTO = new QuestionDTO("14", "1", "quien soy", "OPEN", "DDDD");
        var question = new Question();
        question.setId("14");
        question.setUserId("1");
        question.setType("OPEN");
        question.setCategory("DDDD");

//        when(questionRepository.save(question)).thenReturn(Mono.just(question)); //va a retornar question
        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));

        var respuesta = createUseCase.insertar(QuestionDTO);

        Assertions.assertEquals(Objects.requireNonNull(respuesta.block()).getId(),"14");
    }
}