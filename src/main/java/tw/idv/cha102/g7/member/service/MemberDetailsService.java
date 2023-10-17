package tw.idv.cha102.g7.member.service;

import tw.idv.cha102.g7.group.entity.MemberDetail;
import tw.idv.cha102.g7.member.entity.Member;

import java.util.List;

public interface MemberDetailsService {

    //會員資訊查看
    public Member getMemberByMemEmail(String memEmail);

    List<Member> getAll();

    public Member getMember(Integer memId);

}
