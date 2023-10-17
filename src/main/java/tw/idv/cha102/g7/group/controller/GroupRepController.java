package tw.idv.cha102.g7.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.group.entity.GroupRep;
import tw.idv.cha102.g7.group.service.GroupRepService;

import java.util.List;

@RestController
@CrossOrigin
public class GroupRepController {

    @Autowired
    private GroupRepService groupRepService;

    /**
     * 一般使用者/揪團團主
     * 檢舉揪團
     * @param groupRep 欲檢舉的揪團內容
     */
    @PostMapping("/groupRep")
    public void insert(@RequestBody GroupRep groupRep){
        groupRepService.insert(groupRep);
    }

    /**
     * 管理員
     * 修改檢舉狀態
     * @param groupRepId 檢舉揪團ID
     * @param groupRep 修改完的檢舉資訊
     */
    @PutMapping("/groupRep/{groupRepId}")
    public void update(@PathVariable Integer groupRepId,
                       @RequestBody GroupRep groupRep){
        groupRepService.update(groupRepId, groupRep);
    }

    /**
     * 管理員
     * 刪除檢舉
     * @param groupRepId 檢舉ID
     */
    @DeleteMapping("/groupRep/{groupRepId}")
    public void delete(@PathVariable Integer groupRepId){
        groupRepService.delete(groupRepId);
    }

    /**
     * 管理員
     * 查詢檢舉狀態
     * @param GroupRepSta 檢舉狀態
     * 0: 未處理,
     * 1: 檢舉通過,
     * 2: 檢舉未通過
     * @return 查詢檢舉結果
     */
    @GetMapping("/groupRepsSta/{GroupRepSta}")
    public List<GroupRep> findByGroupRepSta(@PathVariable Integer GroupRepSta) {
        return groupRepService.findByGroupRepSta(GroupRepSta);
    }

    /**
     * 管理員
     * 以檢舉的會員ID查詢檢舉
     * @param MemId 送出檢舉的會員ID
     * @param GroupRepSta 檢舉狀態
     * 0: 未處理,
     * 1: 檢舉通過,
     * 2: 檢舉未通過
     * @return 查詢檢舉的結果
     */
    @GetMapping("/groupRepsMemSta/{MemId}/{GroupRepSta}")
    public List<GroupRep> findByMemIdAndGroupRepSta(@PathVariable Integer MemId,
                                                    @PathVariable Integer GroupRepSta) {
        return groupRepService.findByMemIdAndGroupRepSta(MemId, GroupRepSta);
    }

    /**
     * 尋找用處中...
     * @param groupRepId
     * @return
     */
    @GetMapping("/groupRep/{groupRepId}")
    public GroupRep getGroupReqByGroupRepId(@PathVariable Integer groupRepId){
        return groupRepService.getGroupRepByGroupRepId(groupRepId);
    }

    /**
     * 管理員
     * 查詢所有檢舉
     * @return 所有檢舉
     */
    @GetMapping("/groupRep/all")
    public List<GroupRep> getAll(){
        return groupRepService.getAll();
    }
}
