package tw.idv.cha102.g7.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.group.dto.GroupRegFormMemberDetailDto;
import tw.idv.cha102.g7.group.entity.MemberDetail;
import tw.idv.cha102.g7.group.service.MemberDetailService;

import java.util.List;

@RestController
@CrossOrigin
public class MemberDetailController {
    @Autowired
    private MemberDetailService memberDetailService;

    /**
     * 一般使用者
     * 於報名揪團時新增報名細節
     * @param memberDetail 欲新增的報名細節
     */
    @PostMapping("/member/memberDetail")//新增報名細節
    public void insert(@RequestBody MemberDetail memberDetail){
        memberDetailService.insert(memberDetail);
    }

    /**
     *  管理員
     *　管理員：修改退費狀態、修改報名細節資料
     * @param detailId　報名細節ID
     * @param memberDetail 修改後的報名資料
     */
    @PutMapping("/member/memberDetail/{detailId}")
    public void update(@PathVariable Integer detailId,
                       @RequestBody MemberDetail memberDetail){
        memberDetailService.update(detailId, memberDetail);
    }

    /**
     * 管理員
     * 刪除報名細節
     * @param detailId 報名細節ID
     */
    @DeleteMapping("/memberDetail/{detailId}")//刪除報名細節
    public void delete(@PathVariable Integer detailId){
        memberDetailService.delete(detailId);
    }

    /**
     * 管理員
     * 尋找報名細節資料
     * @param detailId 報名細節ID
     * @return 單筆報名細節
     */
    @GetMapping("/memberDetail/{detailId}")//查詢報名細節
    public MemberDetail getMemberDetailByDetailId(@PathVariable Integer detailId){
        return memberDetailService.getMemberDetailByDetailId(detailId);
    }

    /**
     * 管理員/揪團團主
     * 以揪團編號查詢全部報名細節資料
     * @param groupId 揪團編號
     * @return 查詢結果
     */
    @GetMapping("/memberDetailsGroup/{groupId}")//以groupId查詢全部報名細節
    List<GroupRegFormMemberDetailDto> findGroupRegFormMemberDetailDtoByGroupIdOrderByFormId(@PathVariable Integer groupId){
        return memberDetailService.findGroupRegFormMemberDetailDtoByGroupIdOrderByFormId(groupId);
    }

    /**
     * 管理員
     * 以報名表編號查詢報名細節
     * @param formId 報名表編號
     * @return 查詢結果
     */
    @GetMapping("/member/memberDetailsForms/{formId}")//以formId查詢全部報名細節
    List<MemberDetail> findByFormId(@PathVariable Integer formId){
        return memberDetailService.findByFormId(formId);
    }

    /**
     * 尋找用處中...
     * @return
     */
    @GetMapping("/memberDetailsAll")//查詢全部報名細節
    public List<MemberDetail> getAll(){
        return memberDetailService.getAll();
    }

    //以refundSta查List報名細節;
    /**
     * 管理員
     * 以退費狀態查詢報名細節
     * @param refundSta 退費狀態
     * 0: 未申請退款,
     * 1: 退款時間到期,
     * 2: 已申請退款,
     * 3: 退款完成,
     * @return 查詢結果
     */
    @GetMapping("/memberDetailsRefSta/{refundSta}")
    public List<MemberDetail> findByRefundSta(@PathVariable Integer refundSta) {
        return memberDetailService.findByRefundSta(refundSta);
    }

    /**
     * 管理員/一般使用者
     * 以會員編號查報名細節
     * @param memId 報名會員編號
     * @return 查詢結果
     */
    @GetMapping("/memberDetailsMemId/{memId}")
    public List<MemberDetail> findByMemId(@PathVariable Integer memId) {
        return memberDetailService.findByMemId(memId);
    }

    @PutMapping("/updRefundSta/{detailId}")
    public void updateRefundSta(@PathVariable Integer detailId,
                                @RequestBody MemberDetail memberDetail){
        memberDetailService.updateRefundSta(detailId, memberDetail);
    }
}
