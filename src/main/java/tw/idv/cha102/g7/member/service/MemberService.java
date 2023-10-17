package tw.idv.cha102.g7.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import tw.idv.cha102.g7.member.dto.LoginDTO;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface MemberService {
/////會員登入相關/////

    //會員註冊
    public String insert(Member member);

    //會員登入
    public void login(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response);

    //會員刪除
    public void deleteById(Integer memId);

    //會員資料修改
    public Member update(Member member);
//
//    public void updateMemByMemId(Integer memId, Member member) ;

    public Member getMemByMemId(Integer memId);

    }








