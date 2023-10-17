package tw.idv.cha102.g7.activity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.activity.entity.Activity;
import tw.idv.cha102.g7.activity.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    // 依照活動Id，查詢活動
    @GetMapping("/activity/{activId}")
    public Activity getbyId(@PathVariable Integer activId) {
        return activityService.getById(activId);
    }


    // 依照活動名稱、內容關鍵字，查詢所有在架上活動清單
    @GetMapping("/name/{activName}")
    public List<Activity> findByActivNameCon(@PathVariable String activName) {
        return activityService.findByActivNameCon(activName);
    }


    // 依照活動內容關鍵字，查詢所有在架上活動清單
    @GetMapping("/name2/{activCon}")
    public List<Activity> findByActivCon(@PathVariable String activCon) {
        return activityService.findByActivCon(activCon);
    }


    // 管理員 新增活動
    @PostMapping("/add")
    public ResponseEntity<?> insert(@RequestBody Activity activity) {
        activityService.insert(activity);

        Map<String, String> responseJson = new HashMap<>();
        responseJson.put("message", "新增成功");
        return ResponseEntity.ok(responseJson);
//        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 管理員 刪除活動
    @DeleteMapping("/delete/{activId}")
    public ResponseEntity<String> delete(@PathVariable Integer activId) {
        activityService.deleteById(activId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 管理員 修改活動
    @PutMapping("/update/{activId}")
    public ResponseEntity<?> edit(@PathVariable Integer activId,
                                  @RequestBody Activity activity) {
        Activity existingActivity = activityService.getById(activId);

        if (existingActivity != null) {

            // 更新現有資料
            existingActivity.setActivPic(activity.getActivPic());
            existingActivity.setActivName(activity.getActivName());
            existingActivity.setActivCon(activity.gettActivCon());
//            existingActivity.setActivEndtime(activity.getActivEndtime());
            existingActivity.setActivSta(activity.getActivSta());
            // 保存
            activityService.updateById(activId, existingActivity);

            // 建構返回的JSON資料
            Map<String, String> responseJson = new HashMap<>();
            responseJson.put("message", "更新成功");
            return ResponseEntity.ok(responseJson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 管理員 查全部活動
    @GetMapping("/activityall")
    public List<Activity> findAllActivity() {
        List<Activity> activityList = activityService.findAllActivity();
        return activityList;
    }

    // 隨機  加?count=3
    @GetMapping("/activityrandom")
    public List<Activity> getRandomActivity(@RequestParam int count) {
//        return activityService.getRandomActivity(count);
        List<Activity> activityList = activityService.getRandomActivity(count);
        return activityList;
    }


    // -------------------- 待辦 ------------------


    // 查上架中活動
    @GetMapping("/activityavailable")
    public List<Activity> findAvailableActivity() {
        List<Activity> activityList = activityService.findAvailableActivity();
        return activityList;
    }


}
