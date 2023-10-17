package tw.idv.cha102.g7.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.ArticlePicture;
import tw.idv.cha102.g7.article.repo.ArticleBrowserRepository;
import tw.idv.cha102.g7.article.repo.ArticlePictureRepository;
import tw.idv.cha102.g7.article.repo.ArticleUserEditRepository;
import tw.idv.cha102.g7.article.service.ArticleUserEditService;

import java.util.List;

@Service
public class ArticleUserEditServiceImpl implements ArticleUserEditService {
    @Autowired
    private ArticleUserEditRepository articleUserEditRepository;
    @Autowired
    private ArticleBrowserRepository articleBrowserRepository;
    @Autowired
    private ArticlePictureRepository articlePictureRepository;

    // 使用者「我的文章」頁面
    @Override
    public List<Article> findByMemId(Integer memId) {
        return articleUserEditRepository.findByMemId(memId);
    }

    // 使用者編輯文章(應該有錯)
    @Override
    public String userEdit(Article updArticle) {
        Integer articleId = updArticle.getArticleId();
        Article oldArticle = articleUserEditRepository.findById(articleId).orElse(null);
        if (oldArticle != null) {
            oldArticle.setArticleTitle(updArticle.getArticleTitle());
            oldArticle.setArticleContent(updArticle.getArticleContent());
            oldArticle.setAcTypeId(updArticle.getAcTypeId());

            articleUserEditRepository.save(oldArticle);
            return "更新成功！";
        } else {
            return "更新失敗，查詢的行程不存在";
        }
    }

    @Override
    public String adminEdit(Article updArticle) {
        Integer articleId = updArticle.getArticleId();
        Article oldArticle = articleUserEditRepository.findById(articleId).orElse(null);
        if (oldArticle != null) {
            oldArticle.setArticleSta(updArticle.getArticleSta());
            articleUserEditRepository.save(oldArticle);
            return "更新成功！";
        } else {
            return "更新失敗，查詢的行程不存在";
        }
    }

    @Override
    public ResponseEntity<String> deleteArticleByArticleId(Integer articleId) {
        articleUserEditRepository.deleteById(articleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deletePicById(Integer articlePicId) {
        articlePictureRepository.deleteById(articlePicId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public void postPicsAndSaveArticleId(List<ArticlePicture> pics,Integer articleId) {
        System.out.println(articleId);

        for (ArticlePicture picture : pics) {
            picture.setArticleId(articleId); // 设置每个图片对象的 articleId
        }
        articlePictureRepository.saveAll(pics);
    }
}
