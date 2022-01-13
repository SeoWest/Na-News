/*
package com.example.demo.controller;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;
import com.example.demo.utils.ScriptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.CommandMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MemberController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/loginCK", method = {RequestMethod.POST, RequestMethod.GET})
    public void loginCK(@RequestBody Member member, CommandMap commandMap, HttpServletRequest req, HttpServletResponse res, Model model) throws Exception {
        res.setContentType("text/html; charset=UTF-8");

        System.out.println(member);
        int flag = memberService.Login(member, commandMap.getMap(), req);
        System.out.println("flag : " + flag);
        if(flag == 0) {
            res.getWriter().print("<script>alert('등록된 아이디가 아닙니다.\\n센터에 문의해주시기바랍니다.');window.location.href='main_page.do'</script>");
        } else if(flag == 1) {
            res.getWriter().print("<script>alert('회원가입이 필요합니다.');window.location.href='registration_page.do'</script>");
        } else if(flag == 2) {
            res.getWriter().print("<script>alert('비밀번호를 잘못 입력하셨습니다.');window.location.href='main_page.do'</script>");
        } else if(flag == 3) {
            HttpSession session = req.getSession();
            Map<String, Object> map = (Map<String, Object>)session.getAttribute("user_info");

            ScriptUtils.alert(res, "완료완료완료");

PrintWriter out = res.getWriter();
            out.println("로그인 완료되었습니다!");
            out.flush();

            //return ;

        }
    }
}
*/
