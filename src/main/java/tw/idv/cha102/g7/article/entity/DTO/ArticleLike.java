package tw.idv.cha102.g7.article.entity.DTO;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// 複合主鍵
@Entity
@Data
@Table(name = "article_like")
public class ArticleLike {

    @EmbeddedId
    private ArticleLikeId likeId;

    public ArticleLike() {

    }
}
