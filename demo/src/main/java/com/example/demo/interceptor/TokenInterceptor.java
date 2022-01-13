package com.example.demo.interceptor;

import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@NoArgsConstructor
public class TokenInterceptor extends HandlerInterceptorAdapter {
    protected Log log = LogFactory.getLog(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String url = request.getRequestURI();

        if (!url.startsWith("/schoolware/mobile")) { //스쿨웨어에서 붙여넣기
            response.setContentType("text/html;charset=UTF-8");
            if(session.getAttribute("user_info") ==  null) {
                //로그인 세션 없어도 허용되는 요청 주소 배열
                String acceptURL[] = {"/api/create-board", "/api/board", "/api/loginCK"};
                //아래 요청 외 다른 요청이면 다 차단 (로그인 세션없을 때)
                boolean flag = true;
                for(String e : acceptURL) {
                    if (url.equals(e)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    response.getWriter().write("<script>alert('로그인 유지 시간이 만료되었습니다.');window.location.href = '/api/create-board'</script>");
                    response.getWriter().flush();
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
