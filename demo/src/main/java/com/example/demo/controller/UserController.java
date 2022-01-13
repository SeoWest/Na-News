package com.example.demo.controller;

import com.example.demo.common.CommandMap;
import com.example.demo.configuration.JwtUserDetailsService;
import com.example.demo.dto.Response;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Board;
import com.example.demo.model.Group;
import com.example.demo.model.Subscriber;
import com.example.demo.model.User;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final JwtUserDetailsService userService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value="/signup", method = {RequestMethod.GET, RequestMethod.POST})
    public Response signup(@RequestBody UserDto infoDto) { // 회원 추가
        Response response = new Response();

        try {
            userService.save(infoDto);
            response.setResponse("success");
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @RequestMapping("/myPage") // 마이페이지
    public Map<String, Object> getUserInfo() throws Exception {
        return memberService.getUserInfo();
    }

    @RequestMapping("/getuserall") // 이메일 중복확인
    public List<Map<String, Object>> getuserall() throws Exception {
        return memberService.getuserall();
    }

    @RequestMapping("/edit_profile") // 마이페이지 프로필 수정
    public User edit_profile(@RequestBody User user) throws Exception {
        System.out.println("출력 : " + user);
        return memberService.edit_profile(user);
    }

    @RequestMapping("/edit_email_group")
    public Group edit_email_group(@RequestBody User user) throws Exception {
        return memberService.edit_email_group(user);
    }

    @RequestMapping("/edit_email_subscriber")
    public Subscriber edit_email_subscriber(@RequestBody User user) throws Exception {
        return memberService.edit_email_subscriber(user);
    }

    @RequestMapping("/edit_email_board")
    public Board edit_email_board(@RequestBody User user) throws Exception {
        return memberService.edit_email_board(user);
    }

    @RequestMapping("/edit_pw") // 마이페이지 비밀번호 수정
    public User edit_pw(@RequestBody User user) throws Exception {
        return memberService.edit_pw(user);
    }


}