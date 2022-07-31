package co.com.sofka.questions.router;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.UpdateQuestionByAnswerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class UpdateQuestionByAnswerRouter {

    @Bean
    public RouterFunction updateQuestion(UpdateQuestionByAnswerUseCase updateQuestionByAnswer) {

        return route(PUT("/updateQuestion").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class)
                        .flatMap(questionDTO -> updateQuestionByAnswer.updateQuestionByAnswer(questionDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

}
