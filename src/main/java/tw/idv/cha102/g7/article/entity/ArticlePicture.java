package tw.idv.cha102.g7.article.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article_picture")
public class ArticlePicture implements Serializable {
    private static final long serialVersionUID = 6618518644686967842L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articlepic_id")
    private Integer articlePicId;
    @Column(name = "article_id")
    private Integer articleId;
    @Column(name = "article_pic")
    private byte[] articlePic;


}