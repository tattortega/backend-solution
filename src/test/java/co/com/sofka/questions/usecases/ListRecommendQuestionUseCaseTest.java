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
import reactor.core.publisher.Flux;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ListRecommendQuestionUseCaseTest {

    @MockBean
    private QuestionRepository questionRepository;

    @SpyBean
    private ListRecommendQuestionUseCase listRecommendQuestionUseCase;

    @Test
    @DisplayName("Listar recomendacion de preguntas")
    void listRecommendedQuestions(){

        var questioDTO = new QuestionDTO("15", "1", "quien soy", "OPEN", "DDDD");
        var question = new Question();
        question.setId("15");
        question.setUserId("1");
        question.setQuestion("quien soy yo");
        question.setType("OPEN");
        question.setCategory("DDDD");

        Mockito.when(questionRepository.findByQuestionLike(Mockito.any(String.class))).thenReturn(Flux.just(question));
        var resultado = listRecommendQuestionUseCase.seek("quien");

        Assertions.assertEquals(Objects.requireNonNull(resultado.blockFirst()).getId(),"15");
        Assertions.assertEquals(Objects.requireNonNull(resultado.blockFirst()).getQuestion(),"quien soy yo");
        Assertions.assertEquals(Objects.requireNonNull(resultado.blockFirst()).getType(),"OPEN");
    }
}