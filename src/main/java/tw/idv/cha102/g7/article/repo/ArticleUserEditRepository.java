package tw.idv.cha102.g7.article.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.article.entity.Article;

import java.util.List;

@Repository
public interface ArticleUserEditRepository extends JpaRepository<Article, Integer> {
//    public int update(Article article);
// 查詢使用者自己發表的文章
List<Article> findByMemId(Integer memId);
void deleteByArticleId(Integer articleId);

}
