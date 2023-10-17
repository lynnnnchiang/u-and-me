package tw.idv.cha102.g7.schedule.service;

import org.springframework.web.bind.annotation.PathVariable;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.schedule.dto.ScheduleDayDTO;
import tw.idv.cha102.g7.schedule.dto.ScheduleDaysDTO;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.dto.TagToSchedulesDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

public interface ScheduleService {

    // 查詢所有公開行程清單，並依照起始日期降冪排序(分頁)
    List<Schedule> findAllPublicPaged(int page, int size);

    // 查詢所有公開行程清單，並依照起始日期升冪排序(分頁)
    List<Schedule> findAllPublicPagedASC(int page, int size);

    // 查詢所有公開行程清單，並依照起始日期降冪排序
    public List<Schedule> findAllPublic();

    // 依照行程名稱，查詢所有公開行程清單，並依照起始日期降冪排序
    public Stream<Schedule> findBySchNamePaged(String schName, int page);

    // 依照行程開始日期及結束日期，查詢所有期限內的公開行程清單，並依照起始日期降冪排序
    public List<Schedule> findBetweenDate(Date schStart, Date schEnd);

    // 依行程天數小到大，查詢公開行程及天數，並依照起始日期新到舊排序
    public Stream<ScheduleDayDTO> findOrderByDays(int page);

    // 依行程天數大到小，查詢公開行程及天數，並依照起始日期新到舊排序
    public Stream<ScheduleDayDTO> findOrderByDaysDESC(int page);

    // 依行程ID，查詢單一行程
    public Schedule getById(Integer schId);

    // 從單一行程中查詢對應標籤(但是有重複單一行程資料的問題)
    public List<TagToSchedulesDTO> findTagsInOneSchdule(Integer schId);

    // 查詢所有行程清單
    public List<Schedule> getAll();

    // 複製一個行程大綱及其細節
    public Schedule copyOneSchedule(Integer schId, Integer memId);

    /***
     * 以上為搜瀏覽公開行程相關功能
     * 以下為管理行程功能
     */

    // 查詢使用者自己所有建立過的行程清單(依起始日期降冪排序)
    public Stream<Schedule> getAllByMemId(Integer memId, int page);

    // 查詢使用者自己所有建立過的行程清單(依起始日期升冪排序)
    public Stream<Schedule> getAllByMemIdASC(Integer memId, int page);

    // 依照行程名稱查詢使用者自己建立過的相關行程清單(依照起始日期降冪排序)
    public Stream<Schedule> findByMemIdAndSchNameDESC(Integer memId, String schName, int page);

    // 依照行程天數(小到大)排序會員自己的行程
    public Stream<ScheduleDayDTO> findByMemIdOrderByDays(Integer memId, int page);

    // 依照行程天數(大到小)排序會員自己的行程
    public Stream<ScheduleDayDTO> findByMemIdOrderByDaysDESC(Integer memId, int page);

    // 新增一筆行程(包含選擇出發及結束日期、目的地標籤、行程名稱)
    public Schedule create(Schedule schedule);

    // 刪除一筆行程
    public String deleteOneSchedule(Integer schId);

    // 查詢單一行程後，對行程內容進行修改
    public String edit(Integer schId, Schedule schedule);

    // 屏蔽一筆行程
    public void hide(Integer schId);

    // 行程分享隱私權設定
    public Schedule privateSelect(Integer schId, Byte schPub);

    // 行程複製權限設定
    public Schedule copyrightSelect(Integer schId, Boolean schCopy);

    //宇航 > 查詢會員公開行程
    Stream<Schedule> findPublicSchByMemIdPaged(HttpServletRequest request, Integer page);
}
