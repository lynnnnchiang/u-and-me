package tw.idv.cha102.g7.group.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.group.dto.GroupRegFormMemberDetailDto;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.group.entity.MemberDetail;
import tw.idv.cha102.g7.group.repo.GroupRepository;
import tw.idv.cha102.g7.group.repo.MemberDetailRepository;
import tw.idv.cha102.g7.group.repo.RegFormRepository;
import tw.idv.cha102.g7.group.service.MemberDetailService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class MemberDetailServiceImpl implements MemberDetailService {
    @Autowired
    MemberDetailRepository memberDetailRepository;

    @Autowired
    RegFormRepository regFormRepository;

    @Autowired
    RegFormServiceImpl regFormService;

    @Autowired
    private GroupRepository groupRepository;

    public void insert(MemberDetail memberDetail) {

        memberDetailRepository.save(memberDetail);
    }

    //退費
    public void update(Integer detailId, MemberDetail memberDetail) {
        if (getMemberDetailByDetailId(detailId) != null) {
            MemberDetail updMemberDetail = memberDetailRepository.findById(detailId).get();
            Group group = groupRepository.findMemberDeadlineAmountByDetailId(detailId);

            //做日期相減計算天數
            Date depDate = group.getDepDate();
            long diff = depDate.getTime() - new Date().getTime();
            TimeUnit time = TimeUnit.DAYS;
            long difference = time.convert(diff, TimeUnit.MILLISECONDS);

            //依造出發日期判斷退費金額
            if (difference >= 8) {
                updMemberDetail.setRefund(group.getAmount());
                updMemberDetail.setRefundSta(2);
                group.setMembers(group.getMembers() - 1); //人數-1
            } else if (difference >= 4 && difference < 8) {
                updMemberDetail.setRefund((int) (group.getAmount() * 0.5));
                updMemberDetail.setRefundSta(2); //設置申請退款
                group.setMembers(group.getMembers() - 1); //人數-1
            }else{
                updMemberDetail.setRefund(0);
                updMemberDetail.setRefundSta(4);
            }


            //將時間存入RefundDate
            Long datetime = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(datetime);
            updMemberDetail.setRefundDate(timestamp);

            updMemberDetail.setAccount(memberDetail.getAccount());
            updMemberDetail.setReason(memberDetail.getReason());

            memberDetailRepository.save(updMemberDetail);

            //參團人數-1
            groupRepository.save(group);
            regFormService.updGroupSta(group.getGroupId());
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public void updateRefundSta(Integer detailId, MemberDetail memberDetail) {
        Optional<MemberDetail> updMemberDetail = memberDetailRepository.findById(detailId);
        if(updMemberDetail.isPresent()){
            updMemberDetail.get().setRefundSta(memberDetail.getRefundSta());
            memberDetailRepository.save(updMemberDetail.get());
        }
    }

    public void delete(Integer detailId) {
        memberDetailRepository.deleteById(detailId);
    }

    public MemberDetail getMemberDetailByDetailId(Integer detailId) {
        return memberDetailRepository.findById(detailId).orElse(null);
    }

    public List<MemberDetail> getAll() {
        return memberDetailRepository.findAll();
    }

    @Override
    public List<MemberDetail> findByFormId(Integer formId) {
            List<Group> groupList = groupRepository.findByFormId(formId);

        TimeUnit time = TimeUnit.DAYS;
        Date date = new Date();
        List<MemberDetail> memberDetailList = memberDetailRepository.findByFormId(formId);

        if(groupList != null){
            for(Group group : groupList){
                Date depDate = group.getDepDate();
                long diff =  date.getTime() - depDate.getTime();
                long difference = time.convert(diff, TimeUnit.MILLISECONDS);
                if(difference >= 14){
                    for(MemberDetail memberDetail : memberDetailList){
                        if(memberDetail.getRefundSta() == 0) {
                            memberDetail.setRefundSta(1);
                            memberDetailRepository.save(memberDetail);
                        }
                    }
                }
            }
        }

        return memberDetailList;
    }

    @Override
    public List<GroupRegFormMemberDetailDto> findGroupRegFormMemberDetailDtoByGroupIdOrderByFormId(Integer groupId) {
        return memberDetailRepository.findGroupRegFormMemberDetailDtoByGroupIdOrderByFormId(groupId);
    }

    @Override
    public List<MemberDetail> findByRefundSta(Integer refundSta) {
        return memberDetailRepository.findByRefundSta(refundSta);
    }

    @Override
    public List<MemberDetail> findByMemId(Integer memId) {
        return memberDetailRepository.findByMemId(memId);
    }

}
