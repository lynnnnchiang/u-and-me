package tw.idv.cha102.g7.article.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.article.entity.ArticlePosting;


@Repository
public interface ArticlePostingRepository extends JpaRepository<ArticlePosting, Integer> {

}
