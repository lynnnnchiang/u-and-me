package tw.idv.cha102.g7.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.attraction.dto.CommonResponse;
import tw.idv.cha102.g7.common.entity.PictureBase64DTO;
import tw.idv.cha102.g7.member.dto.LoginDTO;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.MemberRepository;
import tw.idv.cha102.g7.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    /**
     * 會員註冊
     *
     * @param member
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member member) {
        try {
            if("此Email已註冊過，請使用其他信箱!".equals(memberService.insert(member))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("exist account");
            }
            return ResponseEntity.ok("success");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fail");
        }
    }

    /**
     * 會員登入
     *
     * @param loginDTO
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<String>> login(@RequestBody @Valid LoginDTO loginDTO,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response
    ) {
        HttpSession session = request.getSession();
        CommonResponse commonResponse =new CommonResponse();
        if (session.getAttribute("memberId") == null) {
            memberService.login(loginDTO, request, response);
            commonResponse.setMessage("登入成功");
            return new ResponseEntity(commonResponse, HttpStatus.OK);
        }
        commonResponse.setMessage("登入失敗");
        return new ResponseEntity(commonResponse, HttpStatus.OK);


    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response
    ) {
        HttpSession session = request.getSession();
        String jsessionId = session.getAttribute("memberId").toString();
        if (jsessionId == null || jsessionId.isEmpty())
            return new ResponseEntity("登出失敗", HttpStatus.BAD_REQUEST);
        session.removeAttribute("memberId");
        session.removeAttribute("grouper");

        return new ResponseEntity("登出成功", HttpStatus.OK);
    }
    

    @RequestMapping("/testlogin")
    public ResponseEntity<String> testlogin(HttpServletRequest request,
                                            HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("memberId") == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        String sessionId = httpSession.getAttribute("memberId").toString(); // 保存目前登入的會員id，供後續使用
        System.out.println(sessionId);
        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }

    /**
     * 會員刪除
     *
     * @param memId
     * @return
     */
    @DeleteMapping("/delete/{memId}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer memId,
                                          HttpServletRequest request) {
        HttpSession session = request.getSession();
        String jsessionId = session.getAttribute("memberId").toString();
        if (jsessionId.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        try {
            memberService.deleteById(memId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    /**
     * 會員資料呈現
     * @param member
     * @param model
     * @return
     */
    @GetMapping("/controller/showMemInfo")
    public String showMemInfo(Member member, ModelMap model){
        String memEmail = member.getMemEmail();
        Member existingMember = memberRepository.findByMemEmail(memEmail);

        Pattern pMember = Pattern.compile("(..[縣市]) (.{1,3}[區鄉鎮市])(.+)");
        Matcher mMember = pMember.matcher(existingMember.getMemAddr());
        Map<String,String> addMap = new LinkedHashMap<>();

        while (mMember.find()){
            addMap.put("memCity",mMember.group(1));
            addMap.put("memDist",mMember.group(2));
            addMap.put("memAddr",mMember.group(3));
        }
        model.addAttribute("memAddr",addMap);
        return "/member/memberUpdate.html";


    }
    /**
     * 會員資料更新
     *
     * @param member
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Member> update(@RequestBody Member member) {
        System.out.println("UPDATE");
        Member updatedMember = memberService.update(member);
        if (updatedMember != null) {
            System.out.println("SUCCESS");
            return new ResponseEntity<>(updatedMember,HttpStatus.OK);
//            return ResponseEntity.ok(updatedMember);
        } else {
            System.out.println("FAILED");
            return ResponseEntity.notFound().build();
        }


    }

    @RequestMapping("/getMemId")
    public ResponseEntity<Member> getMemId(HttpServletRequest request,
                                           HttpServletResponse response){
        HttpSession session = request.getSession();
        String jsessionId = session.getAttribute("memberId").toString();
        Integer memId = Integer.parseInt(jsessionId);
        Member member = memberService.getMemByMemId(memId);
        return new ResponseEntity<>(member,HttpStatus.OK);

    }


    /**
     *
     * @param request
     * @param memIdcardUpdate
     * @param groupRegisterCard
     * @return
     */
    @RequestMapping("/memberGroupRegister/{memIdcardUpdate}")
    public ResponseEntity<Member> memberGroupRegister(HttpServletRequest request,
                                                      @PathVariable String memIdcardUpdate,
                                                      @RequestBody PictureBase64DTO groupRegisterCard){
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Member member = memberService.getMemByMemId(memId);
        member.setMemGroup(1);
        System.out.println(groupRegisterCard.getPictureData().toString());
        member.setMemIdPic(groupRegisterCard.getPictureData());
        System.out.println("memBer"+member.toString());
        member.setMemIdcard(memIdcardUpdate);
        memberService.update(member);

        return new ResponseEntity(member, HttpStatus.OK);
    }

    @RequestMapping("/getMemberByMemId/{memId}")
    public ResponseEntity<Member> getMemberByMemId(@PathVariable Integer memId){


        return new ResponseEntity(memberService.getMemByMemId(memId), HttpStatus.OK);
    }
 @RequestMapping("/match")
    public void memMatch(){

    }


}
