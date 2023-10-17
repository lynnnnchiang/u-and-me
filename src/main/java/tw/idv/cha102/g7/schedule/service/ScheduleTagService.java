package tw.idv.cha102.g7.schedule.service;

import tw.idv.cha102.g7.schedule.dto.TagsInScheduleDTO;
import tw.idv.cha102.g7.schedule.dto.TagToSchedulesDTO;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;
import tw.idv.cha102.g7.schedule.entity.ScheduleTagList;
import tw.idv.cha102.g7.schedule.entity.ScheduleTagListId;

import java.util.List;

public interface ScheduleTagService {

    // 查詢所有標籤
    public List<ScheduleTag> findAll();

    // 依照標籤id查詢對應標籤
    public ScheduleTag findById(Integer schTagId);

    // 依照標籤名稱查詢對應標籤
    public List<ScheduleTag> findByName(String schTagName);

    // 依照標籤id查詢對應行程
    public List<Schedule> findSchedulesBySchTagId(Integer schTagId);

    // 依照標籤名稱查詢對應行程
    public List<TagToSchedulesDTO> findSchedulesBySchTagName(String schTagName);

    // 依照行程id查詢對應標籤
    public List<ScheduleTag> findTagsBySchId(Integer schId);

    // 新增行程標籤
    public ScheduleTag createTag(ScheduleTag tag);

    // 刪除行程標籤
    public void deleteTag(Integer schTagId);

    // 刪除與行程相關聯的行程與行程標籤清單
    public int deleteScheduleTagListBySchId(Integer schId);

    // 新增行程標籤至行程標籤關聯表格中
    public List<ScheduleTagList> addTagsInSchedule(List<ScheduleTagListId> scheduleTagListIds);

}
