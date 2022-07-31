package co.com.sofka.questions.mapper;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnswerMapper {
    public Function<AnswerDTO, Answer> fromAnswerDtoToAnswer(String id) {
        return updateAnswer -> {
            var answer = new Answer();
            answer.setId(id);
            answer.setUserId(updateAnswer.getUserId());
            answer.setQuestionId(updateAnswer.getQuestionId());
            answer.setAnswer(updateAnswer.getAnswer());
            return answer;
        };

    }

    public Function<Answer, AnswerDTO> fromAnswerToAnswerDTO() {
        return entity ->
                new AnswerDTO(
                        entity.getQuestionId(),
                        entity.getUserId(),
                        entity.getAnswer()
                );
    }

    public Function<Answer, Answer> AnswerClone(String idQuestion) {
        return cloneAnswer -> {
            var answer = new Answer();
            answer.setAnswer(cloneAnswer.getAnswer());
            answer.setId(null);
            answer.setQuestionId(idQuestion);
            answer.setUserId(cloneAnswer.getUserId());


            return answer;
        };
    }

    public Function<Answer, Question> fromAnswerToQuestionsDTO(QuestionDTO updateQuestion) {
        return entity -> {
            var question = new Question();
            if(entity.getId() != null){
                question.setId(null);
                question.setUserId(updateQuestion.getUserId());
                question.setQuestion(updateQuestion.getQuestion());
                question.setType(updateQuestion.getType());
                question.setCategory(updateQuestion.getCategory());
                return question;
            }
            question.setId(updateQuestion.getId());
            question.setUserId(updateQuestion.getUserId());
            question.setQuestion(updateQuestion.getQuestion());
            question.setType(updateQuestion.getType());
            question.setCategory(updateQuestion.getCategory());
            return question;
        };

    }
}
