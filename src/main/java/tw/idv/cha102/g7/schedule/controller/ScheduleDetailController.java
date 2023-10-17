package tw.idv.cha102.g7.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.schedule.entity.ScheduleDetail;
import tw.idv.cha102.g7.schedule.service.ScheduleDetailService;
import tw.idv.cha102.g7.schedule.service.ScheduleService;
import tw.idv.cha102.g7.schedule.service.ScheduleTagService;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/schDetails")
public class ScheduleDetailController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleDetailService detailService;

    @Autowired
    private ScheduleTagService tagService;


    // 顯示單一行程詳細資訊(行程細節)，並按照起始時間排序
    @GetMapping("/{schId}")
    public List<ScheduleDetail> findScheduleDetails(@PathVariable Integer schId) {
        return detailService.findBySchId(schId);
    }


    @RequestMapping("/update")
    public ResponseEntity<?> updateDetail(@RequestBody ScheduleDetail scheduleDetail) {
        try {
            detailService.updateDetails(scheduleDetail);
            return ResponseEntity.ok("更新成功!");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("資料存入失敗！");
        }
    }

    @RequestMapping("/delete/{schdeId}")
    public ResponseEntity<?> deleteOneDetail(@PathVariable Integer schdeId) {
        try {
            detailService.deleteDetail(schdeId);
            return ResponseEntity.ok("刪除成功!");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("刪除失敗！");
        }
    }

    @RequestMapping("/deleteDetailsInSch/{schId}")
    public ResponseEntity<?> deleteDetailsInSch(@PathVariable Integer schId) {
        try {
            int deleteData = detailService.deleteDetailsInSch(schId);
            return new ResponseEntity("成功刪除" + deleteData + "筆行程細節", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @RequestMapping("/addOne")
    public ResponseEntity<?> addOneDetail(@RequestBody ScheduleDetail scheduleDetail) {
        return new ResponseEntity(detailService.addDetail(scheduleDetail), HttpStatus.OK);
    }

}
