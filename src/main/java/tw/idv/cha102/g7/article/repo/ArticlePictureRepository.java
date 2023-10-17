package tw.idv.cha102.g7.article.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.article.entity.ArticlePicture;

import java.util.List;

@Repository
public interface ArticlePictureRepository extends JpaRepository<ArticlePicture, Integer> {

    //查詢特定article_id 的圖片
    List<ArticlePicture> getByArticleId(Integer articleId);
}
