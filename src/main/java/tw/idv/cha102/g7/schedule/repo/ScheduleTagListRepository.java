package tw.idv.cha102.g7.schedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tw.idv.cha102.g7.schedule.entity.ScheduleTagList;
import tw.idv.cha102.g7.schedule.entity.ScheduleTagListId;

import javax.transaction.Transactional;
import java.util.List;

public interface ScheduleTagListRepository extends JpaRepository<ScheduleTagList, ScheduleTagListId> {

    public List<ScheduleTagList> findByScheduleTagListId(ScheduleTagListId listId);

    public void deleteByScheduleTagListId(ScheduleTagListId listId);

    // 欲執行刪除，要呼叫executeUpdate()方法，需有下列annotation
    // 回傳int為受影響的資料筆數
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM schedule_tag_list st WHERE st.sch_id = ?1",nativeQuery = true)
    public int deleteBySchId(Integer schId);
}
