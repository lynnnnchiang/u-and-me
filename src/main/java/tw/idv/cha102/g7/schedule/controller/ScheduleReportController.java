package tw.idv.cha102.g7.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.schedule.entity.ScheduleReport;
import tw.idv.cha102.g7.schedule.service.ScheduleReportService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/schRep")
public class ScheduleReportController {

    @Autowired
    private ScheduleReportService reportService;

    /**
     * 一般使用者/管理員
     * 檢舉行程
     * @param scheduleRep 欲檢舉行程的檢舉內容
     */
    @PostMapping("/member/add")
    public void insert(@RequestBody ScheduleReport scheduleRep, HttpServletRequest request) {
        reportService.insert(scheduleRep, request);
    }

    /**
     * 一般使用者/管理員
     * 修改檢舉內容
     * @param schRepId    行程檢舉編號ID
     * @param scheduleRep 修改完的檢舉資訊
     */
    @PutMapping("/{schRepId}")
    public void update(@PathVariable Integer schRepId,
                       @RequestBody ScheduleReport scheduleRep) {
        reportService.update(schRepId, scheduleRep);
    }

    /**
     * 管理員
     * 查詢所有被檢舉的行程
     * @return 返回查詢結果
     */
    @GetMapping
    public List<ScheduleReport> getAll() {
        List<ScheduleReport> list = reportService.findAll();
        return list;
    }

    /**
     * 管理員
     * 依照檢舉處理情形查詢檢舉清單
     * @param schRepSta 檢舉狀態
     * schRepSta 檢舉狀態
     * 0: 審核中,
     * 1: 已處理,
     * 2: 已撤銷
     * @return 返回查詢結果
     */
    @GetMapping("/status/{schRepSta}")
    public List<ScheduleReport> findByStatus(@PathVariable Short schRepSta){
        List<ScheduleReport> list = reportService.findByStatus(schRepSta);
        return list;
    }

    /**
     * 管理員
     * 查詢單一筆被檢舉的行程檢舉資料
     * @param schRepId 檢舉id
     * @return 返回查詢結果
     */
    @GetMapping("/getOne/{schRepId}")
    public ScheduleReport findById(@PathVariable Integer schRepId){
        ScheduleReport report = reportService.findBySchRepId(schRepId);
        return report;
    }

    /**
     * 管理員
     * 屏蔽行程
     * @param schId 被檢舉的行程ID
     */
    @GetMapping("/hide/{schId}")
    public ResponseEntity<?> hide(@PathVariable Integer schId) {
        try {
            reportService.hideById(schId);
            return ResponseEntity.ok("屏蔽成功！");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("操作失敗，找不到該行程！");
        }
    }

    /**
     * 管理員
     * 修改檢舉處理狀態
     * @param schReport 變更的檢舉處理(內含修改的檢舉處理狀態、檢舉處理的管理員ID等等)
     * schRepSta 檢舉狀態
     * 0: 審核中,
     * 1: 已處理,
     * 2: 已撤銷
     */
    @PutMapping("/modifySta/{schRepId}/{schPub}")
    public ResponseEntity<?> modifyStatus(HttpServletRequest request,
                                          @PathVariable Integer schRepId,
                                          @PathVariable Byte schPub,
                                          @RequestBody ScheduleReport schReport) {
            reportService.modifyStatus(request,schRepId,schPub,schReport);
            return ResponseEntity.ok("更改成功!");
    }


    /**
     * 管理員
     * 刪除被檢舉的行程內容(如移除行程中的行程標籤)或
     * 整個行程(待討論，因為關聯到此行程的FK要先刪除)
     * @param schId 被檢舉的行程ID
     */
    @DeleteMapping("/delete/{schId}")
    public ResponseEntity<?> delete(@PathVariable Integer schId) {
        try {
            reportService.deleteReportedSchedule(schId);
            return ResponseEntity.ok("刪除成功！");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("刪除失敗！");
        }
    }
}
