package tw.idv.cha102.g7.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule_tag_list")
public class ScheduleTagList {

    @EmbeddedId
    public ScheduleTagListId scheduleTagListId;
}
