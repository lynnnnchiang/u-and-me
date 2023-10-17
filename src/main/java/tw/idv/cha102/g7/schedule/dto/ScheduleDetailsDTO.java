package tw.idv.cha102.g7.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleDetail;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDetailsDTO {

    private Schedule schedule;

    private List<ScheduleTag> scheduleTags;

    private List<ScheduleDetail> scheduleDetails;


    public ScheduleDetailsDTO(Object[] objects) {
        this.schedule = (Schedule) objects[0];
        this.scheduleTags = (List<ScheduleTag>) objects[1];
        this.scheduleDetails = (List<ScheduleDetail>) objects[2];
    }
}
