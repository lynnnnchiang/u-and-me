package tw.idv.cha102.g7.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.article.entity.Article;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollection;
import tw.idv.cha102.g7.article.entity.DTO.ArticleCollectionId;
import tw.idv.cha102.g7.article.repo.ArticleBrowserRepository;
import tw.idv.cha102.g7.article.repo.ArticleCollectionRepository;
import tw.idv.cha102.g7.article.service.ArticleCollectionService;
import tw.idv.cha102.g7.article.service.ArticleSingleArticleService;
import tw.idv.cha102.g7.member.repo.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Component
public class ArticleCollectionServiceImpl implements ArticleCollectionService {
    @Autowired
    private ArticleCollectionRepository articleCollectionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArticleBrowserRepository articleBrowserRepository;
    @Autowired
    private ArticleSingleArticleService articleSingleArticleService;

//    public TreeSet<Member> returnMemsByArticleId(Integer articleId) {
//        List<ArticleCollection> articleCollections = articleCollectionRepository.findAll();
//        TreeSet<Member> members = new TreeSet<>();
//
//        for (ArticleCollection articleCollection : articleCollections) {
//            if (articleCollection.getCollectionId().getArticleId()==articleId) {
//                members.add(memberRepository.findById(articleCollection.getCollectionId().getMemId()).orElse(null));
//            }
//        }
//        return members;
//    }


    @Override
    public List<Article> findArticleByMem(HttpServletRequest request,
                                          HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        // 取得memberId屬性
        if (obj == null) {
            return new ArrayList<>();
            // 若屬性為null，則表示會員未登入，則回傳空陣列
        }
        Integer memId = Integer.parseInt(obj.toString());
        // 若屬性不為空值，則轉為整數儲存
        List<ArticleCollection> articleCollections = articleCollectionRepository.findAll();
        List<Article> returnList = new ArrayList<>();
        for (ArticleCollection articleCollection : articleCollections) {
            Article article = articleBrowserRepository.findById(articleCollection.getCollectionId().getArticleId()).orElse(null);
            if (articleCollection.getCollectionId().getMemId().equals(memId) &&
                    article.getArticleSta() != 0) {
                returnList.add(article);
                System.out.println(article);
            }
        }
        return returnList;
    }


    /**
     * 先查詢有無此會員，以及有無此文章，
     * 若皆有則刪除已有的收藏
     *
     * @param articleCollection
     * @return String "success" or "failed"
     */
    @Override
    @Transactional
    public String removeArticleFromCollection(ArticleCollection articleCollection) {
        if (articleCollectionRepository.findById(articleCollection.getCollectionId()) == null) {
            return "failed";
        }
        articleCollectionRepository.deleteById(articleCollection.getCollectionId());
        return "success";

    }

    @Override
    @Transactional
    public Integer AddOrRemoveCollection(ArticleCollection articleCollection) {
        ArticleCollection collectionId = articleCollectionRepository.findById(articleCollection.getCollectionId()).orElse(null);
        System.out.println(collectionId);

        if (collectionId != null) {
            articleCollectionRepository.deleteById(articleCollection.getCollectionId());
            System.out.println("從資料庫中刪除");
            return 0;
        } else {
            articleCollectionRepository.save(articleCollection);
            System.out.println("加入資料庫");
            return 1;

        }

    }

    @Override
    public short CollectionExsitOrNot(ArticleCollection articleCollection) {
        ArticleCollection collectionId = articleCollectionRepository.findById(articleCollection.getCollectionId()).orElse(null);
        if (collectionId != null) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 先查詢有無此會員，以及有無此貼文，
     * 若皆有則新增或更新已有的收藏
     *
     * @param articleCollection
     * @return String
     */
    @Override
    public String addArticleToCollection(ArticleCollection articleCollection) {
        if (articleBrowserRepository.findById(articleCollection.getCollectionId().getArticleId()).equals(null)
                || memberRepository.findById(articleCollection.getCollectionId().getMemId()).equals(null)) {
            return "failed";
        }
        articleCollectionRepository.save(articleCollection);
        return "success";
    }

    /**
     * 透過會員編號，找出該會員的所有景點收藏
     *
     * @param memId
     * @return List<AttrCollectionDTO>
     */

    @Override
    public List<Article> findArticleByMemId(Integer memId) {
        List<ArticleCollection> allCollection = articleCollectionRepository.findAll();
        List<Article> articleCollection = new ArrayList<>();
        for (ArticleCollection collection : allCollection) {
            if (collection.getCollectionId().getMemId().equals(memId)) {
                Article art = articleBrowserRepository.getByArticleId(collection.getCollectionId().getArticleId());
                articleCollection.add(art);
                System.out.println(collection.getCollectionId());
            }
//
        }

        System.out.println(articleCollection);
        return articleCollection;
    }


    /**
     * 透過會員編號，找出該會員的所有文章收藏 過濾已下架景點
     *
     * @param memId
     * @return List<AttrCollectionDTO>
     */
    @Override
    public List<Article> findArticleByMemIdFilter(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Integer memId) {
        List<Article> returnList = new ArrayList<>();
        List<ArticleCollection> articleCollectionsList = articleCollectionRepository.findAll();
        for (ArticleCollection articleCollection : articleCollectionsList) {
            Article article = articleBrowserRepository.findById(articleCollection.getCollectionId().getArticleId()).orElse(null);
            if (articleCollection.getCollectionId().getMemId() == memId &&
                    article.getArticleSta() != 0) {
                returnList.add(article);
            }
        }
        return returnList;
    }

}
