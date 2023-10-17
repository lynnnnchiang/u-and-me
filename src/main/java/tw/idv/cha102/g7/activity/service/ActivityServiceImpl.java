package tw.idv.cha102.g7.activity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.activity.entity.Activity;
import tw.idv.cha102.g7.activity.entity.DTO.ActivitySchRecommend;
import tw.idv.cha102.g7.activity.entity.DTO.ActivitySchRecommendId;
import tw.idv.cha102.g7.activity.repository.ActivityRepository;
import tw.idv.cha102.g7.activity.repository.ActivitySchRecommendRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository repository;
    @Autowired
    private ActivitySchRecommendRepository RecommendRepository;


    public Activity getById(Integer activId) {
        Activity activity = repository.findById(activId).orElse(null);
        return activity;
    }

    @Override
    public List<Activity> findByActivNameCon(String activName) {
        return repository.findByActivNameCon(activName);
    }

    @Override
    public List<Activity> findByActivCon(String activCon) {
        return repository.findByActivCon(activCon);
    }

    @Override
    public void insert(Activity activity) {
        repository.save(activity);
    }

    @Override
    public void deleteById(Integer activId) {
        repository.deleteById(activId);
    }

    @Override
    public void updateById(Integer activId, Activity activity) {
        repository.save(activity);
    }

    @Override
    public List<Activity> findAllActivity() {
        return repository.findAll();
    }

    @Override  // List<Activity>列表數據 Activity類型
    public List<Activity> getRandomActivity(int count) {
        List<Activity> allActivity = repository.findAvailableActivity();
        Collections.shuffle(allActivity); // 隨機排序數據
        return allActivity.subList(0, Math.min(count, allActivity.size()));  // 獲取前count個數據
    }

    @Override
    public List<Activity> findAvailableActivity() {
        return repository.findAvailableActivity();
    }


    @Override
    public List<Integer> findSchRecommendId(Integer activId) {
        List<ActivitySchRecommend> allId = RecommendRepository.findAll();
        List<Integer> SchRecommendId = new ArrayList<>();
        for (ActivitySchRecommend row : allId) {
            if (row.getActivrecommendId().getActivId().equals(activId)) {
                Integer recommendschId = row.getActivrecommendId().getSchId();
                SchRecommendId.add(recommendschId);
            }
        }
        return SchRecommendId;
    }

    @Override
    public void add(ActivitySchRecommend activitySchRecommend) {

    }

    @Override
    public void SchRecommendadd(Integer activId, Integer schId) {
        ActivitySchRecommendId activitySchRecommendId = new ActivitySchRecommendId();
        activitySchRecommendId.setActivId(activId);
        activitySchRecommendId.setSchId(schId);
        ActivitySchRecommend activitySchRecommend = new ActivitySchRecommend();
        activitySchRecommend.setActivrecommendId(activitySchRecommendId);
        RecommendRepository.save(activitySchRecommend);
    }


    @Override
    public void SchRecommendedit(Integer activId, Integer schId, ActivitySchRecommendId activitySchRecommendId) {
        //用schId和activId這兩個參數的值找原本的ActivitySchRecommendId
        ActivitySchRecommendId activitySchRecommendIdOrigin = new ActivitySchRecommendId(schId, activId);
        // 找出來的結果，需要修改的資料
        ActivitySchRecommend activitySchRecommend = RecommendRepository.findById(activitySchRecommendIdOrigin).orElse(null);

        // 開始修改
        activitySchRecommend.setActivrecommendId(activitySchRecommendId);

        RecommendRepository.save(activitySchRecommend);
        RecommendRepository.deleteById(activitySchRecommendIdOrigin);

    }


//    我我我
//    @Override
//    public void SchRecommendedit(Integer activId, Integer newSchId, ActivitySchRecommendId activitySchRecommendId) {
//        // 用新的 newSchId 和 activId 創建新的 ActivitySchRecommendId
//        ActivitySchRecommendId newActivitySchRecommendId = new ActivitySchRecommendId(newSchId, activId);
//
//        // 找出來需要修改的資料
//        ActivitySchRecommend activitySchRecommend = RecommendRepository.findById(newActivitySchRecommendId).orElse(null);
//
//        // 開始
//        if (activitySchRecommend != null) {
//            activitySchRecommend.setActivrecommendId(activitySchRecommendId);
//            RecommendRepository.deleteById(newActivitySchRecommendId);
//            RecommendRepository.save(activitySchRecommend);
//
//        }
//    }




}


