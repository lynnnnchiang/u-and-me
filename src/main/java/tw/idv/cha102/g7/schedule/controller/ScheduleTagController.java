package tw.idv.cha102.g7.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.schedule.dto.TagToSchedulesDTO;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;
import tw.idv.cha102.g7.schedule.entity.ScheduleTagList;
import tw.idv.cha102.g7.schedule.entity.ScheduleTagListId;
import tw.idv.cha102.g7.schedule.service.ScheduleTagService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/schTag")
public class ScheduleTagController {

    @Autowired
    private ScheduleTagService service;

    /**
     * 查詢所有的行程標籤清單
     *
     * @return 所有的行程標籤清單
     */
    @GetMapping("/")
    public List<ScheduleTag> finaAllTag() {
        return service.findAll();
    }

    /**
     * 查詢特定的行程標籤
     *
     * @param schTagId 行程標籤id
     * @return 特定行程標籤
     */
    @GetMapping("/id/{schTagId}")
    public ScheduleTag findById(@PathVariable Integer schTagId) {
        return service.findById(schTagId);
    }

    /**
     * 依照行程標籤名稱查詢行程標籤
     *
     * @param schTagName 行程標籤名稱
     * @return 名稱相似的行程標籤
     */
    @GetMapping("/name/{schTagName}")
    public List<ScheduleTag> findByName(@PathVariable String schTagName) {
        return service.findByName(schTagName);
    }

    /**
     * 依照行程標籤名稱查詢所有關聯行程
     *
     * @param schTagName 行程標籤名稱
     * @return 與行程標籤名稱相關的所有行程
     */
    @GetMapping("/DTOname/{schTagName}")
    public List<TagToSchedulesDTO> findSchedulesByTagName(@PathVariable String schTagName) {
        return service.findSchedulesBySchTagName(schTagName);
    }

    /**
     * 依照行程id查詢所有關聯行程標籤
     *
     * @param schId 行程id
     * @return 與行程相關的所有行程標籤
     */
    @GetMapping("/schId/{schId}")
    public List<ScheduleTag> findTagsBySchId(@PathVariable Integer schId) {
        return service.findTagsBySchId(schId);
    }

    /**
     * 新增行程標籤
     *
     * @param tag 行程標籤
     * @return 新增的行程標籤本身
     */
    @PostMapping("/add")
    public ResponseEntity<?> createTag(@RequestBody ScheduleTag tag) {
        return new ResponseEntity(service.createTag(tag), HttpStatus.OK);
    }

    /**
     * 刪除行程標籤
     *
     * @param schTagId 行程標籤id
     * @return 刪除成功
     */
    @RequestMapping("/delete/{schTagId}")
    public ResponseEntity<?> deleteTag(@PathVariable Integer schTagId) {
        service.deleteTag(schTagId);
        return new ResponseEntity("刪除成功！", HttpStatus.OK);
    }

    /**
     * 刪除行程時與其關聯的所有行程標籤
     *
     * @param schId 行程id
     * @return 刪除成功資料筆數
     */
    @RequestMapping("/deleteTagListInSch/{schId}")
    public ResponseEntity<?> deleteTagsInSchedule(@PathVariable Integer schId) {
        int deleteData = service.deleteScheduleTagListBySchId(schId);
        return new ResponseEntity("共刪除" + deleteData + "筆資料", HttpStatus.OK);
    }


    /**
     * 新增行程標籤至關聯行程
     *
     * @param scheduleTagListId  行程與標籤關聯id List
     * @return 新增的行程與標籤id關聯清單
     */
    @RequestMapping("/addTagListInSch")
    public ResponseEntity<?> addTagsInSchedule(@RequestBody List<ScheduleTagListId> scheduleTagListId) {
        List<ScheduleTagList> addTagsInScheduleList = service.addTagsInSchedule(scheduleTagListId);
        return new ResponseEntity(addTagsInScheduleList, HttpStatus.OK);
    }

}
