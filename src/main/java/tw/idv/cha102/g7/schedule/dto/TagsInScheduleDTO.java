package tw.idv.cha102.g7.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsInScheduleDTO {

    private Schedule schedule;
    private List<ScheduleTag> scheduleTags;


    public TagsInScheduleDTO(Object[] objects) {
        this.schedule = (Schedule) objects[0];
        this.scheduleTags = (List<ScheduleTag>) objects[1];
    }
}
