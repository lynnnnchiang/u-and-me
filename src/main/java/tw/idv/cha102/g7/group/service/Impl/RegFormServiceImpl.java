package tw.idv.cha102.g7.group.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.group.dto.RegFormMemberDetailDto;
import tw.idv.cha102.g7.group.dto.RegformIdDto;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.group.entity.RegForm;
import tw.idv.cha102.g7.group.repo.GroupRepository;
import tw.idv.cha102.g7.group.repo.RegFormRepository;
import tw.idv.cha102.g7.group.service.RegFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

@Component
public class RegFormServiceImpl implements RegFormService {
    @Autowired
    RegFormRepository regFormRepository;

    @Autowired
    private GroupRepository groupRepository;

    public void insert(RegForm regForm, HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        regForm.setMemId(memId);
        Group group = groupRepository.findById(regForm.getGroupId()).get();
        //如果參加人數小於最大人數才新增
        if(group.getMembers() + regForm.getJoinMember() <= group.getMaxMember()){
            regFormRepository.save(regForm);

            //將參與人數新增至group的參團人數
            group.setMembers(group.getMembers() + regForm.getJoinMember());
            groupRepository.save(group);
        }
        updGroupSta(regForm.getGroupId());
    }

    public void update(Integer formId, RegForm regForm){
        if(getRegFormByFormId(formId) != null) {
            regForm.setFormId(formId);
            regFormRepository.save(regForm);
        } else {
            throw new RuntimeException();
        }
    }

    public void delete(Integer formId){
        regFormRepository.deleteById(formId);
    }

    public RegForm getRegFormByFormId(Integer formId){
        return regFormRepository.findById(formId).orElse(null);
    }

    public List<RegForm> getAll(){
        return regFormRepository.findAll();
    }

    @Override
    public List<RegForm> findByMemIdOrderByRegTime(Integer memId) {
        return regFormRepository.findByMemIdOrderByRegTime(memId);
    }

    @Override
    public List<RegForm> findByGroupIdOrderByRegTime(Integer groupId) {
        return regFormRepository.findByGroupIdOrderByRegTime(groupId);
    }

    @Override
    public RegformIdDto findFormId() {
        return regFormRepository.findFormId();
    }

    @Override
    public Stream<RegFormMemberDetailDto> findRegFormMemberDetailDtoByGroupId(Integer groupId, Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "reg_time");
        Pageable pageable = PageRequest.of(page, 10, sort);
        return regFormRepository.findRegFormMemberDetailDtoByGroupId(groupId, pageable).get();
    }

    @Override
    public List<RegForm> findByGroupIdOrderByFromId(Integer groupId) {
        return regFormRepository.findByGroupIdOrderByFormId(groupId);
    }

    //以人數更新揪團狀態的方法
    void updGroupSta(Integer groupId){
        Group group = groupRepository.findById(groupId).orElse(null);
        if(group != null){
            if(group.getMembers() < group.getMinMember()){
                group.setGroupSta(0);//設置未成團狀態
            }else if(group.getMembers() == group.getMaxMember()){
                group.setGroupSta(5);//設置額滿狀態
            }else{
                group.setGroupSta(1);//設置成團狀態
            }
        }
        groupRepository.save(group);
    }
}
