package tw.idv.cha102.g7.member.service;

import tw.idv.cha102.g7.member.dto.HostLoginDTO;
import tw.idv.cha102.g7.member.entity.Host;
import tw.idv.cha102.g7.member.entity.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

public interface HostService {


//管理員資料管理
//管理員權限管理

    //管理員註冊
    public String insert(Host host);

    //管理員帳號刪除
    public void deleteById(Integer hostId);

    public void hostLogin(@Valid HostLoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response);

    public Host update(Host host);

    public List<Member> getAllPaged(int page, int size);

    public List<Host> getAll();


}