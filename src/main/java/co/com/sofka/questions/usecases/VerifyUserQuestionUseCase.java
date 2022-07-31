package co.com.sofka.questions.usecases;

import co.com.sofka.questions.mapper.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class VerifyUserQuestionUseCase {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    @Autowired
    public VerifyUserQuestionUseCase(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public Mono<QuestionDTO> verificarUsuarioPregunta(QuestionDTO questionDTO) {
        return  questionRepository.findByIdAndUserId(questionDTO.getId(), questionDTO.getUserId())
                .switchIfEmpty(Mono.error(new IllegalAccessException()))
                .flatMap(responce->{
                    return editarQuestion(questionDTO);
                });
    }

    private Mono<QuestionDTO> editarQuestion(QuestionDTO question){
        var res= questionMapper.mapperToQuestion(question.getId()).apply(question);
        return questionRepository.save(res).map(questionMapper.mapQuestionToDTO());
    }
}
