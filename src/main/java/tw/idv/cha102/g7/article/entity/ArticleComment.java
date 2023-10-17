package tw.idv.cha102.g7.article.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="article_comment")
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comm_id")
    private Integer commId;
    @Column(name = "article_id")
    private Integer articleId;
    @Column(name = "mem_id")
    private Integer memId;
    @Column(name = "comment_post")
    private String commentPost;
//    @Column(name = "comment_sta")
//    private Short commentSta;

    public ArticleComment(Integer articleId, Integer memId, String commentPost) {
        this.articleId = articleId;
        this.memId = memId;
        this.commentPost = commentPost;
    }
//    @Column(name = "comment_time")
//    private Date commentTime;
    // 不放comment_time是因為，若放了但沒有回傳值的畫，DB會回傳null
}
