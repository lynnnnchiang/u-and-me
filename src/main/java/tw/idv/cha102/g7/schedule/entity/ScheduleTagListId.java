package tw.idv.cha102.g7.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTagListId implements Serializable {

    @Column(name = "sch_id")
    private Integer schId;

    @Column(name = "schtag_id")
    private Integer schTagId;

}
