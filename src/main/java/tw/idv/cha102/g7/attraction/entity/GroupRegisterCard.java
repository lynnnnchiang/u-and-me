package tw.idv.cha102.g7.attraction.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "group_register")
public class GroupRegisterCard {
    @Id
    @Column(name = "mem_id")
    private Integer memId;
    @Column(name = "gr_idcard")
    private String groupIdCard;
    @Column(name = "gr_idcard_pic")
    private String groupIdCardPic;

}
