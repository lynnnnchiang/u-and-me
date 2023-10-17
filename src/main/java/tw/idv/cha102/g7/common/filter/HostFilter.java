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

public class HostFilter extends OncePerRequestFilter {

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)throws ServletException, IOException{
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String requestUrl = request.getRequestURI();
        String hostLogin = "/u-and-me/host/hostLogin";


        //允許所有請求進入登入畫面
        if(hostLogin.equals(requestUrl)){
            chain.doFilter(request,response);
            return;
        }
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("hostId"));
        //檢查是否已經登入 如果沒有登入就重導至登入頁面
        if (session.getAttribute("hostId") == null) {

//            response.getWriter().write("");
            response.setStatus(401);
//            response.sendRedirect("http://localhost:8080/u-and-me/tmp/Front/member/memberLoginIn.html");
            return;
        }

        chain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }
}


