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

import static java.lang.Integer.parseInt;

public class MemberFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String requestUrl = request.getRequestURI();
        String login = "/u-and-me/member/login";
        String register = "/u-and-me/member/register";


        //許可所有請求進入登入畫面
        if (login.equals(requestUrl) || register.equals(requestUrl)) {
            chain.doFilter(request, response);
            return;
        }


        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("memberId"));
        //檢查是否已經登入 若是則跳過此條件進行內部功能 若否則進入此條件重導回登入頁面
        if (session.getAttribute("memberId") == null) {
//            response.getWriter().write("");
            response.setStatus(401);
//            response.sendRedirect("http://localhost:8080/u-and-me/tmp/Front/member/memberLogin.html");
            return;
        }

        chain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }
}
