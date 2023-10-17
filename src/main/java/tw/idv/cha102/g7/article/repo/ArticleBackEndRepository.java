package tw.idv.cha102.g7.article.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.article.entity.Article;

import java.util.List;
@Repository
public interface ArticleBackEndRepository extends JpaRepository <Article, Integer>{
    //查詢下架文章
    @Query(value = "SELECT * FROM article WHERE article_state =0", nativeQuery = true)
    List<Article> findNonPublicArticle();


}
