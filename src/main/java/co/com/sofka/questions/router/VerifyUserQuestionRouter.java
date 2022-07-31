package co.com.sofka.questions.router;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.model.ResponseDTO;
import co.com.sofka.questions.usecases.VerifyUserQuestionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class VerifyUserQuestionRouter {
    @Bean
    public RouterFunction VerifyUserQuestion(VerifyUserQuestionUseCase updateQuestionCloneAnswerUseCase) {

        return route(PUT("/verificarpreguntaporid").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class)
                        .flatMap(questionDTO -> updateQuestionCloneAnswerUseCase.verificarUsuarioPregunta(questionDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)).onErrorResume(error ->{
                                    if(error instanceof IllegalAccessException){
                                        return ServerResponse.badRequest().bodyValue(new ResponseDTO("usuario no coincide con el id de la pregunta",false));
                                    }
                                    return ServerResponse.badRequest().build();
                                })));


    }
}
