package tw.idv.cha102.g7.group.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tw.idv.cha102.g7.group.dto.RegFormMemberDetailDto;
import tw.idv.cha102.g7.group.dto.RegformIdDto;
import tw.idv.cha102.g7.group.entity.RegForm;

import java.util.List;

public interface RegFormRepository extends JpaRepository<RegForm, Integer> {
    List<RegForm> findByMemIdOrderByRegTime(Integer memId);
    List<RegForm> findByGroupIdOrderByRegTime(Integer groupId);

    @Query(value = "SELECT MAX(form_id) AS formId FROM reg_form", nativeQuery = true)
    RegformIdDto findFormId();

    @Query(value = "SELECT r.group_id, r.mem_id, m.detail_id, r.form_id, m.name, r.reg_time, r.email, r.phone, r.join_member, m.idnumber, m.birthday, m.gender FROM reg_form AS r LEFT JOIN member_detail AS m ON r.form_id = m.form_id WHERE r.group_id = ?1", nativeQuery = true)
    Page<RegFormMemberDetailDto> findRegFormMemberDetailDtoByGroupId(Integer groupId, Pageable pageable);

    List<RegForm> findByGroupIdOrderByFormId(Integer groupId);

    @Query(value = "SELECT m.refund_sta FROM reg_form AS r LEFT JOIN member_detail AS m ON r.form_id = m.form_id WHERE r.group_id = ?1", nativeQuery = true)
    List<RegFormMemberDetailDto> findRefundSTaByGroupId(Integer groupId);
}
