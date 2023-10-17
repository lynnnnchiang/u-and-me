package tw.idv.cha102.g7.article.service;

import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.group.entity.Group;

import java.util.List;

public interface ArticleBackEndService {
    public List<Article> getAll();
    public List<Article> findNonPublicArticle();

    List<Article> getAllPaged(int page, int size);

}
