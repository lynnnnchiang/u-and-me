package tw.idv.cha102.g7.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.DTO.ArticleLike;
import tw.idv.cha102.g7.article.repo.ArticleBrowserRepository;
import tw.idv.cha102.g7.article.repo.ArticleLikeRepository;
import tw.idv.cha102.g7.article.repo.ArticleUserEditRepository;
import tw.idv.cha102.g7.article.service.ArticleLikeService;
import tw.idv.cha102.g7.article.service.ArticleSingleArticleService;
import tw.idv.cha102.g7.member.repo.MemberRepository;

import java.util.Optional;


@Component
public class ArticleLikeServiceImpl implements ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArticleBrowserRepository articleBrowserRepository;
    @Autowired
    private ArticleSingleArticleService articleSingleArticleService;


    @Override
    @Transactional
    public Integer AddOrRemoveLike(ArticleLike articleLike) {
        ArticleLike articleLikeId = articleLikeRepository.findById(articleLike.getLikeId()).orElse(null);
        System.out.println(articleLikeId); // 查詢資料庫是否存在MemId&ArticleId組合

        Integer articleId = articleLike.getLikeId().getArticleId();
        Article oldArticle = articleBrowserRepository.findById(articleId).orElse(null);
        // 在article表格中找出該篇article

        Integer likeNum = (oldArticle.getArticleLike());
        System.out.println("原本讚數"+likeNum);

        if (articleLikeId != null) {
            articleLikeRepository.deleteById(articleLike.getLikeId());
            System.out.println("移除讚");
            likeNum--;
            oldArticle.setArticleLike(likeNum);
            System.out.println("點擊後讚數"+likeNum);
            return 0;
        } else {
            articleLikeRepository.save(articleLike);
            System.out.println("按讚");
            likeNum++;
            oldArticle.setArticleLike(likeNum);
            System.out.println("點擊後讚數"+likeNum);

            return 1;

        }

    }

    @Override
    public short LikeExsitOrNot(ArticleLike articleLike) {
        ArticleLike articleLikeId = articleLikeRepository.findById(articleLike.getLikeId()).orElse(null);
        if (articleLikeId != null) {
            System.out.println(articleLikeId.getLikeId().getMemId());
            return 1;
        } else {
            return 0;
        }
    }


}
