package tw.idv.cha102.g7.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollection;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollectionId;
import tw.idv.cha102.g7.article.repo.ArticleCollectionRepository;
import tw.idv.cha102.g7.article.service.ArticleCollectionService;
import tw.idv.cha102.g7.member.entity.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.lang.Integer.parseInt;


@RestController
//@RequestMapping("/articleCollection")
public class ArticleCollectionController {
    @Autowired
    ArticleCollectionService articleCollectionService;
    @Autowired
    ArticleCollectionRepository articleCollectionRepository;

    /**
     * 新刪文章收藏
     *
     * @param articleCollection
     * @return String "success" or "failed"
     */
    @RequestMapping("/AddOrRemoveCollection")
    public short AddOrRemoveCollection(@RequestBody ArticleCollection articleCollection) {
        System.out.println("hey");
        if (articleCollectionService.AddOrRemoveCollection(articleCollection) == 0) {
            return 0;
        } else {
            return 1;

        }
    }

    //    {
//        "collectionId":{
//        "memId": 2,
//        "articleId": 1
//    }
//    }

    // 協助前端讀取收藏icon狀態
    @RequestMapping("/CollectionExsitOrNot/{articleId}")
    public short CollectionExsitOrNot(@PathVariable Integer articleId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        if (obj == null) {
            return 0;
        }
        Integer memberId = parseInt(session.getAttribute("memberId").toString());
        ArticleCollectionId articleCollectionId = new ArticleCollectionId();
        ArticleCollection articleCollection = new ArticleCollection();

        articleCollectionId.setMemId(memberId);
        articleCollectionId.setArticleId(articleId);
        articleCollection.setCollectionId(articleCollectionId);
        if (articleCollectionService.CollectionExsitOrNot(articleCollection) == 1) {
            return 1;
        } else {
            return 0;
        }
    }


    /**
     * 顯示該會員所有文章收藏
     *
     * @param request
     * @return
     */
    @RequestMapping("/getArticleFromCollectionByMemId")
    public List<Article> getAttrsFromCollectionByMemId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memberId = parseInt(session.getAttribute("memberId").toString());
        return articleCollectionService.findArticleByMemId(memberId);
    }


}
