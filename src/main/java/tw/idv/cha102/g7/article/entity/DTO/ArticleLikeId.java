package tw.idv.cha102.g7.article.entity.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

// 複合主鍵
@Embeddable
@Data
public class ArticleLikeId implements Serializable {
    // 可以序列化和反序列化
    @Column(name = "mem_id")
    public Integer memId;

    @Column(name = "article_id")
    public Integer articleId;


    //  equals 方法被重寫
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//            // 比較當前物件（this）與傳入的物件（o）
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        ArticleCollectionId id = (ArticleCollectionId) o;
//        return Objects.equals(articleId, id.articleId)
//                && Objects.equals(memId, id.memId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(articleId, memId);
//    }
}
