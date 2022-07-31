package co.com.sofka.questions.router;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.ListRecommendQuestionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class ListRecommendedQuestionRouter {
    @Bean
    public RouterFunction<ServerResponse> recomendarQuestions(ListRecommendQuestionUseCase listRecommendQuestionUseCase){
        return route(GET("/recomendarquestion/{question}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .body(listRecommendQuestionUseCase.seek(request.pathVariable("question")), QuestionDTO.class)

        );
    }


}
