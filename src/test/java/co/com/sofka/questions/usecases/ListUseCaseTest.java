package co.com.sofka.questions.usecases;


import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;

@SpringBootTest
class ListUseCaseTest {
    @MockBean
    private QuestionRepository questionRepository;

    @SpyBean
    private ListUseCase listUseCase;

    @Test
    @DisplayName("Listar preguntas CRUD use case")
    void setListUseCase(){
        var question = new Question();
        question.setId("14");
        question.setQuestion("quien soy");
        question.setUserId("1");
        question.setType("OPEN");
        question.setCategory("DDDD");
        var question1 = new Question();
        question1.setId("11");
        question1.setQuestion("Donde vivo");
        question1.setUserId("2");
        question1.setType("OPEN");
        question1.setCategory("ninguna");

        Mockito.when(questionRepository.findAll()).thenReturn(Flux.just(question,question1));

        var list = listUseCase.listQuestion();
        Assertions.assertEquals(list.count().block(),2);
    }
}