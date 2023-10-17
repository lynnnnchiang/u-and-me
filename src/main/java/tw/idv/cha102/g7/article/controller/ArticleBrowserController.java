package tw.idv.cha102.g7.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticlePicture;
import tw.idv.cha102.g7.article.service.ArticleBackEndService;
import tw.idv.cha102.g7.article.service.ArticleBrowserService;

import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleBrowserController {
    @Autowired
    private ArticleBrowserService articleBrowserService;
    @Autowired
    private ArticleBackEndService articleBackEndService;

    //首頁呈現所有未下架文章
    @GetMapping("/publicArticles")
    public List<Article> findPublicArticle(){
        return articleBrowserService.findPublicArticle();
    }

    //首頁呈現商城文章(廢code)
    @GetMapping("/shopArticles")
    public List<Article> findShopArticle(){
        return articleBrowserService.findShopArticle();
    }

    //首頁呈現行程文章(廢code)
    @GetMapping("/scheduleArticles")
    public List<Article> findScheduleArticle(){
        return articleBrowserService.findScheduleArticle();
    }

    //首頁呈現揪團文章(廢code)
    @GetMapping("/groupArticles")
    public List<Article> findGroupArticle(){
        return articleBrowserService.findGroupArticle();
    }



    // 透過文章標題搜尋相關文章
    @GetMapping("/OrderByLike/{keyword}")
    public List<Article> findArticleTitleOrderByLike(@PathVariable String keyword) {
        return articleBrowserService.findByArticleTitleOrderByLike(keyword);
    }

    // 透過文章標題搜尋相關文章
//    @GetMapping("/OrderByTime/{keyword}")
//    public List<Article> findArticleTitleOrderByTime(@PathVariable String keyword) {
//        return articleBrowserService.findByTitleOrderByTime(keyword);
//    }


    // 刪除文章



}
