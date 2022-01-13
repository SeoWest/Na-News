package com.example.demo.controller;

import com.example.demo.common.CommandMap;
import com.example.demo.configuration.JwtTokenUtil;
import com.example.demo.model.Board;

import com.example.demo.model.Group;
import com.example.demo.model.Subscriber;
import com.example.demo.model.User;
import com.example.demo.service.BoardService;
import com.example.demo.service.Board1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BoardController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private BoardService boardService;
    @Autowired
    private Board1Service boardService1;


    // get all board
    /*@RequestMapping("/board")
    public List<Board> getAllBoards() throws Exception {
        log.debug("board");
        return boardService1.getAllBoard();
    }*/

    @GetMapping("/admin") // 관리자 계정 메인페이지
    public List<User> getAllUser() {
        return boardService.getAllUser();
    }

    @RequestMapping("/board") // 사용자 뉴스레터 목록
    public List<Map<String, Object>> getAllBoards(HttpServletRequest req, CommandMap commandMap) throws Exception {
        HttpSession session = req.getSession();
        Map<String, Object> map = new HashMap<>();
        map = (HashMap)session.getAttribute("user_info");
        System.out.println("세션정보 : " + map);
        System.out.println("page : " + commandMap.getMap().get("paging"));
        return boardService1.getAllBoard(commandMap.getMap());
    }

    @RequestMapping("/board_detail/{email}") // 관리자 회원 조회
    public List<Map<String, Object>> getAllBoards2(@PathVariable String email, HttpServletRequest req, CommandMap commandMap) throws Exception {
        HttpSession session = req.getSession();
        Map<String, Object> map = new HashMap<>();
        map = (HashMap)session.getAttribute("user_info");
        return boardService1.getAllBoard2(email, commandMap.getMap());
    }

    @RequestMapping("/createBoard") // 뉴스레터 작성
    public Board createBoard(@RequestBody Board board, Map<String, Object> map, HttpServletRequest req) throws Exception {

        return boardService1.createBoard(board, map, req);
    }

    @PostMapping("/create-board") // 뉴스레터 메일 전송
    public void mailStart(@RequestBody Board board2, Map<String, Object>map, HttpServletRequest req, HttpServletResponse res, CommandMap commandMap) throws Exception {
        String msg = boardService.mailStart(board2, commandMap);
        //return boardService.mailStart(board2, map, req, res, commandMap);
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(msg);
        out.flush();
        return;
    }

    @RequestMapping("/create-board") //멤버조회
    public List<Map<String, Object>> getMember() throws Exception {
        return boardService1.getMember();
    }

    @RequestMapping("/selectGroup") // 수신자별 전송 - 선택 조회 부분
    public List<Map<String, Object>> selectGroup(@RequestBody Subscriber subscriber, Map<String, Object> map) throws Exception {
        String username = jwtTokenUtil.userInfo;
        return boardService1.selectGroup(subscriber, map);
    }

    @RequestMapping("/selected") // 그룹 선택 모달창 (해당 그룹명을 가진 수신자 정보 가져오기)
    public List<Map<String, Object>> getGroup(@RequestBody Subscriber subscriber, Map<String, Object> map) throws Exception {
        return boardService1.getGroup(subscriber, map);
    }


    @RequestMapping("/group") // 주소록 목록
    public List<Map<String, Object>> group_print() throws Exception {
        return boardService1.group_print();
    }
    @PostMapping("/group") // 주소록 삽입
    public Subscriber subsinsert(@RequestBody Subscriber subscriber) throws Exception {
        return boardService1.subsinsert(subscriber);
    }
    @DeleteMapping("/group/{id}") // 주소록 삭제
    public Subscriber subsdelete(@PathVariable Integer id) throws Exception {
        return boardService1.subsdelete(id);
    }

    @PutMapping("/group/{id}") // 주소록 수정
    public Subscriber subsupdate(@RequestBody Subscriber subscriber) throws Exception {
        return boardService1.subsupdate(subscriber);
    }

    @GetMapping("/group/{id}") // 수정 모달창 기존 정보 가져오기
    public List<Map<String, Object>> getBoardById(@PathVariable Integer id) throws Exception {
        return boardService1.getBoard(id);
    }

    @RequestMapping("/groupmana") // 그룹 목록
    public List<Map<String, Object>> groupmana() throws Exception {
        return boardService1.groupmana();
    }

    @PostMapping("/groupmana") // 그룹 추가
    public Group groupinsert(@RequestBody Group group, Map<String, Object>map, HttpServletRequest req) throws Exception {
        return boardService1.groupinsert( group, map, req);
    }

    @DeleteMapping("/groupmana/{group_code}") // 그룹 삭제
    public Group groupdelete(@PathVariable Integer group_code) throws Exception {
        return boardService1.groupdelete(group_code);
    }

    @PutMapping("/groupmana/{group_code}")
    public Group groupupdate(@RequestBody Group group) throws Exception {
        return boardService1.groupupdate(group);
    }

    @GetMapping("/groupmana/{group_code}")
    public List<Map<String, Object>> getBoardByGroupcode(@PathVariable Integer group_code) throws Exception {
        return boardService1.getBoardByGroupcode(group_code);
    }

}