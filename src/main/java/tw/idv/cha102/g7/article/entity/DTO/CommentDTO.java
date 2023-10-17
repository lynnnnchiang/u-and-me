package tw.idv.cha102.g7.article.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.cha102.g7.article.entity.ArticleComment;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleDetail;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private ArticleComment articleComment;

    private String memName;
    

    public CommentDTO(Object[] objects) {
        this.memName = (String) objects[0];
        this.articleComment = (ArticleComment) objects[1];
    }
}
