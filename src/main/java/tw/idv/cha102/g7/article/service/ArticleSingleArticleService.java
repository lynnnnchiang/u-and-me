package tw.idv.cha102.g7.article.service;

import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticleComment;
import tw.idv.cha102.g7.article.entity.ArticlePicture;

import java.util.List;

public interface ArticleSingleArticleService {
    // 新增留言後，頁面留言數++



    // find by articleId
    public Article getByArticleId(Integer articleId);

    public String getMemNameByMemId();

    public String getCommentMemNameByMemId();


        // find pic by articleId
    public List<ArticlePicture> findPicByArticleId(Integer articleId);

    //依照文章id查詢
    List<ArticleComment> findAllByArticleId(Integer articleId);

    // 在特定文章id中新增留言(從URL中抓取articleId)
    public short postComment(ArticleComment comment,Integer articleId,Integer memberId ) ;


}
