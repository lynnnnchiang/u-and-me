package tw.idv.cha102.g7.article.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticleComment;
import tw.idv.cha102.g7.article.entity.ArticlePicture;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollection;
import tw.idv.cha102.g7.article.entity.DTO.ArticleLike;
import tw.idv.cha102.g7.article.entity.DTO.ArticleLikeId;
import tw.idv.cha102.g7.article.service.ArticleLikeService;
import tw.idv.cha102.g7.article.service.ArticleSingleArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/article_comment")
@CrossOrigin
public class ArticleCommentController {
    @Autowired
    ArticleSingleArticleService articleSingleArticleService;
    @Autowired
    ArticleLikeService articleLikeService;

    //文章詳情呈現該articleId所有 留言
    @GetMapping("/{articleId}")
    public List<ArticleComment> findAllByArticleId(@PathVariable Integer articleId) {
        return articleSingleArticleService.findAllByArticleId(articleId);
    }

    //文章詳情頁面新增留言
    @RequestMapping("/post/{articleId}")
    public Integer insert(@RequestBody ArticleComment comment,
                          @PathVariable Integer articleId,
                          HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memberId = parseInt(session.getAttribute("memberId").toString());
        if (memberId == null) {
            return 0;
        } else {
            articleSingleArticleService.postComment(comment, articleId, memberId);
            return 1;
        }

    }


    //文章詳情呈現特定articleId 文章
    @GetMapping("/articleId/{articleId}")
    public Article getByArticleId(@PathVariable Integer articleId) {
        return articleSingleArticleService.getByArticleId(articleId);
    }
    //文章詳情呈現特定articleId 的發文者名
    @GetMapping("/articleId/memName")
    public String getMemNameByMemId() {
        System.out.println(articleSingleArticleService.getMemNameByMemId());
        return articleSingleArticleService.getMemNameByMemId();
    }
    //文章詳情呈現特定articleId 的留言者暱稱
    @GetMapping("/articleId/CommentmemName")
    public String getCommentMemNameByMemId() {
        System.out.println(articleSingleArticleService.getCommentMemNameByMemId());
        return articleSingleArticleService.getCommentMemNameByMemId();
    }

    //文章詳情呈現特定articleId 圖片
    @GetMapping("/articleId/article_pic/{articleId}")
    public List<ArticlePicture> postArticle(@PathVariable Integer articleId) {
        return articleSingleArticleService.findPicByArticleId(articleId);
    }

    /**
     * 文章點讚功能
     *
     * @param articleLike
     * @return String "success" or "failed"
     */
    @RequestMapping("/AddOrRemoveLike")
    public short AddOrRemoveLike(@RequestBody ArticleLike articleLike) {
        System.out.println("hey");
        if (articleLikeService.AddOrRemoveLike(articleLike) == 0) {
            return 0;
        } else {
            return 1;

        }
    }

    //    {
//        "likeId":{
//        "memId": 2,
//        "articleId": 1
//    }
//    }

    // 協助前端讀取按讚狀態
    @RequestMapping("/LikeExsitOrNot/{articleId}")
    public short LikeExsitOrNot(@PathVariable Integer articleId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object object = session.getAttribute("memberId");
        if (object == null)
            return 0;
        Integer memId = parseInt(object.toString());
        ArticleLikeId articleLikeId = new ArticleLikeId();
        ArticleLike articleLike = new ArticleLike();
        articleLikeId.setArticleId(articleId);
        articleLikeId.setMemId(memId);
        articleLike.setLikeId(articleLikeId);
        if (articleLikeService.LikeExsitOrNot(articleLike) == 1) {
            return 1;
        } else {
            return 0;
        }
    }

}