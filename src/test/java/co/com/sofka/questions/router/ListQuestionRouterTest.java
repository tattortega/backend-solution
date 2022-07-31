package co.com.sofka.questions.router;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.ListUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ListQuestionRouter.class})
class ListQuestionRouterTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ListUseCase listUseCase;

    @Test
    @DisplayName("listar preguntas CRUD router")
    void listQuestionTest() {

        var questionDTO1 = new QuestionDTO("14", "1", "quien soy", "OPEN", "DDDD");
        var questionDTO2 = new QuestionDTO("15", "1", "quien eres", "OPEN", "DDDD");

        Mockito.when(listUseCase.listQuestion()).thenReturn(Flux.just(questionDTO1, questionDTO2));

        webTestClient.get().uri("/listarQuestion")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(QuestionDTO.class)
                .value(list ->
                        Assertions.assertThat(list.size()).isEqualTo(2)
                );
    }
}