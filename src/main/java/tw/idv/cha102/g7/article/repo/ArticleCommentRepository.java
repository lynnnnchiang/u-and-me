package tw.idv.cha102.g7.article.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.article.entity.ArticleComment;

import java.util.List;
@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Integer> {
    //依照文章id查詢
    List<ArticleComment> findAllByArticleId(Integer articleId);



}
