package tw.idv.cha102.g7.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.member.dto.HostLoginDTO;
import tw.idv.cha102.g7.member.dto.LoginDTO;
import tw.idv.cha102.g7.member.entity.Host;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.HostRepository;
import tw.idv.cha102.g7.member.repo.MemberRepository;
import tw.idv.cha102.g7.member.service.HostService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Component
public class HostServiceImpl implements HostService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private MemberRepository memberRepository;
    public String insert(Host host) {
        if (hostRepository.findByHostEmail(host.getHostEmail()) == null) {
            host.setHostSta(0);
            hostRepository.save(host);
            return "您已成功註冊管理員'";
        }else {
            return "此Email已註冊過，請使用其他信箱!";
        }
    }

    @Override
    public void deleteById(Integer hostId){

        hostRepository.deleteById(hostId);
    }

    @Override
    public void hostLogin(HostLoginDTO hostloginDTO, HttpServletRequest request, HttpServletResponse response) {

        Host host = hostRepository.findByHostEmail(hostloginDTO.getHostEmail());
        System.out.println(host);
        if (host == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此管理員");
//        String hashReqPwd = sha256Hash(loginDTO.getMemPassword());//傳入密碼加密
        //比較帳號密碼
        if (!host.getHostEmail().equals(hostloginDTO.getHostEmail()) || !host.getHostPassword().equals(hostloginDTO.getHostPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "帳號密碼錯誤");
        }else if(host.getHostSta() == 1){
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "您已被停權");
        }
        HttpSession httpSession = request.getSession();
//        httpSession.setAttribute("loggedInMember", member.getMemberId());
        // 添加 Cookie 到回應中
        Cookie sessionCookie = new Cookie("JSESSIONID", httpSession.getId());
        sessionCookie.setMaxAge(30 * 60); // 30 分鐘的過期時間
        sessionCookie.setPath("/"); // 設置 Cookie 的路徑
        response.addCookie(sessionCookie);
        httpSession.setAttribute("hostId", host.getHostId()); // 保存目前登入的管理員id，供後續使用

    }
    @Override
    public Host update(Host host) {
        String hostEmail = host.getHostEmail();
        Host existingHost = hostRepository.findByHostEmail(hostEmail);
        System.out.println("host:" + host);
        if (existingHost != null) {
            if(host.getHostPhone()!=null){
                existingHost.setHostPhone(host.getHostPhone());
            }if(host.getHostName()!=null){
                existingHost.setHostName(host.getHostName());
            }if(host.getHostEmail()!=null){
                existingHost.setHostEmail(host.getHostEmail());
            }if(host.getHostSta()!=null){
                existingHost.setHostSta(host.getHostSta());
            }

            System.out.println("existingHost" + existingHost);
            return hostRepository.save(existingHost);
        }
        return null;
    }
    @Override
    public List<Member> getAllPaged(int page, int size) {
        Page<Member> pageResult = memberRepository.findAll(
                PageRequest.of(page, //查詢的頁數 從0開始
                        size,//查詢的每頁筆數
                        Sort.by("memId").ascending())); //依造group_sta欄位升冪排序
        return pageResult.getContent();
    }

    public List<Host> getAll(){
        return (List<Host>) hostRepository.findAll();
    }
}


