package co.com.sofka.questions.router;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.usecases.AddAnswerUseCase;
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

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AddAnswerRouter.class})
class AddAnswerRouterTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private AddAnswerUseCase addAnswerUseCase;

    @Test
    @DisplayName("crear respuesta CRUD router")
    void addAnswerRouterTest(){

        var answerDTO = new AnswerDTO("1","12","la gallina fue");
        var answer = new Answer();
        answer.setQuestionId("1");
        answer.setAnswer("la gallina fue");
        answer.setUserId("12");

        Mockito.when(addAnswerUseCase.addAnswer(Mockito.any(AnswerDTO.class))).thenReturn(Mono.just(answerDTO));

        webTestClient.post().uri("/añadirrespuesta")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(answerDTO),AnswerDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AnswerDTO.class)
                .value(response->{
                    Assertions.assertThat(response.getQuestionId()).isEqualTo("1");
                });
    }
}