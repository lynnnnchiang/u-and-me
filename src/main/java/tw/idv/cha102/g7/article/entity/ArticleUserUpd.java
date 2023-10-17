package tw.idv.cha102.g7.article.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="article")
public class ArticleUserUpd {
    @Id
    @Column(name="article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleId;
    @Column(name="article_state")
    private Short articleSta;
    @Column(name="comment_num")
    private Integer commentNum;

    @Column(name="article_con")
    private String articleContent;


    // 不放其他非使用者自行輸入的值進來，是因為如果放了但前台沒有該值回傳的話，DB就會顯示null而不是設定的default值

}
