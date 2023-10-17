package tw.idv.cha102.g7.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.HostToMemberRepository;
import tw.idv.cha102.g7.member.repo.MemberRepository;
import tw.idv.cha102.g7.member.service.HostToMemberService;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HostToMemberServiceImpl implements HostToMemberService {
    @Autowired
    private HostToMemberRepository hostToMemberRepository;
    @Autowired
    private MemberRepository memberRepository;



    @Override
    public String setMemberStatus(String memEmail, Integer sta) {
        Member choiceMember = memberRepository.findByMemEmail(memEmail);
        if (choiceMember != null) {
            Member member = choiceMember;
             member.setMemSta(sta);
             return "修改成功!";
        }
        return "修改失敗，該會員不存在!";
    }

    @Override
    public String setMemberGroupSta(String memEmail, Integer groupSta){
        Member choiceMember = memberRepository.findByMemEmail(memEmail);
        if (choiceMember.getMemIdcard() != null) {
            Member member = choiceMember;
            member.setMemSta(groupSta);
            return "修改成功!該會員已更新完成團主狀態";
        }
        return "修改失敗，請再次嘗試";
    }

    @Override
    public Member getMemberByEmail(String memEmail) {
        return memberRepository.findByMemEmail(memEmail);
    }



}