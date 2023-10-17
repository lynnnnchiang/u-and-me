package tw.idv.cha102.g7.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.group.entity.MemberDetail;
import tw.idv.cha102.g7.member.repo.MemberRepository;
import tw.idv.cha102.g7.member.service.MemberDetailsService;
import tw.idv.cha102.g7.member.service.MemberService;
import tw.idv.cha102.g7.member.entity.Member;

import java.util.List;

@RestController
@RequestMapping("/member/Details")
public class MemberDetailsController {
    @Autowired
    private MemberDetailsService memberDetailsService;

    @Autowired
    private MemberRepository memberRepository;


    /**
     * 顯示會員資訊(用memEmail查詢)
     * @param member
     * @return
     */
    @PostMapping("/memProfile")
    public Member viewMemberProfile(@RequestBody Member member) {
        Member mem = memberDetailsService.getMemberByMemEmail(member.getMemEmail());
            return mem;
    }

    /**
     * 查詢全部會員資料
     * @return
     */
    @GetMapping("/all")
    public List<Member> getAll(){
        return memberDetailsService.getAll();
    }

    /**
     * 查詢會員資料(透過memId)
     * @param memId
     * @return
     */
    @GetMapping("/{memId}")
    public Member getMem(@PathVariable Integer memId){
        return memberDetailsService.getMember(memId);
    }
}

