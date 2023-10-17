package tw.idv.cha102.g7.group.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tw.idv.cha102.g7.group.dto.GroupRegFormMemberDetailDto;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.group.entity.MemberDetail;

import java.util.List;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Integer> {
    List<MemberDetail> findByFormId(Integer formId);

    @Query(value = "Select g.theme, m.* From member_detail AS m Left JOIN reg_form AS r ON m.form_id = r.form_id " +
            "LEFT JOIN `group` AS g on r.group_id = g.group_id " +
            "WHERE g.group_id = ?1 ORDER BY m.form_id", nativeQuery = true)
    List<GroupRegFormMemberDetailDto> findGroupRegFormMemberDetailDtoByGroupIdOrderByFormId(Integer groupId);

    List<MemberDetail> findByRefundSta(Integer refundSta);

    @Query(value = "Select m.* From member_detail AS m Left JOIN reg_form AS r ON m.form_id = r.form_id " +
            "LEFT JOIN `group` AS g on r.group_id = g.group_id " +
            "WHERE r.mem_id = ?1", nativeQuery = true)
    List<MemberDetail> findByMemId(Integer memId);

    @Query(value = "Select m.* From member_detail AS m Left JOIN reg_form AS r ON m.form_id = r.form_id " +
            "LEFT JOIN `group` AS g on r.group_id = g.group_id " +
            "WHERE g.group_id = ?1 ORDER BY m.form_id", nativeQuery = true)
    List<MemberDetail> findByGroupId(Integer groupId);
}
