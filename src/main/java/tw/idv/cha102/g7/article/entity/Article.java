package tw.idv.cha102.g7.article.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="article")
public class Article implements Serializable {
    // 為了照片而實作序列化
    private static final long serialVersionUID = 2072014924350494700L;
    @Id
    @Column(name="article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleId;

    @Column(name="mem_id")
    private Integer memId;

    @Column(name="ac_type_id")
    private short acTypeId;

    @Column(name="article_title")
    private String articleTitle;

    @Column(name="article_time")
    private Date articleTime;

    @Column(name="article_like")
    private Integer articleLike;

    @Column(name="comment_num")
    private Integer commentNum;

    @Column(name="article_con")
    private String articleContent;

    @Column(name="article_state")
    private Short articleSta;



    // Adminupd
    public Article(Integer articleId, Short articleSta) {
        this.articleId = articleId;
        this.articleSta = articleSta;
    }

    //UserUpd

    public Article(String articleTitle, String articleContent) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
    }
}
