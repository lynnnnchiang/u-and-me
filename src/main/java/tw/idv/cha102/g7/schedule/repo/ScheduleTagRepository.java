package tw.idv.cha102.g7.schedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;

import java.util.List;

@Repository
public interface ScheduleTagRepository extends JpaRepository<ScheduleTag, Integer> {

    public List<ScheduleTag> findBySchTagNameContaining(String schTagName);

//    @Query(value = "SELECT st.*, s.* " +
//            "FROM schedule_tag st " +
//            "JOIN schedule_tag_list stl ON st.schtag_id = stl.schtag_id " +
//            "JOIN schedules s ON stl.sch_id = s.sch_id " +
//            "WHERE st.schtag_id =  ?1 AND sch_pub = 2 ORDER BY s.sch_start", nativeQuery = true)
//    public List<Object[]> findSchedulesBySchTagId(Integer schTagId);


    @Query(value = "SELECT st.*, s.* " +
            "FROM schedules s " +
            "JOIN schedule_tag_list stl ON s.sch_id = stl.sch_id " +
            "JOIN schedule_tag st ON stl.schtag_id = st.schtag_id " +
            "WHERE st.schtag_name like %?1% AND sch_pub = 2 ORDER BY s.sch_start", nativeQuery = true)
    public List<Object[]> findSchedulesBySchTagName(String schTagName);


}
