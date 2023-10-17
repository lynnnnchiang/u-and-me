package tw.idv.cha102.g7.group.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GroupOwnnerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        Members members = (Members) session.getAttribute("members");
//        if(members.mem_group = 1){
//          return true;}
        System.out.println("執行LoginInterceptor的preHandle");
//        session.setAttribute("preUrl", request.getRequestURI()); //未登入時紀錄
//        response.sendRedirect(request.getContextPath() + "/註冊揪團團主頁面");
//        return false;

        return true;
    }
}
