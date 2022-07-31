package co.com.sofka.questions.router;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.GetUseCase;
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
import reactor.core.publisher.Mono;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetQuestionRouter.class})
class GetQuestionRouterTest {
    @MockBean
    private GetUseCase getUseCase;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("obtener pregunta CRUD router")
    void obtenerQuestionTest() {

        var questionDTO = new QuestionDTO("12", "1", "que fue primero", "OPEN", "xxx");

        Mockito.when(getUseCase.apply(Mockito.any(String.class))).thenReturn(Mono.just(questionDTO));

        webTestClient.get().uri("/consultar/{id}", "12")
                .exchange()
                .expectStatus().isOk()
                .expectBody(QuestionDTO.class)
                .value(respuesta -> {
                    Assertions.assertThat(respuesta.getQuestion()).isEqualTo("que fue primero");
                });
    }
}