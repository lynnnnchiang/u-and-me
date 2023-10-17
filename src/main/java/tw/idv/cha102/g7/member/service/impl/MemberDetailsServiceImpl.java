package tw.idv.cha102.g7.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.group.entity.MemberDetail;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.MemberRepository;
import tw.idv.cha102.g7.member.service.MemberDetailsService;

import java.util.List;

@Component
public class MemberDetailsServiceImpl implements MemberDetailsService {

    @Autowired
    private MemberRepository memberRepository;
    public Member getMemberByMemEmail(String memEmail) {
        return memberRepository.findByMemEmail(memEmail);
    }

    public List<Member> getAll(){
        return (List<Member>) memberRepository.findAll();
    }

    @Override
    public Member getMember(Integer memId) {
        return memberRepository.findById(memId).orElse(null);
    }

}
