package com.example.demo.service;

import com.example.demo.common.CommandMap;
import com.example.demo.configuration.JwtTokenUtil;
import com.example.demo.controller.JwtAuthenticationController;
import com.example.demo.dao.BoardDAO;
import com.example.demo.mapper.BoardMapper;
import com.example.demo.model.Board;
import com.example.demo.model.Group;
import com.example.demo.model.Subscriber;
import com.example.demo.repository.BoardRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class Board1ServiceImpl implements Board1Service {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public List<Map<String, Object>> getAllBoard(Map<String, Object> map) throws Exception {
        String username = jwtTokenUtil.userInfo;
//        map.replace("paging", 10*(Integer.parseInt(map.get("paging").toString())-1));
        System.out.println("username : " + username);
        return boardMapper.getAllBoard(username);
    }

    @Override
    public List<Map<String, Object>> getAllBoard2(String email, Map<String, Object> map) throws Exception {
        String username = jwtTokenUtil.userInfo;
        return boardMapper.getAllBoard2(email);
    }

    @Override
    public Board createBoard(Board board, Map<String, Object> map, HttpServletRequest req) throws Exception {
        return boardRepository.save(board);
    }
    @Override
    public List<Group> getAllGroups() throws Exception {
        return boardMapper.getAllGroups();
    }

    public List<Map<String, Object>> getMember() throws Exception {
        String username = jwtTokenUtil.userInfo;
        List<Map<String, Object>> map = boardMapper.getMember(username);
        Map<String, Object> data = new HashMap<>();
        data.put("create_name", username);
        if(map.isEmpty()) {
            map.add(data);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> selectGroup(Subscriber subscriber, Map<String, Object> map) throws Exception { // 수신자별 전송 - 선택 조회 부분
        String username = jwtTokenUtil.userInfo;
        map.put("username", username);
        map.put("selectgroup", subscriber.getgroup_title());
        return boardMapper.selectGroup(map);
    }

    @Override
    public List<Map<String, Object>> getGroup(Subscriber subscriber, Map<String, Object> map) throws Exception { // 그룹 선택 모달창
        String username = jwtTokenUtil.userInfo;
        map.put("username", username);

        String[] selectgroup = subscriber.getgroup_title().replace(" ", "").split(",");
        List<Map<String, Object>> data = new ArrayList<>();

        for (int i=0; i<selectgroup.length; i++) {
            map.put("selectgroup", selectgroup[i]);
            data.addAll(boardMapper.getGroup(map));
        }
        return data;
    }

    //20210803 김영지
    public List<Map<String, Object>> group_print() throws Exception {
        String username = jwtTokenUtil.userInfo;
        return boardMapper.group_print(username);
    }

    public Subscriber subsinsert(Subscriber subscriber) throws Exception {
        String username = jwtTokenUtil.userInfo;
        subscriber.setCreate_name(username);
        return boardMapper.subsinsert(subscriber);
    }

    public Subscriber subsdelete(Integer id) throws Exception {
        return boardMapper.subsdelete(id);
    }

    /*public Subscriber subsupdate(Integer id, Subscriber subscriber) throws Exception{
        return boardMapper.subsupdate(id, subscriber);
    }*/
    public Subscriber subsupdate(Subscriber subscriber) throws Exception {
        System.out.println(subscriber);
        return boardMapper.subsupdate(subscriber);
    }

    public List<Map<String, Object>> getBoard(Integer id) throws Exception{
        return boardMapper.getBoard(id);
    }

    public List<Map<String, Object>> groupmana() throws Exception {
        String username = jwtTokenUtil.userInfo;
        return boardMapper.groupmana(username);
    }

    public Group groupinsert(Group group, Map<String, Object> map, HttpServletRequest req) throws Exception{
        String username = jwtTokenUtil.userInfo;
        group.setCreate_name(username);
        return boardMapper.groupinsert(group);
    }

    public Group groupdelete(Integer group_code) throws Exception{
        return boardMapper.groupdelete(group_code);
    }

    public Group groupupdate(Group group) throws Exception {
        System.out.println(group);
        return boardMapper.groupupdate(group);
    }

    public List<Map<String, Object>> getBoardByGroupcode(Integer group_code) throws Exception {
        return boardMapper.getBoardByGroupcode(group_code);
    }
}
