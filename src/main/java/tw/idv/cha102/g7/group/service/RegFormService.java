package tw.idv.cha102.g7.group.service;

import org.springframework.data.domain.Page;
import tw.idv.cha102.g7.group.dto.RegFormMemberDetailDto;
import tw.idv.cha102.g7.group.dto.RegformIdDto;
import tw.idv.cha102.g7.group.entity.RegForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Stream;

public interface RegFormService {
    void insert(RegForm regForm, HttpServletRequest request);

    void update(Integer formId, RegForm regForm);

    void delete(Integer formId);

    RegForm getRegFormByFormId(Integer formId);

    List<RegForm> getAll();

    List<RegForm> findByMemIdOrderByRegTime(Integer memId);

    List<RegForm> findByGroupIdOrderByRegTime(Integer groupId);

    RegformIdDto findFormId();

    Stream<RegFormMemberDetailDto> findRegFormMemberDetailDtoByGroupId(Integer groupId, Integer page);

    List<RegForm> findByGroupIdOrderByFromId(Integer groupId);
}
