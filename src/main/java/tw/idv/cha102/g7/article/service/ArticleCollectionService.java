package tw.idv.cha102.g7.article.service;

import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollection;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollectionId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ArticleCollectionService {
    public String addArticleToCollection(ArticleCollection articleCollection);

    public List<Article> findArticleByMem(HttpServletRequest request,
                                          HttpServletResponse response);

    public String removeArticleFromCollection(ArticleCollection articleCollection);

    public List<Article> findArticleByMemId(Integer memId);

    public List<Article> findArticleByMemIdFilter(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Integer memId);

    public Integer AddOrRemoveCollection(ArticleCollection articleCollection);

    public short CollectionExsitOrNot(ArticleCollection articleCollection);

    }
