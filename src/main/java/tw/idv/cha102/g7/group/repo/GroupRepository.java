package tw.idv.cha102.g7.group.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tw.idv.cha102.g7.group.dto.*;
import tw.idv.cha102.g7.group.entity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findByMemIdOrderByGroupStaDesc(Integer memId); //揪團主的尋找揪團
//    List<Group> findGroupsByMemId(Integer memId);
    List<Group> findByThemeContaining(String keyword);

    List<Group> findGroupByGroupSta(Integer groupSta);

    List<Group> findGroupByPaymentSta(Integer paymentSta);



    @Query(value = "select g.group_id, g.mem_id, g.theme, g.dep_date, g.cover, r.form_id FROM `group` as g left join reg_form as r on g.group_id = r.group_id where r.mem_id = ?1", nativeQuery = true)
    Page<GroupRegFormDto> findGroupRegFormDtoByMemId(Integer memId, Pageable pageable);

    //以原生方法findAll製作分頁
    //用List<Dto>包裝想要回傳的屬性
    @Query(value = "Select g.*, gp.group_pic_id, gp.group_pic FROM `group` AS g LEFT JOIN group_picture AS gp ON g.group_id = gp.group_id " +
            "WHERE g.group_id = ?1 ORDER BY gp.group_pic_id", nativeQuery = true)
    List<GroupGroupPicDto> findGroupRegFormDtoByGroupIdOrderByGroupPicId(Integer groupId);

    //以自定義方法製作分頁
    //用Page<Dto>包裝想要回傳的屬性，返回值最後一個必須是Pageable pageable
    @Query(value = "SELECT group_id, cover, theme, amount, (min_member - members) AS m, DATEDIFF(deadline, current_date) as d FROM `group` WHERE group_sta = 0 OR group_sta = 1", nativeQuery = true)
    Page<GroupListDto> findGroupListByGroupSta(Pageable pageable);

    @Query(value = "SELECT group_id, theme, sch_id, group_sta, payment_sta FROM `group` WHERE mem_id = ?1", nativeQuery = true)
    Page<MyGroupListDto> findMyGroupListDtoByMemId(Integer memId, Pageable pageable);

    @Query(value = "SELECT min_member, max_member, theme, amount, dep_date, deadline, group_desc, notice, cover FROM `group` WHERE group_id = ?1", nativeQuery = true)
    UpdateMyGroupDto findUpdateMyGroupByGroupId(Integer groupId);

    @Query(value = "SELECT group_id, cover, theme, amount, (min_member - members) AS m, DATEDIFF(deadline, current_date) as d FROM `group` WHERE group_sta = 0 OR group_sta = 1 ORDER BY deadline", nativeQuery = true)
    Page<GroupListDto> findGroupByGroupStaOrderByDeadline(Pageable pageable);

    @Query(value = "SELECT group_id, cover, theme, amount, (min_member - members) AS m, DATEDIFF(deadline, current_date) as d FROM `group` WHERE group_sta = 0 OR group_sta = 1 ORDER BY deadline DESC", nativeQuery = true)
    Page<GroupListDto> findGroupByGroupStaOrderByDeadlineDesc(Pageable pageable);

    @Query(value = "SELECT group_id, cover, theme, amount, (min_member - members) AS m, DATEDIFF(deadline, current_date) as d FROM `group` WHERE group_sta = 0 OR group_sta = 1 ORDER BY amount", nativeQuery = true)
    Page<GroupListDto> findGroupByGroupStaOrderByAmount(Pageable pageable);

    @Query(value = "SELECT group_id, cover, theme, amount, (min_member - members) AS m, DATEDIFF(deadline, current_date) as d FROM `group` WHERE group_sta = 0 OR group_sta = 1 ORDER BY amount DESC", nativeQuery = true)
    Page<GroupListDto> findGroupByGroupStaOrderByAmountDesc(Pageable pageable);

    @Query(value = "SELECT group_id, cover, theme, amount, (min_member - members) AS m, DATEDIFF(deadline, current_date) as d FROM `group` WHERE (group_sta = 0 OR group_sta = 1) AND theme LIKE %?1%", nativeQuery = true)
    Page<GroupListDto> findGroupByGroupStaThemeLike(String str, Pageable pageable);

    @Query(value = "SELECT g.sch_id, g.theme, g.members, g.dep_date, g.amount, DATEDIFF(g.deadline, current_date) as days, g.min_member, g.max_member, g.group_sta, g.group_desc, g.cover, g.notice, m.mem_name FROM `group` AS g LEFT JOIN members AS m ON g.mem_id = m.mem_id WHERE group_id = ?1", nativeQuery = true)
    GroupMemo findGroupMemo(Integer groupId);

    @Query(value = "SELECT (max_member - members) AS `member`, amount FROM `group` WHERE group_id = ?1", nativeQuery = true)
    GroupMemberDto finGroupMember(Integer groupId);

    @Query(value = "SELECT g.group_id, g.mem_id, g.theme, g.dep_date, g.cover FROM `group` as g LEFT JOIN reg_form AS r ON g.group_id = r.group_id WHERE r.mem_id = ?1 AND g.group_sta = 0", nativeQuery = true)
    Page<GroupRegFormDto> findGroupRegFormDtoByMemIdGroupSta(Integer memId, Pageable pageable);

    @Query(value = "SELECT g.group_id, g.mem_id, g.theme, g.dep_date, g.cover, r.form_id FROM `group` as g LEFT JOIN reg_form as r on g.group_id = r.group_id WHERE r.mem_id = ?1 AND g.group_sta = 0", nativeQuery = true)
    Page<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta0(Integer memId, Pageable pageable);

    @Query(value = "SELECT g.group_id, g.mem_id, g.theme, g.dep_date, g.cover, r.form_id FROM `group` as g LEFT JOIN reg_form as r on g.group_id = r.group_id WHERE r.mem_id = ?1 AND g.group_sta = 1", nativeQuery = true)
    Page<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta1(Integer memId, Pageable pageable);

    @Query(value = "SELECT g.group_id, g.mem_id, g.theme, g.dep_date, g.cover, r.form_id FROM `group` as g LEFT JOIN reg_form as r on g.group_id = r.group_id WHERE r.mem_id = ?1 AND g.group_sta = 4", nativeQuery = true)
    Page<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta4(Integer memId, Pageable pageable);

    @Query(value = "SELECT g.group_id, g.mem_id, g.theme, g.dep_date, g.cover, r.form_id FROM `group` as g LEFT JOIN reg_form as r on g.group_id = r.group_id WHERE r.mem_id = ?1 AND g.group_sta = 5", nativeQuery = true)
    Page<GroupRegFormDto> findGroupRegFormDtoByMemIdAndGroupSta5(Integer memId, Pageable pageable);

    @Query(value = "SELECT g.group_id, g.mem_id, g.theme, g.dep_date, g.cover, r.form_id FROM `group` as g LEFT JOIN reg_form as r on g.group_id = r.group_id WHERE r.mem_id = ?1 AND g.theme LIKE %?2%", nativeQuery = true)
    Page<GroupRegFormDto> findGroupRegFormDtoByMemIdAndThemeLike(Integer memId, String str ,Pageable pageable);

    @Query(value = "SELECT g.* FROM member_detail AS m LEFT JOIN reg_form AS r ON m.form_id = r.form_id LEFT JOIN `group` AS g ON r.group_id = g.group_id WHERE m.detail_id =?1", nativeQuery = true)
    Group findMemberDeadlineAmountByDetailId(Integer detailId);

    @Query(value = "SELECT g.* FROM `group` AS g LEFT JOIN reg_form AS r ON g.group_id = r.group_id WHERE r.form_id = ?1", nativeQuery = true)
    List<Group> findByFormId(Integer formId);

    List<Group> findBySchId(Integer schId);
}
