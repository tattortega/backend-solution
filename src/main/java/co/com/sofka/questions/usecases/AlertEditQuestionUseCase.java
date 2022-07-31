package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.mapper.ResponseMapper;
import co.com.sofka.questions.model.ResponseDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class AlertEditQuestionUseCase {
    private final AnswerRepository answerRepository;
    private final ResponseMapper responseMapper;


    @Autowired
    public AlertEditQuestionUseCase( AnswerRepository answerRepository, ResponseMapper responseMapper) {
        this.answerRepository = answerRepository;
        this.responseMapper = responseMapper;
    }

    public Mono<ResponseDTO> throwAlert(String id){
        return answerRepository.findAllByQuestionId(id).next().switchIfEmpty(Mono.just(new Answer())).map(responseMapper.answerToResponse());
    }
}
