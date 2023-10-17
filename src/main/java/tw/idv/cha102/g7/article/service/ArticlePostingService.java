package tw.idv.cha102.g7.article.service;

import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.article.entity.ArticlePicture;
import tw.idv.cha102.g7.article.entity.ArticlePosting;

import java.util.List;

@Component
public interface ArticlePostingService {

    // 新增文章
    public int postArticle(ArticlePosting article, Integer memberId);

    // 上傳圖片與取得articleId
    public int postPicsAndSaveArticleId(List<ArticlePicture> pics);

}

