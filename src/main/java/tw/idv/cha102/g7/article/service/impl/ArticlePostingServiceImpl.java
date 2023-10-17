package tw.idv.cha102.g7.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticlePicture;
import tw.idv.cha102.g7.article.entity.ArticlePosting;
import tw.idv.cha102.g7.article.repo.ArticlePictureRepository;
import tw.idv.cha102.g7.article.repo.ArticlePostingRepository;
import tw.idv.cha102.g7.article.service.ArticlePostingService;

import java.util.List;

@Component
public class ArticlePostingServiceImpl implements ArticlePostingService {
    @Autowired
    ArticlePostingRepository articlePostingRepository;
    @Autowired
    ArticlePictureRepository articlePictureRepository;

    private Integer articleId;

    @Override
    @Transactional
    public int postArticle(ArticlePosting article, Integer memberId) {
        article.setMemId(memberId);
        if (article.getArticleTitle() == "" || article.getArticleContent()=="" ||article.getAcTypeId()==0) {
            return 0;
        } else {
            articlePostingRepository.save(article);
            Integer articleId = article.getArticleId();
            this.articleId = articleId;
            return 1;
        }


    }


    public int postPicsAndSaveArticleId(List<ArticlePicture> pics) {
        if (articleId == null) {
            return 0;
        } else {
            System.out.println(articleId);
            for (ArticlePicture picture : pics) {
                picture.setArticleId(articleId); // 设置每个图片对象的 articleId
            }
            articlePictureRepository.saveAll(pics);
            return 1;
        }
    }
}
