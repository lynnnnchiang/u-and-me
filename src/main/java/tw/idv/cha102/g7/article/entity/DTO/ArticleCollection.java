package tw.idv.cha102.g7.article.entity.DTO;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// 複合主鍵
@Entity
@Data
@Table(name = "article_collection")
public class ArticleCollection {

    @EmbeddedId
    private ArticleCollectionId collectionId;

    public ArticleCollection() {

    }

    public ArticleCollection(ArticleCollectionId collectionId) {
        this.collectionId = collectionId;
    }
}
