package tw.idv.cha102.g7.article.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollection;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollectionId;
import tw.idv.cha102.g7.article.entity.DTO.ArticleLike;
import tw.idv.cha102.g7.article.entity.DTO.ArticleLikeId;

import java.util.List;
@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, ArticleLikeId> {
    public List<ArticleLike> findByLikeId(ArticleLikeId likeId);

    @Query(value = "select * FROM article_like  WHERE mem_id = :memId", nativeQuery = true)
    public ArticleCollectionId findByMemId(Integer memId);

//    public void deleteByCollectionId(ArticleCollectionId collectionId);

//    @Modifying
//    @Query(value="DELETE FROM ArticleCollection ac WHERE ac.articleId = :articleId AND ac.memId = :memId", nativeQuery = false)
//    public void deleteByArticleIdAndMemId( @Param("articleId") Integer articleId, @Param("memId") Integer memId);

//    public List<AttrCollectionDTO> findByMemNameContaining(String memName);


//    public List<CollectionDTO> findByMemId(Integer memId);


}
