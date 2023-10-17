package tw.idv.cha102.g7.group.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "group_picture")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupPicture implements Serializable {
    private static final long serialVersionUID = 6618518644686967842L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_pic_id")
    private Integer groupPicId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "group_pic", columnDefinition = "MEDIUMBLOB")
    @Lob
    private byte[] groupPic;

//    @ManyToOne
//    @JoinColumn(name = "group_id", insertable = false,updatable = false)
//    @JsonBackReference
//    private Group group;
}
