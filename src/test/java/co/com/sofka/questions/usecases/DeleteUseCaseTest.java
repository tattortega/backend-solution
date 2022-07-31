package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
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
import reactor.core.publisher.Mono;

@SpringBootTest
class DeleteUseCaseTest {
    @SpyBean
    private DeleteUseCase deleteUseCase;
    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("eliminar preguntas CRUD use case")
    public void deleteQuestion(){

        var question = new QuestionDTO("14", "1", "quien soy", "OPEN", "DDDD");
        var respuesta = new AnswerDTO();
        respuesta.setQuestionId("14");
        respuesta.setUserId("1");
        respuesta.setAnswer("una mujer");

        Mockito.when(questionRepository.deleteById("14")).thenReturn(Mono.empty());
        Mockito.when(answerRepository.deleteByQuestionId("14")).thenReturn(Mono.empty());

        var datosRespuesta = deleteUseCase.deleteByquestionId("14").block();

        Assertions.assertNull(datosRespuesta);


    }
}