package tw.idv.cha102.g7.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.cha102.g7.schedule.dto.ScheduleDayDTO;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.service.ScheduleService;
import tw.idv.cha102.g7.schedule.service.ScheduleTagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedules")
public class ScheduleSearchController {

    @Autowired
    private ScheduleService service;
    @Autowired
    private ScheduleTagService tagService;

    /**
     * 一般使用者
     * 查詢所有最新公開行程並依分頁顯示
     *
     * @param page 當前分頁 (從0開始)(暫定顯示每頁6筆行程)
     * @return 返回所有公開行程列表
     */
    @GetMapping("/all/{page}")
    public List<Schedule> findAllPublic(@PathVariable int page) {
        return service.findAllPublicPaged(page, 6);
    }

    /**
     * 一般使用者
     * 查詢所有最新公開行程並依分頁顯示
     *
     * @param page 當前分頁 (從0開始)(暫定顯示每頁6筆行程)
     * @return 返回所有公開行程列表
     */
    @GetMapping("/allASC/{page}")
    public List<Schedule> findAllPublicASC(@PathVariable int page) {
        return service.findAllPublicPagedASC(page, 6);
    }


    /**
     * 隨機選取三個公開行程放置於首頁
     *
     * @return 返回隨機的公開行程列表
     */
    @GetMapping("/all")
    public Set<Schedule> findAll() {
        List<Schedule> scheduleList = service.findAllPublic();
        Set<Schedule> randomSchList = new HashSet<>();
        int randomIndex;
        for (int i = 0; randomSchList.size() < 3; i++) {
            randomIndex = (int) (Math.random() * 9 + 1);
            randomSchList.add(scheduleList.get(randomIndex));
        }
        return randomSchList;
    }


    /**
     * 一般使用者
     * 以行程名稱查詢行程
     *
     * @param keyword 欲查詢的名稱
     * @return 返回查詢結果
     */
    @GetMapping("/{keyword}/{page}")
    public List<Schedule> findBySchName(@PathVariable String keyword,
                                        @PathVariable int page) {
        return service.findBySchNamePaged(keyword, page).collect(Collectors.toList());
    }


    /**
     * 一般使用者
     * 以行程開始日期及結束日期，查詢期限內的行程
     *
     * @param schStart 行程開始日期
     * @param schEnd   行程結束日期
     * @return 返回查詢結果
     */
    @GetMapping("/dateBetween/{schStart}/{schEnd}")
    public List<Schedule> findBetweenDate(@PathVariable Date schStart,
                                          @PathVariable Date schEnd) {
        List<Schedule> schedules = service.findBetweenDate(schStart, schEnd);
        return schedules;
    }

    /**
     * 一般使用者
     * 以行程天數由小到大排序，查詢行程
     *
     * @return 返回查詢結果
     */
    @GetMapping("/days/{page}")
    public List<ScheduleDayDTO> findOrderByDays(@PathVariable int page) {
        List<ScheduleDayDTO> schedules = service.findOrderByDays(page).collect(Collectors.toList());
        return schedules;
    }

    /**
     * 一般使用者
     * 以行程天數由大到小排序，查詢行程
     *
     * @return 返回查詢結果
     */
    @GetMapping("/daysDESC/{page}")
    public List<ScheduleDayDTO> findOrderByDaysDESC(@PathVariable int page) {
        List<ScheduleDayDTO> schedules = service.findOrderByDaysDESC(page).collect(Collectors.toList());
        return schedules;
    }

    /**
     * 依照行程標籤id查詢所有關聯行程
     *
     * @param schTagId 行程標籤id
     * @return 與行程標籤id相關的所有公開行程
     */
    @GetMapping("/findSchedulesPublicByTagId/{schTagId}")
    public ResponseEntity<List<Schedule>> findSchedulesPublicByTagId(@PathVariable Integer schTagId) {
        return new ResponseEntity(tagService.findSchedulesBySchTagId(schTagId), HttpStatus.OK);
    }

    /**
     * 一般使用者
     * 查詢單一行程(依據行程ID)
     *
     * @return 返回查詢結果
     */
    @GetMapping("/schId/{schId}")
    public Schedule getOne(@PathVariable Integer schId) {
        return service.getById(schId);
    }

    /**
     * 會員
     * 複製單一行程(包含行程大綱及行程細節)
     *
     * @return 返回複製的行程大綱
     */
    @RequestMapping("copySchedule/{schId}")
    public Schedule copyOne(HttpServletRequest request, @PathVariable Integer schId) {
        HttpSession session = request.getSession();
        String jsessionId = session.getAttribute("memberId").toString();
        Integer memId = Integer.parseInt(jsessionId);
        return service.copyOneSchedule(schId, memId);
    }

}
