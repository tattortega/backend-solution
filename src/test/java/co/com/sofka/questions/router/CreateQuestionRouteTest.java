package co.com.sofka.questions.router;


import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.CreateUseCase;
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
@ContextConfiguration(classes = {CreateQuestionRoute.class})
class CreateQuestionRouteTest {
    @MockBean
    private CreateUseCase createUseCase;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("crear pregunta CRUD router")
    void crearQuestionTest(){
        var questionDTO = new QuestionDTO("1", "14", "Cual es el sentido de la vida","OPEN","DDDD");

        Mockito.when(createUseCase.insertar(questionDTO)).thenReturn(Mono.just(questionDTO));

        webTestClient.post().uri("/crearquestion")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(questionDTO),QuestionDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QuestionDTO.class)
                .value(userResponse ->{
                    Assertions.assertThat(userResponse).isInstanceOf(QuestionDTO.class);
                });
    }
}