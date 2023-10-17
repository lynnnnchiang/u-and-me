package tw.idv.cha102.g7.article.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="article")
public class ArticlePosting implements Serializable {
    // 為了照片而實作序列化
    private static final long serialVersionUID = 2072014924350494700L;

    @Id
    @Column(name="article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleId;

//    @Column(name="mem_id")
//    private Integer memId;

    @Column(name="ac_type_id")
    private Integer acTypeId;

    @Column(name="mem_id")
    private Integer memId;

    @Column(name="article_title")
    private String articleTitle;

    @Column(name="article_con")
    private String articleContent;

    @Column(name="update_time")
    private String updateTime;

//    @Column(name = "article_pic")
//    private byte[] cover;




    // 不放其他非使用者自行輸入的值進來，
    // 因為如果前台沒有該值回傳的話，DB就會顯示null而不是設定的default值


}
