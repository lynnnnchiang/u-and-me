package tw.idv.cha102.g7.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticlePicture;
import tw.idv.cha102.g7.article.repo.ArticleBrowserRepository;
import tw.idv.cha102.g7.article.repo.ArticlePictureRepository;
import tw.idv.cha102.g7.article.service.ArticleBrowserService;

import java.util.List;

@Component
public class ArticleBrowserServiceImpl implements ArticleBrowserService {

    @Autowired
    private ArticleBrowserRepository articleBrowserRepository;
    @Autowired
    private ArticlePictureRepository articlePictureRepository;


    @Override
    public String updateByArticleId(Integer articleId, Article article) {
        // 舊文章
        // 新文章為輸入要更改的文章ID
        Article art = articleBrowserRepository.findById(articleId).orElse(null);
        //更改後的新文章
        if (art != null) {  // 為欲修改的行程資料
            art.setArticleTitle(article.getArticleTitle());
            art.setAcTypeId(article.getAcTypeId());
            art.setArticleContent(article.getArticleContent());
            articleBrowserRepository.save(art);
            return "更新成功！";
        } else {
            return "更新失敗，查詢的文章不存在";
        }

    }

    //    public List<Article> findByArticleTitle(String keyword) {
    //   return articleRepository.findByTitleOrderByTime(keyword);
    //    }

    // 查詢所有公開文章的title
    @Override
    public List<Article> findByArticleTitleOrderByLike(String keyword) {
        return articleBrowserRepository.findByTitleOrderByLike(keyword);
    }

    @Override
    public List<Article> findByTitleOrderByTime(String keyword) {
        return articleBrowserRepository.findByTitleOrderByTime(keyword);
    }


    // 首頁查詢未下架文章
    @Override
    public List<Article> findPublicArticle() {
        return articleBrowserRepository.findPublicArticle();
    }

    // 首頁查詢商城文章(廢code)
    @Override
    public List<Article> findShopArticle() {
        return articleBrowserRepository.findShopArticle();
    }

    // 首頁查詢行程文章(廢code)
    @Override
    public List<Article> findScheduleArticle() {
        return articleBrowserRepository.findScheduleArticle();
    }

    // 首頁查詢揪團文章(廢code)
    @Override
    public List<Article> findGroupArticle() {
        return articleBrowserRepository.findGroupArticle();
    }




}
