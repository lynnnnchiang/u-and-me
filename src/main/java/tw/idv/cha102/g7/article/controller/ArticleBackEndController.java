package tw.idv.cha102.g7.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.service.ArticleBackEndService;
import tw.idv.cha102.g7.article.service.ArticleUserEditService;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.schedule.controller.exception.ScheduleNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/BackEnd")
public class ArticleBackEndController {
    @Autowired
    ArticleBackEndService articleBackEndService;
    @Autowired
    ArticleUserEditService articleUserEditService;


    // 後台瀏覽所有文章
    @GetMapping("/articles")
    public List<Article> getAll() {
        return articleBackEndService.getAll();
    }

    //後台瀏覽下架文章
    @GetMapping("/NonPublic")
    public List<Article> findNonPublicArticle(){return articleBackEndService.findNonPublicArticle();}

    // 管理員只能改文章上下架狀態
    @PutMapping("/Adminedit")
    public ResponseEntity<?> edit(@RequestBody Article updArticle) {
        try {
            System.out.println(updArticle);
            // 在console印出回傳的Article
            articleUserEditService.adminEdit(updArticle);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            Integer articleId = updArticle.getArticleId();
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ScheduleNotFoundException(articleId));
        }

    }

    @GetMapping("/{page}")
    public List<Article> getAllPaged(@PathVariable Integer page){
        return articleBackEndService.getAllPaged(page,5);
    }

}
