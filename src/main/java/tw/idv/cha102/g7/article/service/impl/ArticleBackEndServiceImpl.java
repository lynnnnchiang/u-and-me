package tw.idv.cha102.g7.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.repo.ArticleBackEndRepository;
import tw.idv.cha102.g7.article.service.ArticleBackEndService;
import tw.idv.cha102.g7.group.entity.Group;

import java.util.List;
@Component
public class ArticleBackEndServiceImpl implements ArticleBackEndService {
    @Autowired
    private ArticleBackEndRepository articleBackEndRepository;
    @Override
    public List<Article> getAll() {
        return articleBackEndRepository.findAll();
    }

    @Override
    public List<Article> findNonPublicArticle() {
        return articleBackEndRepository.findNonPublicArticle();
    }

    @Override
    public List<Article> getAllPaged(int page, int size) {
        Page<Article> pageResult = articleBackEndRepository.findAll(
                PageRequest.of(page, //查詢的頁數 從0開始
                        size,//查詢的每頁筆數
                        Sort.by("articleId").ascending())); //依articleId欄位升冪排序
//        可能會運用到的方法
//        pageResult.getNumberOfElements(); //本頁筆數
//        pageResult.getSize(); //每頁筆數
//        pageResult.getTotalElements(); //全部筆數
//        pageResult.getTotalPages(); //全部頁數

        List<Article> articleList = pageResult.getContent();
        return articleList;
    }
}
