package tw.idv.cha102.g7.common.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GrouperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String requestUrl = request.getRequestURI();
        String login = "/u-and-me/member/login";


        //許可所有請求進入登入畫面
        if (login.equals(requestUrl)) {
            chain.doFilter(request, response);
            return;
        }


        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("memberId"));
        //檢查是否已經登入 若是則跳過此條件進行內部功能 若否則進入此條件重導回登入頁面
        if (session.getAttribute("memberId") == null) {
//          response.getWriter().write("");
            response.setStatus(401);
            response.sendRedirect("http://localhost:8081/u-and-me/tmp/Front/member/memberLoginIn.html");
            return;
        } else if (session.getAttribute("memberId") != null && (session.getAttribute("grouper").toString().equals("0"))) {
//            response.sendRedirect( request.getContextPath() + "/member/memberGroupRegister");
            response.setStatus(403);
            System.out.println("grouper=" + (session.getAttribute("grouper")));
            return;
        } else {
            //我還沒打 :: 確認會員登入後檢查團主狀態!!確認是否為團主
            chain.doFilter(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
            System.out.println("你是團主!!!!" + (session.getAttribute("memberId")) + "grouper=" + (session.getAttribute("grouper")));
        }
    }
}