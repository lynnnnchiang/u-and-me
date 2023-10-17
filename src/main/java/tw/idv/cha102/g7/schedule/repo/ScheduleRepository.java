package tw.idv.cha102.g7.schedule.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.schedule.dto.ScheduleDayDTO;
import tw.idv.cha102.g7.schedule.dto.ScheduleDaysDTO;
import tw.idv.cha102.g7.schedule.entity.Schedule;

import java.sql.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    // 查詢所有公開行程清單，並依照起始日期降冪排序
    @Query(value = "SELECT * FROM schedules WHERE sch_pub = 2 ORDER BY sch_start DESC", nativeQuery = true)
    public List<Schedule> findAllPublic();

    // 查詢所有公開行程清單，並依照起始日期升冪排序
    @Query(value = "SELECT * FROM schedules WHERE sch_pub = 2 ORDER BY sch_start ASC", nativeQuery = true)
    public List<Schedule> findAllPublicASC();

    // 依照行程名稱，查詢所有公開行程清單，並依照起始日期降冪排序
    @Query(value = "SELECT * FROM schedules where sch_name like %?1% And sch_pub = 2 ORDER BY sch_start DESC", nativeQuery = true)
    public Page<Schedule> findBySchName(String schName, Pageable pageable);

    // 依照行程開始日期及結束日期，查詢所有期限內的公開行程清單，並依照起始日期降冪排序
    @Query(value = "SELECT * FROM schedules WHERE sch_start >= ?1 AND sch_end <= ?2 And sch_pub = 2 ORDER BY sch_start DESC", nativeQuery = true)
    public List<Schedule> findBetweenDate(Date schStart, Date schEnd);

    // 依行程天數小到大，查詢所有公開行程清單，並依照起始日期降冪排序
    @Query(value = "SELECT sch_id as schId, sch_name as schName, mem_id as memId, sch_start as schStart, sch_end as schEnd, sch_pub as schPub, sch_copy as schCopy, sch_cost as schCost, DATEDIFF(sch_end, sch_start) as days FROM schedules WHERE sch_pub = 2 " +
            "ORDER BY days ASC, sch_start DESC", nativeQuery = true)
    public Page<ScheduleDayDTO> findOrderByDays(Pageable pageable);

    // 依行程天數大到小，查詢所有公開行程清單，並依照起始日期降冪排序
    @Query(value = "SELECT sch_id as schId, sch_name as schName, mem_id as memId, sch_start as schStart, sch_end as schEnd, sch_pub as schPub, sch_copy as schCopy, sch_cost as schCost, DATEDIFF(sch_end, sch_start) as days FROM schedules WHERE sch_pub = 2 " +
            "ORDER BY days DESC, sch_start DESC", nativeQuery = true)
    public Page<ScheduleDayDTO> findOrderByDaysDESC(Pageable pageable);

    // 依行程天數小到大，查詢所有會員行程清單，並依照起始日期降冪排序
    @Query(value = "SELECT sch_id as schId, sch_name as schName, mem_id as memId, sch_start as schStart, sch_end as schEnd, sch_pub as schPub, sch_copy as schCopy, sch_cost as schCost, DATEDIFF(sch_end, sch_start) as days FROM schedules WHERE mem_id = ?1 " +
            "ORDER BY days ASC, sch_start DESC", nativeQuery = true)
    public Page<ScheduleDayDTO> findByMemIdOrderByDays(Integer memId,Pageable pageable);

    // 依行程天數大到小，查詢所有會員行程清單，並依照起始日期降冪排序
    @Query(value = "SELECT sch_id as schId, sch_name as schName, mem_id as memId, sch_start as schStart, sch_end as schEnd, sch_pub as schPub, sch_copy as schCopy, sch_cost as schCost, DATEDIFF(sch_end, sch_start) as days FROM schedules WHERE mem_id = ?1 " +
            "ORDER BY days DESC, sch_start DESC", nativeQuery = true)
    public Page<ScheduleDayDTO> findByMemIdOrderByDaysDESC(Integer memId,Pageable pageable);

    // 查詢使用者自己建立過的所有行程清單
    @Query(value = "SELECT * FROM schedules WHERE mem_id = ?1", nativeQuery = true)
    public Page<Schedule> findByMemIdPaged(Integer memId, Pageable pageable);

    // 查詢使用者自己建立過的所有行程清單(不分頁)
    @Query(value = "SELECT * FROM schedules WHERE mem_id = ?1", nativeQuery = true)
    public List<Schedule> findByMemId(Integer memId);

    // 依照行程名稱查詢使用者自己建立過的相關行程清單
    @Query(value = "SELECT * FROM schedules WHERE mem_id = ?1 AND sch_name like %?2%", nativeQuery = true)
    public Page<Schedule> findByMemIdAndSchName(Integer memId, String schName, Pageable pageable);


    // 查詢行程及其細節，並依照行程細節起始時間排序
    @Query(value = "SELECT * FROM schedules s " +
            "JOIN schedule_de sd ON s.sch_id = sd.sch_id " +
            "WHERE s.sch_id = ?1 ORDER BY schde_starttime", nativeQuery = true)
    public Schedule findByIdOrderByStarttime(Integer schId);


    // 有重複行程資料的問題
    @Query(value = "SELECT s.sch_id schId, sch_name schName, mem_id memId, sch_start schStart, sch_end schEnd, sch_pub schPub, sch_copy schCopy, sch_cost schCost, st.schtag_id schTagId, st.schtag_name schTagName " +
            "FROM schedules s " +
            "JOIN schedule_tag_list stl ON s.sch_id = stl.sch_id " +
            "JOIN schedule_tag st ON stl.schtag_id = st.schtag_id " +
            "WHERE s.sch_id = ?1", nativeQuery = true)
    public List<Object[]> findTagsByOneSchedule(Integer schId);

    //宇航 > 查詢會員公開行程
    @Query(value = "SELECT * FROM schedules WHERE mem_id = ?1 And sch_pub = 2", nativeQuery = true)
    Page<Schedule> findPublicSchByMemIdPaged(Integer memId, Pageable pageable);
}
