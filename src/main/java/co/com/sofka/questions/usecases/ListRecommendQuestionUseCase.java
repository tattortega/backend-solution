package co.com.sofka.questions.usecases;

import co.com.sofka.questions.mapper.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ListRecommendQuestionUseCase {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Autowired
    public ListRecommendQuestionUseCase(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public Flux<QuestionDTO> seek(String question){
        return   questionRepository.findByQuestionLike(question)
                .map(questionMapper.mapQuestionToDTO());

    }
}
