package tw.idv.cha102.g7.article.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.article.entity.Article;

import java.util.List;

@Repository
public interface ArticleBrowserRepository extends JpaRepository<Article, Integer> {
// updLike監聽前端頁面onclick，like++
// 新增留言後，頁面comment_num++

    // 輸入文章標題，模糊搜尋未受屏蔽的文章，可依照熱門度(讚數)排序
    @Query(value = "SELECT * FROM article WHERE article_title LIKE %?1% ORDER BY article_Like DESC", nativeQuery = true)
    List<Article> findByTitleOrderByLike(String keyword);

    // 輸入文章標題，模糊搜尋未受屏蔽的文章，可依照最新po文排序
    @Query(value = "SELECT * FROM article WHERE articleTitle LIKE %?1% ORDER BY article_Time", nativeQuery = true)
    List<Article> findByTitleOrderByTime(String keyword);

    //查詢未下架文章
    @Query(value = "SELECT * FROM article WHERE article_state = 1 ORDER BY article_time DESC", nativeQuery = true)

    List <Article> findPublicArticle();

    //查詢商城文章(廢code)
    @Query(value = "SELECT * FROM article WHERE article_state =1 and ac_type_id=1 ORDER BY article_time DESC", nativeQuery = true)
    List <Article> findShopArticle();

    //查詢行程文章(廢code)
    @Query(value = "SELECT * FROM article WHERE article_state =1 and ac_type_id=2 ORDER BY article_time DESC", nativeQuery = true)
    List <Article> findScheduleArticle();

    //查詢揪團文章(廢code)
    @Query(value = "SELECT * FROM article WHERE article_state =1 and ac_type_id=3 ORDER BY article_time DESC", nativeQuery = true)
    List <Article> findGroupArticle();

    //查詢特定article_id 的內文資訊(要改到single)，也可用在後台管理員搜尋上
    Article getByArticleId(Integer articleId);



}
