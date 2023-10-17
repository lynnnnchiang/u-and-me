package tw.idv.cha102.g7.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticlePicture;
import tw.idv.cha102.g7.article.service.ArticleSingleArticleService;
import tw.idv.cha102.g7.article.service.ArticleUserEditService;
import tw.idv.cha102.g7.schedule.controller.exception.ScheduleNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/UserEdit")
@CrossOrigin
public class ArticleUserEditController {
    @Autowired
    ArticleUserEditService articleUserEditService;
    @Autowired
    ArticleSingleArticleService articleSingleArticleService;

    // 使用者的「我的文章」
    @GetMapping("/findByMemId")
    public List<Article> findByMemId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memberId = parseInt(session.getAttribute("memberId").toString());
        return articleUserEditService.findByMemId(memberId);
    }

    // 在修改頁面載入文章
    @GetMapping("/{articleid}")
    public Article getByArticleId(@PathVariable Integer articleid) {
        return articleSingleArticleService.getByArticleId(articleid);
    }

    // 在修改頁面回傳修改後的文章
    @PutMapping("/upd/{articleId}")
    public ResponseEntity<?> edit(@PathVariable Integer articleId,
                                  @RequestBody Article updArticle,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memberId = parseInt(session.getAttribute("memberId").toString());

        System.out.println(updArticle);
        try {
            articleUserEditService.userEdit(updArticle);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ScheduleNotFoundException(articleId));

        }

    }

    // 在「我的文章」頁面刪除文章
    @DeleteMapping("/deleteArticle/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable Integer articleId) {
        try {
            articleUserEditService.deleteArticleByArticleId(articleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 在「修改頁面」刪除圖片
    @DeleteMapping("/upd/deletePic/{articlePicId}")
    public ResponseEntity<String> deletePic(@PathVariable Integer articlePicId) {
        try {
            articleUserEditService.deletePicById(articlePicId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 在「修改頁面」新增文章的圖片
    @PostMapping("/upd/post_pics/{articleId}")
    // void-->ResponseEntity<E>
    public ResponseEntity<String> insert(@RequestParam("files") MultipartFile[] files,
                       @PathVariable Integer articleId) throws IOException {
        try {
            List<ArticlePicture> pics = new ArrayList<>();

            for (MultipartFile file : files) {
                ArticlePicture pic = new ArticlePicture();
                pic.setArticlePic(file.getBytes()); // 將 MultipartFile 轉換成 byte[]
                pics.add(pic);  // 存到pics陣列中
            }
            articleUserEditService.postPicsAndSaveArticleId(pics, articleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
