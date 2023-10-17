package tw.idv.cha102.g7.group.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.group.dto.*;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.group.entity.MemberDetail;
import tw.idv.cha102.g7.group.repo.GroupRepository;
import tw.idv.cha102.g7.group.repo.MemberDetailRepository;
import tw.idv.cha102.g7.group.repo.RegFormRepository;
import tw.idv.cha102.g7.group.service.GroupService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

@Component
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RegFormRepository regFormRepository;

    @Autowired
    private MemberDetailRepository memberDetailRepository;

    public void insert(Group group, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        group.setMemId(memId);
        groupRepository.save(group);
    }

    public void update(Integer groupId, Group group) {
        var existGroup = groupRepository.findById(groupId);
        if (existGroup.isPresent()) {
            existGroup.get().setGroupId(groupId);
            groupRepository.save(existGroup.get());
        }
    }

    @Override
    public void updateMyGroupByGroupId(Integer groupId, Group group) {
        var existGroup = groupRepository.findById(groupId);
        if (existGroup.isPresent()) {
            //以實際前端傳送情況更新
            existGroup.get().setMinMember(group.getMinMember());
            existGroup.get().setMaxMember(group.getMaxMember());
            existGroup.get().setTheme(group.getTheme());
            existGroup.get().setDepDate(group.getDepDate());
            existGroup.get().setDeadline(group.getDeadline());
            existGroup.get().setGroupDesc(group.getGroupDesc());
            existGroup.get().setNotice(group.getNotice());
            existGroup.get().setCover(group.getCover());
            groupRepository.save(existGroup.get());
        }
    }

    public boolean delete(Integer groupId) {
        boolean control = true;
        List<RegFormMemberDetailDto> refundList = regFormRepository.findRefundSTaByGroupId(groupId);
        System.out.println(refundList);
        //如果還有尚未退費的，就不能刪除
        if (refundList != null) {
            for (RegFormMemberDetailDto refundLists : refundList) {
                if (refundLists.getRefund_Sta() != 3) {
                    control = false;
                }
            }
        }

        if (control) {
            groupRepository.deleteById(groupId);
        }
        return control;
    }

    public Group getGroupByGroupId(Integer groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    public List<Group> getAllPaged(int page, int size) {
        Page<Group> pageResult = groupRepository.findAll(
                PageRequest.of(page, //查詢的頁數 從0開始
                        size,//查詢的每頁筆數
                        Sort.by("startDate").ascending())); //依造start_Date欄位升冪排序
//        可能會運用到的方法
//        pageResult.getNumberOfElements(); //本頁筆數
//        pageResult.getSize(); //每頁筆數
//        pageResult.getTotalElements(); //全部筆數
//        pageResult.getTotalPages(); //全部頁數

        List<Group> groupList = pageResult.getContent();
        return groupList;
    }

    @Override
    public List<Group> findByMemIdOrderByGroupStaDesc(Integer memId) {
        return groupRepository.findByMemIdOrderByGroupStaDesc(memId);
    }
//    public List<Group> getGroupsByMemId(Integer memId){
//        return groupRepository.findGroupsByMemId(memId);
//    }

    @Override
    public List<Group> findByThemeContaining(String keyword) {
        return groupRepository.findByThemeContaining(keyword);
    }

    @Override
    public List<Group> findGroupByGroupSta(Integer groupSta) {
        return groupRepository.findGroupByGroupSta(groupSta);
    }

    @Override
    public List<Group> findGroupByPaymentSta(Integer paymentSta) {
        return groupRepository.findGroupByPaymentSta(paymentSta);
    }

    @Override
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemId(HttpServletRequest request, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Sort sort = Sort.by(Sort.Direction.ASC, "Group_Sta");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return groupRepository.findGroupRegFormDtoByMemId(memId, pageable).get();
    }

    @Override
    public List<GroupGroupPicDto> findGroupRegFormDtoByGroupIdOrderByGroupPicId(Integer groupId) {
        return groupRepository.findGroupRegFormDtoByGroupIdOrderByGroupPicId(groupId);
    }

    @Override
    public Stream<GroupListDto> findGroupListByGroupSta(Integer page) {

        Sort sort = Sort.by(Sort.Direction.ASC, "group_Id");  //定義Sort ASC為升冪排序，通過揪團狀態排序 start_Date 是MySQL的欄位名

        //測試想法(service層寫一個set方法，然後在取得body的controller層調用set方法，再將需要運用到的service層調用set方法)
//        if(sortType != null){
//            switch (sortType.getType()){
//                case 1:
//                    sort = Sort.by(Sort.Direction.ASC, "deadline");
//                    break;
//                case 2:
//                    sort = Sort.by(Sort.Direction.DESC, "deadline");
//                    break;
//                case 3:
//                    break;
//            }
//        }

        Pageable pageable = PageRequest.of(page, 6, sort);   //定義Pageable(page 當前頁數, size 傳入資料筆數, sort 排序方法)
        BigInteger zero = new BigInteger("0");  //比較BigInteger所以要用BigInteger互相比較
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        //以下判斷取出的揪團狀態
        List<Group> groups = groupRepository.findGroupByGroupSta(0); //找出揪團狀態為0的列表
        for (Group group : groups) {

            //參團人數到最大人數
            if (group.getMembers() == group.getMaxMember()) {
                group.setGroupSta(5); //設置額滿狀態
                groupRepository.save(group);
            }

            //揪團時間截止做的判斷
            Date date = group.getDeadline(); //轉換java.sql.TimeStamp 至 java.util.Date
            if (date.compareTo(new Date()) < 0) {
                if (group.getMembers() > group.getMinMember()) {
                    group.setGroupSta(1); //設置成團狀態
                    groupRepository.save(group);
                } else {
                    group.setGroupSta(2); //設置揪團取消狀態
                    groupRepository.save(group);

                    //將參團人員設置申請退款
                    List<GroupRegFormMemberDetailDto> detailList = memberDetailRepository.findGroupRegFormMemberDetailDtoByGroupIdOrderByFormId(group.getGroupId());
                    if (detailList != null) {
                        for (GroupRegFormMemberDetailDto detail : detailList) {
                            MemberDetail updDetail = memberDetailRepository.findById(detail.getDetailId()).get();
                            if (updDetail != null) {
                                if (updDetail.getRefundSta() == 0 || updDetail.getRefundSta() == 4) {
                                    updDetail.setRefundSta(2);
                                    updDetail.setRefund(group.getAmount());
                                    updDetail.setRefundDate(timestamp);
                                    memberDetailRepository.save(updDetail);
                                }
                            }
                        }
                    }

                }
            }
        }
        //問題 Stream.filter()後的列表是亂的(結果:因為pageable size一次傳入固定比數， 呼叫分頁時才會再給下一頁的值，所以才會挖空)
        return groupRepository.findGroupListByGroupSta(pageable).get(); //返回值為Stream<T>
    }

    @Override
    public Stream<MyGroupListDto> findMyGroupListDtoByMemId(HttpServletRequest request, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Sort sort = Sort.by(Sort.Direction.ASC, "Group_Id");
        Pageable pageable = PageRequest.of(page, 10, sort);

        List<Group> groupList = groupRepository.findByMemIdOrderByGroupStaDesc(memId);
        for (Group group : groupList) {
            //揪團時間截止做的判斷
            Date date = group.getDeadline(); //轉換java.sql.TimeStamp 至 java.util.Date
            if (date.compareTo(new Date()) < 0) {
                if (group.getMembers() > group.getMinMember()) {
                    group.setGroupSta(1); //設置成團狀態
                    groupRepository.save(group);
                } else {
                    group.setGroupSta(2); //設置揪團取消狀態
                    groupRepository.save(group);
                }
            }
        }
        return groupRepository.findMyGroupListDtoByMemId(memId, pageable).get();
    }

    @Override
    public UpdateMyGroupDto findUpdateMyGroupByGroupId(Integer groupId) {
        return groupRepository.findUpdateMyGroupByGroupId(groupId);
    }

    @Override
    public Stream<GroupListDto> findGroupByGroupStaOrderByDeadline(Integer page) {
        Pageable pageable = PageRequest.of(page, 6);
        return groupRepository.findGroupByGroupStaOrderByDeadline(pageable).get();
    }

    @Override
    public Stream<GroupListDto> findGroupByGroupStaOrderByDeadlineDesc(Integer page) {
        Pageable pageable = PageRequest.of(page, 6);
        return groupRepository.findGroupByGroupStaOrderByDeadlineDesc(pageable).get();
    }

    @Override
    public Stream<GroupListDto> findGroupByGroupStaOrderByAmount(Integer page) {
        Pageable pageable = PageRequest.of(page, 6);
        return groupRepository.findGroupByGroupStaOrderByAmount(pageable).get();
    }

    @Override
    public Stream<GroupListDto> findGroupByGroupStaOrderByAmountDesc(Integer page) {
        Pageable pageable = PageRequest.of(page, 6);
        return groupRepository.findGroupByGroupStaOrderByAmountDesc(pageable).get();
    }

    @Override
    public Stream<GroupListDto> findGroupByGroupStaThemeLike(String str, Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "start_Date");
        Pageable pageable = PageRequest.of(page, 6, sort);
        return groupRepository.findGroupByGroupStaThemeLike(str, pageable).get();
    }

    @Override
    public GroupMemo findGroupMemo(Integer groupId) {
        Group group = groupRepository.findById(groupId).get();

        //設置人數達標
        if (group.getMembers() >= group.getMinMember() && group.getMembers() < group.getMaxMember()) {
            group.setGroupSta(1);
            groupRepository.save(group);
        }

        //如果人數額滿則自動設置額滿狀態
        if (group.getMembers() == group.getMaxMember()) {
            group.setGroupSta(5);
            groupRepository.save(group);
        }

        return groupRepository.findGroupMemo(groupId);
    }

    @Override
    public GroupMemberDto finGroupMember(Integer groupId) {
        return groupRepository.finGroupMember(groupId);
    }

    @Override
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta0(HttpServletRequest request, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Sort sort = Sort.by(Sort.Direction.ASC, "Dep_Date");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return groupRepository.findGroupRegFormDtoByMemIdAndGroupSta0(memId, pageable).get();
    }

    @Override
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta1(HttpServletRequest request, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Sort sort = Sort.by(Sort.Direction.ASC, "Dep_Date");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return groupRepository.findGroupRegFormDtoByMemIdAndGroupSta1(memId, pageable).get();
    }

    @Override
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta4(HttpServletRequest request, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Sort sort = Sort.by(Sort.Direction.ASC, "Dep_Date");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return groupRepository.findGroupRegFormDtoByMemIdAndGroupSta4(memId, pageable).get();
    }

    @Override
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta5(HttpServletRequest request, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Sort sort = Sort.by(Sort.Direction.ASC, "Dep_Date");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return groupRepository.findGroupRegFormDtoByMemIdAndGroupSta5(memId, pageable).get();
    }

    @Override
    public Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndThemeLike(HttpServletRequest request, String str, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Sort sort = Sort.by(Sort.Direction.ASC, "Group_Sta");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return groupRepository.findGroupRegFormDtoByMemIdAndThemeLike(memId, str, pageable).get();
    }

    @Override
    public void updateGroupSta(Integer groupId, Group group) {
        var updGroup = groupRepository.findById(groupId);
        List<MemberDetail> memberDetailList = memberDetailRepository.findByGroupId(groupId);
        if (updGroup.isPresent()) {
            if(updGroup.get().getGroupSta() != 4 && memberDetailList.size() < 1 ) {
                updGroup.get().setGroupSta(group.getGroupSta());
                groupRepository.save(updGroup.get());
            }
        }
    }

    @Override
    public void updateGroupStaPaymentSta(Integer groupId, Group group) {
        var updGroup = groupRepository.findById(groupId);
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);

        if (updGroup.isPresent()) {
            if (group.getGroupSta() != null) {
                updGroup.get().setGroupSta(group.getGroupSta());
            }
            if (group.getPaymentSta() != null) {
                updGroup.get().setPaymentSta(group.getPaymentSta());
            }
            groupRepository.save(updGroup.get());
        }

        //如果管理員設置下架揪團時
        if (group.getGroupSta() == 4) {
            updMemberDetailRefund(groupId);
        }
    }

    //為了多表查members只在service用
    @Override
    public Group findMemberDeadlineAmountByDetailId(Integer detailId) {
        return groupRepository.findMemberDeadlineAmountByDetailId(detailId);
    }

    @Override
    public List<Group> findAll() {
        List<Group> groupList = groupRepository.findAll();
        TimeUnit time = TimeUnit.DAYS;
        for (Group group : groupList) {
            Date depDate = group.getDepDate();
            long diff = new Date().getTime() - depDate.getTime();
            long difference = time.convert(diff, TimeUnit.MILLISECONDS);
            if(difference >= 14){
                group.setPaymentSta(1);
            }

            List<MemberDetail> memberDetailList = memberDetailRepository.findByGroupId(group.getGroupId());
            for (MemberDetail detail : memberDetailList) {
                if (detail != null) {
                    if (detail.getRefundSta() == 2) { //如果還有申請退款的團員
                        group.setPaymentSta(2); //設置不能請款
                    }
                }
            }
        }

        return groupList;
    }

    //檢舉行程時更新揪團狀態方法
    public void updGroupStaBySchRep(Integer schId){
        List<Group> groupList = groupRepository.findBySchId(schId);
        if(groupList != null){
            System.out.println("group !=null");
            for(Group group : groupList){
                //如果正在揪團
                if(group.getGroupSta() == 0 || group.getGroupSta() == 1 || group.getGroupSta() == 5) {
                    group.setGroupSta(4);
                    group.setPaymentSta(2);
                    groupRepository.save(group);
                    updMemberDetailRefund(group.getGroupId());
                }
            }
        }
    }

    //更新團員退費方法
    public void updMemberDetailRefund(Integer groupId){
        Long datetime = System.currentTimeMillis();
        Group group = groupRepository.findById(groupId).orElse(null);
        Timestamp timestamp = new Timestamp(datetime);
        List<MemberDetail> memberDetailList = memberDetailRepository.findByGroupId(groupId);
        if (memberDetailList != null) {
            //設置退費狀態
            for (MemberDetail detail : memberDetailList) {
                if (detail.getRefundSta() == 0) {
                    detail.setRefundSta(2);
                    detail.setRefundDate(timestamp);
                    System.out.println(group.getAmount());
                    detail.setRefund(group.getAmount());
                    detail.setReason("管理員處理揪團下架");
                    System.out.println(detail.getReason());
                    memberDetailRepository.save(detail);
                }
            }
        }
    }
}
