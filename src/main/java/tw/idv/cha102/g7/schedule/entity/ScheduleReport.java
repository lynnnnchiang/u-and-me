package tw.idv.cha102.g7.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule_rep")
public class ScheduleReport {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "schrep_id")
    private Integer schRepId;

    @Column(name = "mem_id")
    private Integer memId;

    @Column(name = "sch_id")
    private Integer schId;

    @Column(name = "schrep_con")
    private String schRepCon;

    @Column(name = "host_id")
    private Integer hostId;

    @Column(name = "schrep_sta", insertable = false)
    private Short schRepSta;
}
