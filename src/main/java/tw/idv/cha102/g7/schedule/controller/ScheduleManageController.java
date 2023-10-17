package tw.idv.cha102.g7.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.schedule.dto.ScheduleDayDTO;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/mySch")
public class ScheduleManageController {

    @Autowired
    private ScheduleService service;

    /**
     * 會員
     * 查詢使用者自己所有建立過的行程清單(依照起始日期降冪排序)
     *
     * @param memId 會員id
     * @param page  頁數
     * @return 返回查詢結果
     */
    @GetMapping("/my/{memId}/{page}")
    public ResponseEntity<List<Schedule>> findByMemId(@PathVariable Integer memId,
                                                      @PathVariable int page) {
        List<Schedule> mySchedules = service.getAllByMemId(memId, page)
                .collect(Collectors.toList());
        return new ResponseEntity(mySchedules, HttpStatus.OK);
    }

    /**
     * 會員
     * 查詢使用者自己所有建立過的行程清單(依照起始日期升冪排序)
     *
     * @param page  頁數
     * @return 返回查詢結果
     */
    @GetMapping("/my/{page}")
    public ResponseEntity<List<Schedule>> findByMemIdDESC(HttpServletRequest request,
                                                          @PathVariable int page) {
        HttpSession session = request.getSession();
        String jsessionId = session.getAttribute("memberId").toString();
        Integer memId = parseInt(jsessionId);
        List<Schedule> mySchedules = service.getAllByMemIdASC(memId, page)
                .collect(Collectors.toList());
        return new ResponseEntity(mySchedules, HttpStatus.OK);
    }

    /**
     * 會員
     * 以行程名稱查詢會員專屬行程(依照起始日期降冪排序)
     *
     * @param memId   會員id
     * @param keyword 欲查詢的名稱
     * @param page    頁數
     * @return 返回查詢結果
     */
    @GetMapping("/{keyword}/{memId}/{page}")
    public ResponseEntity<List<Schedule>> findByMemIdAndSchNameOrderBySchStartDESC(@PathVariable Integer memId,
                                                                                   @PathVariable String keyword,
                                                                                   @PathVariable int page) {
        List<Schedule> mySchedulesName = service.findByMemIdAndSchNameDESC(memId, keyword, page)
                .collect(Collectors.toList());
        return new ResponseEntity(mySchedulesName, HttpStatus.OK);
    }

    /**
     * 會員
     * 以行程名稱查詢會員專屬行程(依照起始日期升冪排序)
     *
     * @param memId   會員id
     * @param keyword 欲查詢的名稱
     * @param page    頁數
     * @return 返回查詢結果
     */
    @GetMapping("/ASC/{keyword}/{memId}/{page}")
    public ResponseEntity<List<Schedule>> findByMemIdAndSchNameOrderBySchStartASC(@PathVariable Integer memId,
                                                                                  @PathVariable String keyword,
                                                                                  @PathVariable int page) {
        List<Schedule> mySchedulesNameASC = service.findByMemIdAndSchNameDESC(memId, keyword, page)
                .sorted(Comparator.comparing(Schedule::getSchStart))
                .collect(Collectors.toList());
        return new ResponseEntity(mySchedulesNameASC, HttpStatus.OK);
    }

    /**
     * 會員
     * 以行程天數由小到大排序，查詢該會員的行程
     *
     * @param page 頁數
     * @return 返回查詢結果
     */
    @GetMapping("/days/{page}")
    public ResponseEntity<List<ScheduleDayDTO>> findOrderByDays(HttpServletRequest request, @PathVariable int page) {
        HttpSession session = request.getSession();
        String jsessionId = session.getAttribute("memberId").toString();
        Integer memId = parseInt(jsessionId);
        List<ScheduleDayDTO> schedules = service.findByMemIdOrderByDays(memId, page)
                .collect(Collectors.toList());
        return new ResponseEntity(schedules, HttpStatus.OK);
    }

    /**
     * 會員
     * 以行程天數由大到小排序，查詢該會員的行程
     *
     * @param page 頁數
     * @return 返回查詢結果
     */
    @GetMapping("/daysDESC/{page}")
    public ResponseEntity<List<ScheduleDayDTO>> findOrderByDaysDESC(HttpServletRequest request, @PathVariable int page) {
        HttpSession session = request.getSession();
        String jsessionId = session.getAttribute("memberId").toString();
        Integer memId = parseInt(jsessionId);
        List<ScheduleDayDTO> schedules = service.findByMemIdOrderByDaysDESC(memId, page)
                .collect(Collectors.toList());
        return new ResponseEntity(schedules, HttpStatus.OK);
    }


    /**
     * 會員
     * 新增一筆行程(包含選擇出發及結束日期、目的地標籤、行程名稱)
     *
     * @param schedule 欲新增的行程
     * @return 返回新增的行程
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Schedule schedule) {
        return new ResponseEntity(service.create(schedule), HttpStatus.OK);
    }

    /**
     * 會員
     * 刪除一個行程
     *
     * @param schId 行程id
     * @return 刪除成功或失敗
     */
    @RequestMapping("/delete/{schId}")
    public ResponseEntity<?> delete(@PathVariable Integer schId) {
        try {
            String deleteMsg = service.deleteOneSchedule(schId);
            return new ResponseEntity(deleteMsg, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 會員
     * 對單一行程大綱內容進行修改
     *
     * @param schId    行程id
     * @param schedule 行程
     * @return 修改成功或失敗
     */
    @RequestMapping("/edit/{schId}")
    public ResponseEntity<?> edit(@PathVariable Integer schId,
                                  @RequestBody Schedule schedule) {
        try {
            String editResult = service.edit(schId, schedule);
            return new ResponseEntity(editResult, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 屏蔽一筆行程
    @GetMapping("/hide/{schId}")
    public ResponseEntity<?> hide(@PathVariable Integer schId) {
        try {
            service.hide(schId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    /**
     * 會員
     * 公開行程權限設定
     *
     * @param schId  行程id
     * @param schPub 公開權限
     *               0: 私密,
     *               1: 透過連結分寫,
     *               2: 公開,
     * @return 行程物件
     */
    @RequestMapping("/private/{schId}/{schPub}")
    public ResponseEntity<?> privateSelect(@PathVariable Integer schId,
                                           @PathVariable Byte schPub) {
        return ResponseEntity.ok(service.privateSelect(schId, schPub));

    }

    /**
     * 會員
     * 複製行程權限設定
     *
     * @param schId   行程id
     * @param schCopy 複製權限
     *                true: 可複製,
     *                false: 不可複製,
     * @return 行程物件
     */
    @RequestMapping("/copyright/{schId}/{schCopy}")
    public ResponseEntity<?> copyrightSelect(@PathVariable Integer schId,
                                             @PathVariable Boolean schCopy) {
        return ResponseEntity.ok(service.copyrightSelect(schId, schCopy));

    }

    @GetMapping("/member/publicSch/{page}")
    public ResponseEntity<Stream<Schedule>> findPublicSchByMemId(HttpServletRequest request,
                                                                       @PathVariable Integer page){
        return ResponseEntity.ok(service.findPublicSchByMemIdPaged(request, page));
    }
}
