package tw.idv.cha102.g7.article.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticlePicture;

import java.util.List;

@Component
public interface ArticleUserEditService {

    // 使用者搜尋「我的文章」
    public List<Article> findByMemId(Integer memId);

    // 使用者編輯文章
    public String userEdit( Article updarticle);

    // 管理員修改上下架狀態
    public String adminEdit(Article article);

    // 刪除文章
    ResponseEntity<String> deleteArticleByArticleId(Integer articleId);

    // 刪除圖片
    ResponseEntity<String> deletePicById(Integer articleId);

    public void postPicsAndSaveArticleId(List<ArticlePicture> pics,Integer articleId);

    }
