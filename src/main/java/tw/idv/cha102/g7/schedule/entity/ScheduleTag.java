package tw.idv.cha102.g7.schedule.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule_tag")
public class ScheduleTag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "schtag_id")
    private Integer schTagId;

    @Column(name = "schtag_name")
    private String schTagName;
}
