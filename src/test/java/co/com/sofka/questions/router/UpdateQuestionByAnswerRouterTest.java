package co.com.sofka.questions.router;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.UpdateQuestionByAnswerUseCase;
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
@ContextConfiguration(classes = {UpdateQuestionByAnswerRouter.class})
class UpdateQuestionByAnswerRouterTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UpdateQuestionByAnswerUseCase updateQuestionByAnswerUseCase;

    @Test
    @DisplayName("Test modificar respuesta")
    void UpdateQuestionByAnswerTest() {
        var questionDTO = new QuestionDTO("14", "1", "quien soy", "OPEN", "DDDD");
        Mockito.when(updateQuestionByAnswerUseCase.updateQuestionByAnswer(questionDTO)).thenReturn(Mono.just(questionDTO));

        webTestClient.put().uri("/updateQuestion")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(questionDTO), QuestionDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QuestionDTO.class)
                .value(response ->
                        Assertions.assertThat(response.getId()).isEqualTo("14")
                );
    }
}