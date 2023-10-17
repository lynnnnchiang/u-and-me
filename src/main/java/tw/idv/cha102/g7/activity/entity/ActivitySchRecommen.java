package tw.idv.cha102.g7.activity.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySchRecommen implements Serializable {

    @EmbeddedId
    private Integer schId;
    private Integer activId;

}


//@Entity
//@Table(name = "activity_schedule_recommendation")
//public class ActivitySchRecommen implements Serializable {
//
//    @Id
//    @Column(name = "sch_id")
//    private Integer schId;
//
//    @Column(name = "activ_id")
//    private Integer activId;
//
//}