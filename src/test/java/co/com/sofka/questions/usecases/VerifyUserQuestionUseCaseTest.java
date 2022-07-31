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
class VerifyUserQuestionUseCaseTest {
    @MockBean
    private QuestionRepository questionRepository;
    @SpyBean
    private VerifyUserQuestionUseCase verifyUserQuestionUseCase;

    @Test
    @DisplayName("verificar preguntas por id de usuario")
    void verifyUserQuestion(){
        var questionDTO = new QuestionDTO("14", "1", "quien soy", "OPEN", "DDDD");
        var question = new Question();
        question.setId("15");
        question.setUserId("1");
        question.setQuestion("quien soy");
        question.setType("OPEN");
        question.setCategory("DDDD");

        var question2 = new Question();
        question.setId("15");
        question.setUserId("1");
        question.setQuestion("quien soy yo");
        question.setType("OPEN");
        question.setCategory("DDDD");

        Mockito.when(questionRepository.save(Mockito.any(Question.class)))
                .thenReturn(Mono.just(question));
        Mockito.when(questionRepository.findByIdAndUserId(Mockito.any(String.class),Mockito.any(String.class)))
                .thenReturn(Mono.just(question2));
        var response = verifyUserQuestionUseCase.verificarUsuarioPregunta(questionDTO);

        Assertions.assertEquals(Objects.requireNonNull(response.block()).getQuestion(),"quien soy yo");
    }
}