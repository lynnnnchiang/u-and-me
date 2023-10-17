package tw.idv.cha102.g7.article.service;

import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticlePicture;

import java.util.List;

public interface ArticleBrowserService {




    String updateByArticleId(Integer articleId, Article article);

    // 搜尋文章title，可依照熱門(讚數)或最新最舊排序
    List<Article> findByArticleTitleOrderByLike(String keyword);

    // 論壇首頁呈現所有未下架文章
    public List<Article>findPublicArticle();

    // 論壇首頁呈現所有商城文章(廢code)
    public List<Article>findShopArticle();

    // 論壇首頁呈現所有行程文章(廢code)
    public List<Article>findScheduleArticle();

    // 論壇首頁呈現所有揪團文章(廢code)
    public List<Article>findGroupArticle();

    // 文章標題查詢
    public List<Article>findByTitleOrderByTime(String keyword);




    // 精確搜尋文章hashtag，可依照熱門(讚數)或最新最舊排序(多對多表格)


}
