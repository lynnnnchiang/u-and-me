package tw.idv.cha102.g7.schedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.schedule.entity.ScheduleReport;

import java.util.List;

@Repository
public interface ScheduleReportRepository extends JpaRepository<ScheduleReport, Integer> {

    @Query(value = "SELECT * FROM schedule_rep WHERE schrep_sta = ?1",nativeQuery = true)
    public List<ScheduleReport> findByStatus(Short schRepSta);
}
