package tw.idv.cha102.g7.group.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tw.idv.cha102.g7.group.dto.*;
import tw.idv.cha102.g7.group.entity.Group;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Stream;

public interface GroupService {
    void insert(Group group, HttpServletRequest request);

    void update(Integer groupId, Group group);

    void updateMyGroupByGroupId(Integer groupId, Group group);

    boolean delete(Integer groupId);

    Group getGroupByGroupId(Integer groupId);

    List<Group> getAllPaged(int page, int size);

    List<Group> findByMemIdOrderByGroupStaDesc(Integer memId);
//    List<Group> getGroupsByMemId(Integer memId);
    List<Group> findByThemeContaining(String keyword);

    List<Group> findGroupByGroupSta(Integer groupSta);

    List<Group> findGroupByPaymentSta(Integer paymentSta);

    Stream<GroupRegFormDto> findGroupRegFormDtoByMemId(HttpServletRequest request, Integer page);

    List<GroupGroupPicDto> findGroupRegFormDtoByGroupIdOrderByGroupPicId(Integer groupId);

    //以自定義方法製作分頁時到Service必須以Stream包裝型別
    //Java 8 API添加了一個新的抽象稱爲流Stream，可以讓你以一種聲明的方式處理數據。
    //Stream 使用一種類似用 SQL 語句從數據庫查詢數據的直觀方式來提供一種對 Java 集合運算和表達的高階抽象。
    Stream<GroupListDto> findGroupListByGroupSta(Integer page);

    Stream<MyGroupListDto> findMyGroupListDtoByMemId(HttpServletRequest request, Integer page);

    UpdateMyGroupDto findUpdateMyGroupByGroupId(Integer groupId);

    Stream<GroupListDto> findGroupByGroupStaOrderByDeadline(Integer page);

    Stream<GroupListDto> findGroupByGroupStaOrderByDeadlineDesc(Integer page);

    Stream<GroupListDto> findGroupByGroupStaOrderByAmount(Integer page);

    Stream<GroupListDto> findGroupByGroupStaOrderByAmountDesc(Integer page);

    Stream<GroupListDto> findGroupByGroupStaThemeLike(String str, Integer page);

    GroupMemo findGroupMemo(Integer groupId);

    GroupMemberDto finGroupMember(Integer groupId);

    Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta0(HttpServletRequest request, Integer page);

    Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta1(HttpServletRequest request, Integer page);
    Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta4(HttpServletRequest request, Integer page);
    Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta5(HttpServletRequest request, Integer page);

    Stream<GroupRegFormDto> findGroupRegFormDtoByMemIdAndThemeLike(HttpServletRequest request, String str, Integer page);

    void updateGroupSta(Integer groupId, Group group);

    Group findMemberDeadlineAmountByDetailId(Integer detailId);

    List<Group> findAll();

    void updateGroupStaPaymentSta(Integer groupId, Group group);

}
