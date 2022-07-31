package co.com.sofka.questions.router;

import co.com.sofka.questions.model.ResponseDTO;
import co.com.sofka.questions.usecases.AlertEditQuestionUseCase;
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
@ContextConfiguration(classes = {AlertEditQuestionRouter.class})
class AlertEditQuestionRouterTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private AlertEditQuestionUseCase alertEditQuestionUseCase;

    @Test
    @DisplayName("Test de alerta de edicion de pregunta")
    void AlertEditQuestionRouterTest() {
        var responseDTO = new ResponseDTO();
        responseDTO.setEstado(false);
        responseDTO.setMensaje("Puedes modificar la pregunta");
        Mockito.when(alertEditQuestionUseCase.throwAlert("1")).thenReturn(Mono.just(responseDTO));

        webTestClient.get().uri("/alertedit/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseDTO.class)
                .value(response -> {
                    Assertions.assertThat(response).isInstanceOf(ResponseDTO.class);
                    Assertions.assertThat(response.getMensaje()).isEqualTo("Puedes modificar la pregunta");
                });
    }
}