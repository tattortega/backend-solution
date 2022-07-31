package co.com.sofka.questions.router;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.model.ResponseDTO;
import co.com.sofka.questions.usecases.VerifyUserQuestionUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {VerifyUserQuestionRouter.class})
class VerifyUserQuestionRouterTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private VerifyUserQuestionUseCase verifyUserQuestionUseCase;

    @Test
    @DisplayName("Test de verificar pregunta de usuario")
    void verifyUserQuestionRouterTest() {
        var questionDTO = new QuestionDTO("15", "1", "pregunta sin editar", "OPEN", "DDDD");
        var questionDTO2 = new QuestionDTO("14", "1", "pregunta editada", "OPEN", "DDDD");
        Mockito.when(verifyUserQuestionUseCase.verificarUsuarioPregunta(questionDTO)).thenReturn(Mono.just(questionDTO2));

        webTestClient.put().uri("/verificarpreguntaporid")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(questionDTO), QuestionDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QuestionDTO.class)
                .value(response ->
                        Assertions.assertThat(response.getUserId()).isEqualTo("1")
                );

    }

    @Test
    @DisplayName("Test de verificar erro en identificacion de usuario")
    void verifyUserQuestionErrorRouterTest() {
        var questionDTO = new QuestionDTO("15", "1", "pregunta sin editar", "OPEN", "DDDD");
        var questionDTO2 = new QuestionDTO("14", "1", "pregunta editada", "OPEN", "DDDD");
        Mockito.when(verifyUserQuestionUseCase.verificarUsuarioPregunta(questionDTO)).thenReturn(Mono.error(new IllegalAccessException()));

        webTestClient.put().uri("/verificarpreguntaporid")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(questionDTO), QuestionDTO.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ResponseDTO.class)
                .value(response ->
                        Assertions.assertThat(response.getMensaje()).isEqualTo("usuario no coincide con el id de la pregunta")
                );

    }
}