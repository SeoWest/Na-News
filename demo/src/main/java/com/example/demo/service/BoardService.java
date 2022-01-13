package com.example.demo.service;

import com.example.demo.common.CommandMap;
import com.example.demo.configuration.JwtTokenUtil;
import com.example.demo.mapper.BoardMapper;
import com.example.demo.model.Board;
import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service("BoardService")
public class BoardService  {

    Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardMapper boardMapper; //20210719 이윤희 받는사람 이름으로 메일 보내기
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "fileUtils")
    private FileUtils1 fileUtils;

    // get boards data
    public List<Board> getAllBoard() {
        return boardRepository.findAll();
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //create board
    public Board createBoard(Board board, Map<String, Object>map, HttpServletRequest req) throws Exception {
        //BoardService.this.mailStart(map, req);
        return boardRepository.save(board);
    }

    //public List<Group> getAllGroups() { return groupRepository.findGroupByGroupCodeAndGroup_title(2, "으하하"); }
    public List<Group> getAllGroups() {
        //return groupRepository.findGroupMember(2, "dd", "dd");}
        return groupRepository.findGroupByGroupCodeAndGroup_title(0, "0");}
    //Mail 전송
    /*public String mailStart(Board board, Map<String, Object> map, HttpServletRequest req) throws Exception {
        JSONObject obj = new JSONObject();
        try{
            HttpSession session = req.getSession();
            map.putAll((HashMap) session.getAttribute("board"));
            String Sender = (String)map.get("member_no");
            String Reciver = (String)map.get("member_rev");
            String Content = (String)map.get("contents");
            String Title= (String)map.get("title");
            System.out.println(Title);
            new Send_Mail(Sender, Reciver, Content, Title).Send_News_Mail();
            obj.put("msg", "메일이 전송되었습니다.");
        } catch (Exception e) {
            obj.put("msg", "메일 전송 오류!");
        }
        return obj.toJSONString();
    }*/

    @SuppressWarnings("unchecked")
    public String mailStart(Board board2, CommandMap commandMap) throws Exception {
        String username = jwtTokenUtil.userInfo;
        JSONObject obj = new JSONObject();
        String msg = "";

        try {

            String[] addrArr = board2.getMemberRev().replace(" ", "").split(",");
            //String result = "";
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> data2 = new HashMap<>();
            for(int i=0; i<addrArr.length; i++) {
                data2.put("username", username);
                data2.put("addrArr", addrArr[i]);
                if (addrArr[i].contains("@")) {
                    //result = result + "," + addrArr[i];
                    if (boardMapper.memberRevName(data2) == null) {
                        data.put("email", addrArr[i]);
                        list.add(data);
                    } else {
                        commandMap.put("addrArr", boardMapper.memberRevName(data2)); //20210725
                        list.add(boardMapper.memberRevName(data2)); //20210725
                    }
                    continue;
                }

                //commandMap.put("addrArr", boardMapper.memberRev(addrArr[i]));
                Map<String, Object> result = boardMapper.memberRev(data2);
                list.add(result);
                //list.add(boardMapper.memberRev(addrArr[i]));
                //result = result + "," + boardMapper.memberRev(addrArr[i]).get("mail");
            }

            String Sender = board2.getMemberNo();
            List<Map<String, Object>> Reciver = list;
            String Content = board2.getContents();
            String Title = board2.getTitle();
            String File_id = board2.getFile_id();
            new Send_Mail(Sender, Reciver, Content, Title, File_id).Send_News_Mail(commandMap);
            obj.put("msg", "메일이 전송되었습니다.");
            msg = "메일이 전송되었습니다.";
        } catch (Exception e) {
            obj.put("msg", "죄송합니다. 메일 발송에 실패하였습니다.");
            msg = "죄송합니다. 메일 발송에 실패하였습니다.";
        }
        //return obj.toJSONString();
        return msg;
    }
}
