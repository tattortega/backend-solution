package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class AddAnswerUseCaseTest {

    @MockBean
    private AnswerRepository answerRepository;

    @SpyBean
    private AddAnswerUseCase addAnswerUseCase;


    @Test
    @DisplayName("consultar respuestas CRUD use case")
    void setAddAnswerUseCaseTest(){
        var answerDTO = new AnswerDTO("2", "11", "Medellin");
        var answer = new Answer();
        answer.setQuestionId("1");
        answer.setAnswer("Medellin");
        answer.setUserId("11");

        Mockito.when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(Mono.just(answer));

        var  datoAnswer = addAnswerUseCase.addAnswer(answerDTO).block();

        assert datoAnswer != null;
        Assertions.assertEquals(datoAnswer.getAnswer(), "Medellin");
    }
}