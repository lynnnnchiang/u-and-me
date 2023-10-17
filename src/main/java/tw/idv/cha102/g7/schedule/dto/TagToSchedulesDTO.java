package tw.idv.cha102.g7.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagToSchedulesDTO {

    private ScheduleTag tag;

    private List<Schedule> schedules;


    public TagToSchedulesDTO(Object[] objects) {
        this.tag = (ScheduleTag) objects[0];
        this.schedules = (List<Schedule>) objects[1];
    }
}
