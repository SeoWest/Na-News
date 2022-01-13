package com.example.demo.service;

import com.example.demo.common.CommandMap;
import com.example.demo.model.Board;
import com.example.demo.model.Group;
import com.example.demo.model.Subscriber;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface Board1Service {
    List<Map<String, Object>> getAllBoard(Map<String, Object> map) throws Exception;

    List<Map<String, Object>> getAllBoard2(String email, Map<String, Object> map) throws Exception;

    Board createBoard(Board board, Map<String, Object> map, HttpServletRequest req) throws Exception;

    List<Group> getAllGroups() throws Exception;

    List<Map<String, Object>> getMember() throws Exception;

    List<Map<String, Object>> selectGroup(Subscriber subscriber, Map<String, Object> map) throws Exception; // 수신자별 전송 - 선택 조회 부분

    List<Map<String, Object>> getGroup(Subscriber subscriber, Map<String, Object> map) throws Exception; // 그룹 선택 모달창

    //20210803 김영지
    List<Map<String, Object>> group_print() throws Exception;

    Subscriber subsinsert(Subscriber subscriber) throws Exception;

    Subscriber subsdelete(Integer id) throws Exception;

    //Subscriber subsupdate(Integer id, Subscriber subscriber) throws Exception;
    Subscriber subsupdate(Subscriber subscriber) throws Exception;

    List<Map<String, Object>> getBoard(Integer id) throws Exception;

    List<Map<String, Object>> groupmana() throws Exception;

    Group groupinsert(Group group, Map<String, Object> map, HttpServletRequest req) throws Exception;

    Group groupdelete(Integer group_code) throws Exception;

    Group groupupdate(Group group) throws Exception;

    List<Map<String, Object>> getBoardByGroupcode(Integer group_code) throws Exception;
}
