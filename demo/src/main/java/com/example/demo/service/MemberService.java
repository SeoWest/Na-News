package com.example.demo.service;

import com.example.demo.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MemberService {
    int Login(Member member, Map<String, Object> map, HttpServletRequest req) throws Exception;

    Map<String, Object> getUserInfo() throws Exception; // 마이페이지

    List<Map<String, Object>> getuserall() throws Exception; // 이메일 중복확인

    User edit_profile(User user) throws Exception; // 마이페이지 프로필 수정

    Group edit_email_group(User user) throws Exception;

    Subscriber edit_email_subscriber(User user) throws Exception;

    Board edit_email_board(User user) throws Exception;

    User edit_pw(User user) throws Exception; //마이페이지 비밀번호 수정
}
