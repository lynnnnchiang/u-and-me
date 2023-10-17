package tw.idv.cha102.g7.activity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.activity.entity.DTO.ActivitySchRecommendId;
import tw.idv.cha102.g7.activity.service.ActivityService;

import java.util.List;


@CrossOrigin
@RestController
public class ActivitySchRecommenController {

    @Autowired
    ActivityService activityService;

    // 查詢推薦行程
    @GetMapping("/activityRecommend/{activId}")
    public List<Integer> activityRecommend(@PathVariable Integer activId ){
        return activityService.findSchRecommendId(activId);
    }

    // 新增推薦行程
    @RequestMapping("/activityRecommendadd/{activId}/{schId}")
    public void insert(@PathVariable Integer activId ,
                       @PathVariable Integer schId){
        activityService.SchRecommendadd(activId, schId);

    }

    // 修改推薦行程
    @RequestMapping("/activityRecommendedit/{activId}/{schId}")
    public void edit(@PathVariable Integer activId ,
                     @PathVariable Integer schId,
                     @RequestBody ActivitySchRecommendId activitySchRecommendId ){
        activityService.SchRecommendedit(activId, schId, activitySchRecommendId);

    }

//我1
//    @RequestMapping("/activityRecommendedit/{activId}/{newSchId}") // 新的 schId 作為路徑文參數
//    public void edit(@PathVariable Integer activId ,
//                     @PathVariable Integer newSchId, // 新的 schId
//                     @RequestBody ActivitySchRecommendId activitySchRecommendId ){
//        activityService.SchRecommendedit(activId, newSchId, activitySchRecommendId);
//    }


//    @PostMapping("/add")
//    public ResponseEntity<?> insert(@RequestBody Activity activity) {
//        activityService.insert(activity);
//
//        Map<String, String> responseJson = new HashMap<>();
//        responseJson.put("message", "新增成功");
//        return ResponseEntity.ok(responseJson);
////        return new ResponseEntity<>(HttpStatus.OK);
//    }




}
