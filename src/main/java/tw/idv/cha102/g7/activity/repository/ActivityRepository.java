package tw.idv.cha102.g7.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.idv.cha102.g7.activity.entity.Activity;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {


    // 依照活動名稱、內容關鍵字，查詢所有在架上活動清單
    @Query(value = "SELECT * FROM activity WHERE (activ_name LIKE %?1% OR activ_con LIKE %?1%) AND activ_Sta = 1", nativeQuery=true)
    public List<Activity> findByActivNameCon(String activNameCon);

    // 依照活動內容關鍵字，查詢所有在架上活動清單
    @Query(value = "SELECT * FROM activity WHERE activ_con like %?1% AND activ_sta = 1", nativeQuery=true)
    List<Activity> findByActivCon(String activCon);

    @Query(value = "SELECT * FROM activity WHERE activ_sta = 1", nativeQuery = true)
    List<Activity> getRandomActivity(@Param("count") int count);

    // 查詢所有在架上活動
    @Query(value = "SELECT * FROM activity WHERE activ_sta = 1", nativeQuery = true)
    List<Activity> findAvailableActivity();
}
