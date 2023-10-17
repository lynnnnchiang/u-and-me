package tw.idv.cha102.g7.member.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.attraction.dto.CommonResponse;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.member.dto.HostLoginDTO;
import tw.idv.cha102.g7.member.dto.LoginDTO;
import tw.idv.cha102.g7.member.entity.Host;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.HostRepository;

import tw.idv.cha102.g7.member.service.HostService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/host")
public class HostController {



    @Autowired
    private HostService hostService;

    @Autowired
    private HostRepository hostRepository;


    /**
     * 管理員註冊
     * @param host
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestBody Host host) {
        return hostService.insert(host);
    }

    /**
     *
     * @param hostLoginDTO
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/hostLogin")
    public ResponseEntity<CommonResponse<String>> hostLogin(@RequestBody @Valid HostLoginDTO hostLoginDTO,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response
    ) {
        HttpSession session = request.getSession();
//        session.removeAttribute("hostId");
        System.out.println(session.getAttribute("hostId"));
        CommonResponse commonResponse =new CommonResponse();
        if (session.getAttribute("hostId") == null) {
            System.out.println("QAQ!");
            hostService.hostLogin(hostLoginDTO, request, response);
            commonResponse.setMessage("登入成功");
            return new ResponseEntity(commonResponse, HttpStatus.OK);
        }
        System.out.println("QAQqq!");
        commonResponse.setMessage("登入失敗");
        return new ResponseEntity(commonResponse, HttpStatus.OK);


    }
    @PostMapping("/hostLogout")
    public ResponseEntity<String> hostLogout(HttpServletRequest request,
                                         HttpServletResponse response
    ) {
        HttpSession session = request.getSession();
        String jsessionId = session.getAttribute("hostId").toString();
        if (jsessionId == null || jsessionId.isEmpty())
            return new ResponseEntity("登出失敗", HttpStatus.BAD_REQUEST);
        session.removeAttribute("hostId");
//        if(session.getAttribute("memberId").toString()==null)
//            System.out.println("NULL");
        return new ResponseEntity("登出成功", HttpStatus.OK);
    }

    @RequestMapping("/testHostLogin")
    public ResponseEntity<String> testHostLogin(HttpServletRequest request,
                                            HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("hostId") == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        String sessionId = httpSession.getAttribute("hostId").toString(); // 保存目前登入的會員id，供後續使用
        System.out.println(sessionId);
        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }
    /**
     * 刪除管理員
     * (By hostId)
     * @param hostId
     * @return
     */
    @DeleteMapping("/delete/{hostId}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer hostId) {
        try {
            hostService.deleteById(hostId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    /**
     * 更新管理員資料
     * @param host
     * @return
     */
    @PostMapping("/update")

    public ResponseEntity<Host> update(@RequestBody Host host) {
        Host updatedMember = hostService.update(host);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * 查詢會員管理
     * @param page 當前分頁 (從0開始)
     * @return 返回所有會員列表
     * 謝謝宇航大神
     */
    @GetMapping("/member/all/{page}")
    public List<Member> getAllPaged(@PathVariable Integer page){

        return hostService.getAllPaged(page,7);
    }


    /**
     * 管理員查詢全部管理員
     * @return
     */

    @GetMapping("/hostAll")
    public List<Host> hostAll(){
        return hostService.getAll();
    }

    @RequestMapping("/match")
    public void hostMatch(){

    }

}


