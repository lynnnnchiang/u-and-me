package tw.idv.cha102.g7.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticleComment;
import tw.idv.cha102.g7.article.entity.ArticlePicture;
import tw.idv.cha102.g7.article.repo.ArticleBrowserRepository;
import tw.idv.cha102.g7.article.repo.ArticleCommentRepository;
import tw.idv.cha102.g7.article.repo.ArticlePictureRepository;
import tw.idv.cha102.g7.article.service.ArticleSingleArticleService;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.MemberRepository;

import java.util.List;

@Component
public class SingleArticleServiceImpl implements ArticleSingleArticleService {
    @Autowired
    ArticleBrowserRepository articleBrowserRepository;
    @Autowired
    ArticleCommentRepository articleCommentRepository;
    @Autowired
    ArticlePictureRepository articlePictureRepository;
    @Autowired
    MemberRepository memberRepository;

    private Integer memId;
    private Integer commentMemId;

    // 依照文章id查詢該文所有留言
    @Override
    public List<ArticleComment> findAllByArticleId(Integer articleId) {
        return articleCommentRepository.findAllByArticleId(articleId);
    }

    // 在該文章底下新增留言
    @Transactional
    @Override
    public short postComment(ArticleComment comment, Integer articleId, Integer memberId) {
        Article oldArticle = articleBrowserRepository.findById(articleId).orElse(null);
        Integer commentNum = (oldArticle.getCommentNum());
        System.out.println("原留言數" + commentNum);
        if (comment.getCommentPost() == null) {
            return 0;
            // 後端空值驗證
        } else {
            System.out.println("留言新增成功");
            comment.setArticleId(articleId); // 將文章ID存到留言table
            comment.setMemId(memberId); // 將memberId存到留言table
            commentNum++;
            oldArticle.setCommentNum(commentNum);
            articleBrowserRepository.save(oldArticle);
            articleCommentRepository.save(comment);
            System.out.println("新增留言後留言數" + commentNum);
//            articleCommentRepository.save(comment);
            return 1;
        }

    }


    @Override
    public Article getByArticleId(Integer articleId) {
        Integer memId = articleBrowserRepository.getByArticleId(articleId).getMemId();
        this.memId = memId;
        return articleBrowserRepository.getByArticleId(articleId);
    }

    public String getMemNameByMemId() {
        Member member = memberRepository.findById(memId).orElse(null);
        String memberName = member.getMemName();
        return memberName;
    }

    public String getCommentMemNameByMemId() {
        Member member = memberRepository.findById(commentMemId).orElse(null);
        String memberName = member.getMemName();
        return memberName;
    }


    @Override
    public List<ArticlePicture> findPicByArticleId(Integer articleId) {

        return articlePictureRepository.getByArticleId(articleId);
    }
}
