package com.example.demo.mapper;

import com.example.demo.model.Board;
import com.example.demo.model.Group;
import com.example.demo.model.Subscriber;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BoardMapper {
    List<Map<String, Object>> getAllBoard(String username) throws Exception;

    List<Map<String, Object>> getAllBoard2(String email) throws Exception;

    Map<String, Object> login() throws Exception;

    Board createBoard(Board board) throws Exception;

    List<Group> getAllGroups() throws Exception;

    List<Map<String, Object>> getMember(String username) throws Exception;

    Map<String, Object> memberRev(Map<String, Object> data2) throws Exception;

    Map<String, Object> memberRevName(Map<String, Object> data2) throws Exception;

    List<Map<String, Object>> selectGroup(Map<String, Object> map) throws Exception; // 그룹별 전송 - 선택 조회 부분

    List<Map<String, Object>> getGroup(Map<String, Object>map) throws Exception; // 그룹 선택 모달창

    //20210803 김영지
    List<Map<String, Object>> group_print(String username) throws Exception;

    Subscriber subsinsert(Subscriber subscriber) throws Exception;

    Subscriber subsdelete(Integer id) throws Exception;

    //Subscriber subsupdate(Integer id, Subscriber subscriber) throws Exception;
    Subscriber subsupdate(Subscriber subscriber) throws Exception;

    List<Map<String, Object>> getBoard(Integer id) throws Exception;

    List<Map<String, Object>> groupmana(String username) throws Exception;

    Group groupinsert(Group group) throws Exception;

    Group groupdelete(Integer group_code) throws Exception;

    Group groupupdate(Group group) throws Exception;

    List<Map<String, Object>> getBoardByGroupcode(Integer group_code) throws Exception;
}
