package tw.idv.cha102.g7.schedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.schedule.entity.ScheduleDetail;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Integer> {

    // 查詢單一行程的所有行程細節
    @Query(value = "SELECT * FROM schedule_de WHERE sch_id = ?1 ORDER BY schde_starttime", nativeQuery = true)
    public List<ScheduleDetail> findBySchId(Integer schId);

    // 依照行程id刪除其所有行程細節
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM schedule_de sd WHERE sd.sch_id = ?1",nativeQuery = true)
    public int deleteBySchId(Integer schId);
}
