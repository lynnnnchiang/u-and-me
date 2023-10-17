package tw.idv.cha102.g7.article.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollection;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollectionId;

import java.util.List;

public interface ArticleCollectionRepository extends JpaRepository<ArticleCollection, ArticleCollectionId> {
    public List<ArticleCollection> findByCollectionId(ArticleCollectionId collectionId);

    @Query(value = "select * FROM article_collection  WHERE mem_id = :memId", nativeQuery = true)
    public ArticleCollectionId findByMemId(Integer memId);

//    public void deleteByCollectionId(ArticleCollectionId collectionId);

//    @Modifying
//    @Query(value="DELETE FROM ArticleCollection ac WHERE ac.articleId = :articleId AND ac.memId = :memId", nativeQuery = false)
//    public void deleteByArticleIdAndMemId( @Param("articleId") Integer articleId, @Param("memId") Integer memId);

//    public List<AttrCollectionDTO> findByMemNameContaining(String memName);


//    public List<CollectionDTO> findByMemId(Integer memId);


}
