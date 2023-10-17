package tw.idv.cha102.g7.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tw.idv.cha102.g7.member.dto.LoginDTO;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.MemberRepository;
import tw.idv.cha102.g7.member.service.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Service
public class MemberServiceImpl implements MemberService {


    @Override
    public void login(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {

        Member member = memberRepository.findByMemEmail(loginDTO.getMemEmail());
        System.out.println(member);
        if (member == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此使用者");
//        String hashReqPwd = sha256Hash(loginDTO.getMemPassword());//傳入密碼加密
        //比較帳號密碼
        if (!member.getMemEmail().equals(loginDTO.getMemEmail()) || !member.getMemPassword().equals(loginDTO.getMemPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "帳號密碼錯誤");
        }else if(member.getMemSta() == 1){
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "您已被停權");
        }
        HttpSession httpSession = request.getSession();
//        httpSession.setAttribute("loggedInMember", member.getMemberId());
        // 添加 Cookie 到回應中
        Cookie sessionCookie = new Cookie("JSESSIONID", httpSession.getId());
        sessionCookie.setMaxAge(30 * 60); // 30 分鐘的過期時間
        sessionCookie.setPath("/"); // 設置 Cookie 的路徑
        response.addCookie(sessionCookie);
        httpSession.setAttribute("memberId", member.getMemId()); // 保存目前登入的會員id，供後續使用
        httpSession.setAttribute("grouper", member.getMemGroup()); // 保存目前登入的團主狀態，供後續使用

    }

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JavaMailSender mailSender;
    /**
     * 新增
     *
     * @param member
     */
    @Override
    public String insert(Member member) {
        if (memberRepository.findByMemEmail(member.getMemEmail()) == null) {
            member.setMemSta(0);
            member.setMemGroup(0);
            memberRepository.save(member);

//寄註冊成功
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("xiaoina914@gmail.com");
            message.setTo(member.getMemEmail());
            message.setSubject("U-and-Me 註冊成功通知");
            message.setText(
                    "＊－。－。－。－。－。－。－。－－。－。－。－。－＊"+"\n\n"+
                    "您好!" + member.getMemName()+"\n\n"+
                    "您已成功註冊 遊&Me 官方會員!"+"\n\n"+
                    "＊－。－。－。－。－。－。－。－－。－。－。－。－＊");

            mailSender.send(message);

            return "您已成功註冊";
        } else {
            return "此Email已註冊過，請使用其他信箱!";
        }
    }

    @Override
    public void deleteById(Integer memId) {

        memberRepository.deleteById(memId);
    }

//    public Boolean login(Integer memId,String memPassword) {
//        return memberRepository.findById(memId).orElseThrow().getMemPassword().equals(memPassword);
//    }

//    @Override
//    public String login(String memEmail, String memPassword) {
//        Member member = memberRepository.findByMemEmail(memEmail);
////        System.out.println(member.toString());
//        if (member.getMemPassword().equals(memPassword)) {
//            // 登入成功，檢查團主狀態
//            if (member.getMemGroup() == 1) {
//                return "您已登入團主狀態";
//            } else {
//                return "您已登入會員狀態";
//            }
//        }
//        return "登入失敗";
//    }
//
//    @Override
//    public Integer getMemberStatus(Integer memId, String memPassword) {
//        Optional<Member> optionalMember = memberRepository.findById(memId);
//        if (optionalMember.isPresent()) {
//            Member member = optionalMember.get();
//            if (member.getMemPassword().equals(memPassword)) {
//                return member.getMemSta();
//            }
//        }
//        return null;
//    }
//
//


    @Override
    public Member update(Member member) {
        String memEmail = member.getMemEmail();
        Member existingMember = memberRepository.findByMemEmail(memEmail);
//        Member existingMember = memberRepository.findById(member.getMemId()).orElse(null);
        System.out.println("member:" + member);
        if (existingMember != null) {
            if (member.getMemPhone() != null) {
                existingMember.setMemPhone(member.getMemPhone());
            }
            if (member.getMemName() != null) {
                existingMember.setMemName(member.getMemName());
            }
            if (member.getMemIdcard() != null) {
                existingMember.setMemIdcard(member.getMemIdcard());
            }
            if (member.getMemCity() != null) {
                existingMember.setMemCity(member.getMemCity());
            }
            if (member.getMemDist() != null) {
                existingMember.setMemDist(member.getMemDist());
            }
            if (member.getMemAddr() != null) {
                existingMember.setMemAddr(member.getMemAddr());
            }
            if (member.getMemGender() != null) {
                existingMember.setMemGender(member.getMemGender());
            }
            if (member.getMemIdPic() != null) {
                existingMember.setMemIdPic(member.getMemIdPic());
            }
            if (member.getMemEmail() != null) {
                existingMember.setMemEmail(memEmail);
            }
            if (member.getMemGroup() != null) {
                existingMember.setMemGroup(member.getMemGroup());
            } if (member.getMemSta() != null) {
                existingMember.setMemSta(member.getMemSta());
            }if (member.getMemPassword() != null) {
                existingMember.setMemPassword(member.getMemPassword());
            }




            System.out.println("existingMember" + existingMember);
            return memberRepository.save(existingMember);
        }
        return null;
    }


//    @Override
//    public Member updateMemByMemId(Integer memId, Member member) {
//        var existingMember = memberRepository.findById(memId);
//        if (existingMember != null) {
//            if(member.getMemPhone()!=null){
//                existingMember.setMemPhone(member.getMemPhone());
//            }if(member.getMemName()!=null){
//                existingMember.setMemName(member.getMemName());
//            }if(member.getMemIdcard()!=null){
//                existingMember.setMemIdcard(member.getMemIdcard());
//            }if(member.getMemAddr()!=null){
//                existingMember.setMemAddr(member.getMemAddr());
//            }if(member.getMemGender()!=null){
//                existingMember.setMemGender(member.getMemGender());
//            }
//
//            System.out.println("existingMember" + existingMember);
//            return memberRepository.save(existingMember);
//        }
//        return null;
//    }

    @Override
    public Member getMemByMemId(Integer memId) {
        return memberRepository.findById(memId).orElse(null);
    }
//    @Override
//    public Member sendEmailVerify(Member member) {
//        String memEmail = member.getMemEmail();
//
//        if (memEmail != null) {
//       MemberService sendMember = new MailService(member);
//
//        }
//        return null;
//    }
//第一步，確認用戶EMAIL是否註冊過，
//@Override
//public Member checkEmail(Member member) {
//        String memEmail = member.getMemEmail();
//        if (memEmail != null) {
//            return ("此Email已註冊過");
//        }
////       MemberService sendMember = new MailService(member);
////
////        }
////        return null;
////    }
//    if(dao.selectByMemEmail(mem.getMemEmail()) != null){
//
//        mem.setMessage("此Email信箱已註冊！");
//        mem.setSuccessful(false);
//        return mem;
//    }
//
//    mem.setMessage("Email可使用！");
//    mem.setSuccessful(true);
//    return mem;
//}

}



