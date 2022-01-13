package com.example.demo.mapper;

import com.example.demo.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MemberMapper {
    Map<String, Object> Login_1(String member_id) throws Exception;

    Map<String, Object> Login_2(Map<String, Object> map) throws Exception;

    Map<String, Object> getUserInfo(String username) throws Exception; // 마이페이지

    List<Map<String, Object>> getuserall() throws Exception; // 이메일 중복확인

    User edit_info(Map<String, Object> map) throws Exception; // 마이페이지 프로필 수정

    Board edit_email_board(Map<String, Object> map) throws Exception; // 작성된 뉴스레터 member_no 변경

    Group edit_email_group(Map<String, Object> map) throws Exception; // 그룹 create_name 변경

    Subscriber edit_email_subscriber(Map<String, Object> map) throws Exception; // Subscriber create_name 변경

    User edit_pw(Map<String, Object> map) throws Exception; // 마이페이지 비밀번호 수정
}
