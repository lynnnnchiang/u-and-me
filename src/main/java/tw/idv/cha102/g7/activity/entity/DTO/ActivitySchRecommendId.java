package tw.idv.cha102.g7.activity.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


// 複合主鍵
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySchRecommendId implements Serializable {
    // 可以序列化和反序列化
    @Column(name = "sch_id")
    public Integer schId;

    @Column(name = "activ_id")
    public Integer activId;

}

