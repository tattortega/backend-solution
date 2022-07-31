package co.com.sofka.questions.router;

import co.com.sofka.questions.usecases.DeleteUseCase;
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
@ContextConfiguration(classes = {DeleteQuestionRouter.class})
class DeleteQuestionRouterTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private DeleteUseCase deleteUseCase;

    @Test
    @DisplayName("eliminar pregunta CRUD router")
    void deleteQuestionRouterTest(){
        Mockito.when(deleteUseCase.deleteByquestionId("1")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/eliminar/{id}","1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class)
                .value(response->{
                    Assertions.assertThat(response).isEqualTo(null);

                });

    }
}