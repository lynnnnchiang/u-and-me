package tw.idv.cha102.g7.activity.service;

import tw.idv.cha102.g7.activity.entity.Activity;
import tw.idv.cha102.g7.activity.entity.DTO.ActivitySchRecommend;
import tw.idv.cha102.g7.activity.entity.DTO.ActivitySchRecommendId;

import java.util.List;


public interface ActivityService {

    // 依活動Id,查詢單一活動內容
    public Activity getById(Integer activId);

    // 依活動名稱內容關鍵字模糊比對查詢行程
    List<Activity> findByActivNameCon(String activName);

    // 依活動內容關鍵字模糊比對查詢行程
    List<Activity> findByActivCon(String activCon);

    // 新增活動
    void insert(Activity activity);

    // 刪除活動byId  // (需要一個Entity作為引數，而不是一個整數id)
    void deleteById(Integer activId);


    void updateById(Integer activId, Activity activity);

    public List<Activity> findAllActivity();

    public List<Activity> getRandomActivity(int count);

    public List<Activity> findAvailableActivity();

    public List<Integer> findSchRecommendId(Integer activId);

    void add(ActivitySchRecommend activitySchRecommend);

    public void SchRecommendadd(Integer activId, Integer schId);

    void SchRecommendedit(Integer activId, Integer schId, ActivitySchRecommendId activitySchRecommendId);


}