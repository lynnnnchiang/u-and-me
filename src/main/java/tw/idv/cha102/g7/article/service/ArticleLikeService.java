package tw.idv.cha102.g7.article.service;

import tw.idv.cha102.g7.article.entity.DTO.ArticleLike;

public interface ArticleLikeService {
    // 按讚的功能用OnClick監聽
    // 新增留言後，頁面留言數++

//    public String smashLike(ArticleLike articleLike);

    public Integer AddOrRemoveLike(ArticleLike articleLike);

    public short LikeExsitOrNot(ArticleLike articleLike);
}
