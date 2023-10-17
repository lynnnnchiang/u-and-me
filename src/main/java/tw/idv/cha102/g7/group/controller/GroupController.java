package tw.idv.cha102.g7.group.controller;

import org.hibernate.annotations.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.group.dto.*;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.group.service.GroupService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Stream;

@RestController
@CrossOrigin
public class GroupController {

    @Autowired
    private GroupService groupService;

//    @Autowired SortType sortType;

    /**
     * 揪團團主
     * 新增揪團
     * @param group 欲新增的揪團資訊
     */
    @PostMapping("/member/grouper/group")
    public void insert(@RequestBody Group group, HttpServletRequest request){
        groupService.insert(group, request);
    }

    /**
     * 管理員
     * 修改揪團狀態/支付尾款狀態
     *
     * 揪團團主
     * 修改成團最大最小人數、標題、價格、行程出發時間、揪團截止時間、揪團描述、行前通知
     *
     * @param groupId 揪團ID
     * @param group 修改後的揪團資訊
     */
    @PutMapping("/group/{groupId}")
    public void update(@PathVariable Integer groupId,
                       @RequestBody Group group){

        groupService.update(groupId, group);
    }

    /**
     * 管理員
     * 刪除揪團
     * @param groupId 揪團ID
     */
    @DeleteMapping("/member/grouper/group/{groupId}")
    public ResponseEntity<?> delete(@PathVariable Integer groupId){
        try {
            boolean control = groupService.delete(groupId);
            if(control == true){
                return ResponseEntity.ok("success");
            }else{
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND).body("fail");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("fail");
        }
    }

    /**
     * 管理員
     * 查詢指定的揪團編號
     * @param groupId
     * @return 返回查詢單一揪團結果
     */
    @GetMapping("/group/{groupId}")
    public Group getGroupByGroupId(@PathVariable Integer groupId){
        return groupService.getGroupByGroupId(groupId);
    }

    /**
     * 遊客/揪團團主/一般使用者
     * 查詢所有揪團並依分頁顯示
     * @param page 當前分頁 (從0開始)
     * @return 返回所有揪團列表
     */
    @GetMapping("/groups/all/{page}")
    public List<Group> getAllPaged(@PathVariable Integer page){
        return groupService.getAllPaged(page,5);
    }

    /**
     * 揪團團主
     * 查詢自己的揪團列表
     * @param memId 揪團團主ID
     * @return 揪團團主的揪團列表 以groupSta升冪排序
     */
    @GetMapping("/groups/findByMemId/{memId}")
    public List<Group> findByMemIdOrderByGroupStaDesc(@PathVariable Integer memId){
        return groupService.findByMemIdOrderByGroupStaDesc(memId);
    }

    /**
     * 遊客/揪團團主/一般使用者
     * 以揪團標題查詢揪團(暫時沒用)
     * @param keyword 欲查詢的名稱
     * @return 返回查詢結果
     */
    @GetMapping("/groups/searchTheme/{keyword}")
    public List<Group> findByThemeContaining(@PathVariable String keyword){
        return groupService.findByThemeContaining(keyword);
    }

    /**
     * 管理員
     * 查詢揪團狀態
     * @param groupSta 欲查詢的揪團狀態
     * 0: 未成團,
     * 1: 揪團成功,
     * 2: 揪團取消,
     * 3: 揪團延期,
     * 4: 揪團被下架
     * 5: 已額滿
     * @return 查詢結果
     */
    @GetMapping("/groups/searchSta/{groupSta}")
    public List<Group> findGroupByGroupSta(@PathVariable Integer groupSta){
        return groupService.findGroupByGroupSta(groupSta);
    }

    /**
     * 管理員
     * 查詢揪團尾款狀態
     * @param paymentSta 揪團尾款支付狀態
     * 0: 未撥款,
     * 1: 可申請撥款,
     * 2: 待撥款,
     * 3: 撥款完成
     * @return 查詢結果
     */
    @GetMapping("/groups/searchPaySta/{paymentSta}")
    public List<Group> findGroupByPaymentSta(@PathVariable Integer paymentSta){
        return groupService.findGroupByPaymentSta(paymentSta);
    }

    /**
     * 會員
     * 報名參加的揪團頁面
     * @param page 頁面
     * @return 查詢結果
     */
    @GetMapping("/member/groups/joined/{page}")
    Stream<GroupRegFormDto> findGroupRegFormDtoByMemId(HttpServletRequest request,
                                                       @PathVariable Integer page){
        return groupService.findGroupRegFormDtoByMemId(request, page);
    }

    /**
     * 遊客/揪團團主/一般使用者
     * 揪團詳情頁面
     * @param groupId 揪團ID
     * @return 單筆揪團詳情資訊
     */
    @GetMapping("/groupDetail/{groupId}")
    List<GroupGroupPicDto> findGroupRegFormDtoByGroupIdOrderByGroupPicId(@PathVariable Integer groupId){
        return groupService.findGroupRegFormDtoByGroupIdOrderByGroupPicId(groupId);
    }

    /**
     * 所有人
     * 揪團列表
     * @param page 分頁
     * 0: 未成團,
     * 1: 揪團成功,
     * 2: 揪團取消,
     * 3: 揪團延期,
     * 4: 揪團被下架
     * @param page 分頁頁數
     * @return 揪團列表
     */
    @GetMapping("/groupList/{page}")
    public Stream<GroupListDto> findGroupListByGroupSta(@PathVariable Integer page, SortType sortType){
//        sortType = this.sortType;
          return groupService.findGroupListByGroupSta(page);
    }

    /**
     * 揪團團主
     * 顯示揪團團主舉辦的揪團
     * @param page 目標顯示的頁數
     * @return 查詢結果
     */
    @GetMapping("/member/grouper/myGroupList/{page}")
    public Stream<MyGroupListDto> findMyGroupListDtoByGroupId(HttpServletRequest request,
                                                              @PathVariable Integer page){
        return groupService.findMyGroupListDtoByMemId(request, page);
    }

    /**
     * 揪團團主
     * 更新揪團時在更新頁面顯示揪團詳情
     * @param groupId 揪團ID
     * @return 查詢結果
     */
    @GetMapping("/myGroup/update/{groupId}")
    public UpdateMyGroupDto findUpdateMyGroupByGroupId(@PathVariable Integer groupId){
        return groupService.findUpdateMyGroupByGroupId(groupId);
    }

    /**
     * 揪團團主
     * 更新揪團
     * @param groupId 揪團ID
     * @param group 前端傳送來須修改的值
     */
    @PutMapping("/myGroup/update/{groupId}")
    public ResponseEntity<?> updateMyGroupByGroupId(@PathVariable Integer groupId,
                                                    @RequestBody Group group){
        try {
            groupService.updateMyGroupByGroupId(groupId, group);
            return ResponseEntity.ok("更新成功！");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body("更新失敗！");
        }
    }

    //揪團列表金額排序
    @GetMapping("/groupList/byDeadline/{page}")
    public Stream<GroupListDto> findGroupByGroupStaOrderByDeadline(@PathVariable Integer page){
           return groupService.findGroupByGroupStaOrderByDeadline(page);
    }

    //揪團列表金額排序
    @GetMapping("/groupList/byDeadlineDesc/{page}")
    public Stream<GroupListDto> findGroupByGroupStaOrderByDeadlineDesc(@PathVariable Integer page){
            return groupService.findGroupByGroupStaOrderByDeadlineDesc(page);
    }

    //揪團列表金額排序
    @GetMapping("/groupList/byAmount/{page}")
    public Stream<GroupListDto> findGroupByGroupStaOrderByAmount(@PathVariable Integer page){
        return groupService.findGroupByGroupStaOrderByAmount(page);
    }

    //揪團列表金額排序
    @GetMapping("/groupList/byAmountDesc/{page}")
    public Stream<GroupListDto> findGroupByGroupStaOrderByAmountDesc(@PathVariable Integer page){
        return groupService.findGroupByGroupStaOrderByAmountDesc(page);
    }

    //揪團名查詢
    @GetMapping("/groupList/name{str}/{page}")
    public Stream<GroupListDto> findGroupByGroupStaThemeLike(@PathVariable String str,
                                                           @PathVariable Integer page){
        return groupService.findGroupByGroupStaThemeLike(str, page);
    }

    /**
     * 所有人
     * 查看揪團詳情
     * @param groupId 揪團編號
     * @return 查詢結果
     */
    @GetMapping("/groupMemo/{groupId}")
    public GroupMemo findGroupMemo(@PathVariable Integer groupId){
        return groupService.findGroupMemo(groupId);
    }

    //查找最大人數用
    @GetMapping("/group/findMember/{groupId}")
    public GroupMemberDto finGroupMember(@PathVariable Integer groupId) {
        return groupService.finGroupMember(groupId);
    }

    @GetMapping("/member/grouper/match")
    public void matchGrouper(){
    }

    //會員以揪團狀態揪團紀錄
    @GetMapping("/member/groups/joined/groupSta0/{page}")
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta0(HttpServletRequest request,
                                                                          @PathVariable Integer page){
        return groupService.findGroupRegFormDtoByMemIdAndGroupSta0(request, page);
    }

    //會員以揪團狀態揪團紀錄
    @GetMapping("/member/groups/joined/groupSta1/{page}")
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta1(HttpServletRequest request,
                                                                          @PathVariable Integer page){
        return groupService.findGroupRegFormDtoByMemIdAndGroupSta1(request, page);
    }

    @GetMapping("/member/groups/joined/groupSta4/{page}")
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta4(HttpServletRequest request,
                                                                          @PathVariable Integer page){
        return groupService.findGroupRegFormDtoByMemIdAndGroupSta4(request, page);
    }

    @GetMapping("/member/groups/joined/groupSta5/{page}")
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta5(HttpServletRequest request,
                                                                          @PathVariable Integer page){
        return groupService.findGroupRegFormDtoByMemIdAndGroupSta5(request, page);
    }

    //會員以名稱搜尋揪團紀錄
    @GetMapping("/member/groups/joined/searchTheme={str}/{page}")
    public  Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndThemeLike(HttpServletRequest request,
                                                                           @PathVariable String str,
                                                                           @PathVariable Integer page){
        return groupService.findGroupRegFormDtoByMemIdAndThemeLike(request, str, page);
    }

    //揪團主更改上下架
    @PutMapping("/member/grouper/group/updateGroupSta/{groupId}")
    public ResponseEntity<?> updateGroupSta(@PathVariable Integer groupId, 
                               @RequestBody Group group){
        try {
            groupService.updateGroupSta(groupId, group);
            return ResponseEntity.ok("更新成功");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("更新失敗");
        }
    }

    @PutMapping("/group/updateGStaPSta/{groupId}")
    public ResponseEntity<?> updateGroupStaPaymentSta(@PathVariable Integer groupId,
                                            @RequestBody Group group){
        try {
            groupService.updateGroupStaPaymentSta(groupId, group);
            return ResponseEntity.ok("更新成功");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("更新失敗");
        }
    }

    @GetMapping("/groups")
    public List<Group> findAll() {
        return groupService.findAll();
    }

    //測試包裝排序(有空優化)
//    @PostMapping("/test")
//    public SortType setSortType(@RequestBody SortType sortType){
//        this.sortType = sortType;
//        return sortType;
//    }

}
