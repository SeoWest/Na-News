package com.example.demo.service;

import com.example.demo.configuration.JwtTokenUtil;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.*;
import com.example.demo.repository.GroupRepository;
import com.example.demo.utils.SHA_256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("memberService")
public class MemberServiceImpl implements MemberService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "SHA_256")
    private SHA_256 sha_256;

    @Override
    public int Login(Member member, Map<String, Object> map, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        Map<String, Object> resultMap = new HashMap<>();
        String member_id = member.getId();
        String member_pw = member.getPw();
        map.put("id", member_id);
        System.out.println("코드 전 : "+ map);
        if((resultMap = memberMapper.Login_1(member_id)) == null) {
            log.error("등록이 안된 회원 ID : " + map.get("id"));
            return 0;
        } else if(!resultMap.containsKey("pw")) {
            log.error("회원가입이 필요함 ID : " + map.get("id"));
            return 1;
        } else {
            try {
                map.put("pw", sha_256.encrypt(member_pw));
            } catch (Exception e) {
                log.error("비밀번호 sha 암호화 오류 "+e);
            }
            System.out.println("코드 후 : " + map);
            if((resultMap = memberMapper.Login_2(map)) == null) {
                log.error("비밀번호 오류 ID : " + map.get("id"));
                return 2;
            }
        }
        session.setAttribute("user_info", resultMap);
        session.setMaxInactiveInterval(10*60);
        log.debug("로그인 : " + resultMap);
        return 3;
    }

    @Override
    public Map<String, Object> getUserInfo() throws Exception { // 마이페이지
        String username = jwtTokenUtil.userInfo;
        return memberMapper.getUserInfo(username);
    }

    @Override
    public List<Map<String, Object>> getuserall() throws Exception { // 이메일 중복확인
        String username = jwtTokenUtil.userInfo;
        List<Map<String, Object>> result = new ArrayList<>();
        result.addAll(memberMapper.getuserall());

        for (int i=0; i<result.size(); i++) {
            if (result.get(i).get("email").equals(username)) {
                result.remove(i);
                break;
            }
        }

        return result;
    }

    @Override
    public User edit_profile(User user) throws Exception { // 마이페이지 프로필 수정
        String username = jwtTokenUtil.userInfo;
        Map<String, Object> map = new HashMap<>();

        map.put("username", username);
        map.put("email", user.getEmail());
        map.put("phone", user.getPhone());
        map.put("name", user.getName());
        map.put("message", user.getMessage());

        System.out.println("이거 : "+ map);
        return memberMapper.edit_info(map);
    }

    @Override
    public Group edit_email_group(User user) throws Exception {
        String username = jwtTokenUtil.userInfo;
        Map<String, Object> map = new HashMap<>();

        map.put("username", username);
        map.put("email", user.getEmail());
        return memberMapper.edit_email_group(map);
    }

    @Override
    public Subscriber edit_email_subscriber(User user) throws Exception {
        String username = jwtTokenUtil.userInfo;
        Map<String, Object> map = new HashMap<>();

        map.put("username", username);
        map.put("email", user.getEmail());
        return memberMapper.edit_email_subscriber(map);
    }

    @Override
    public Board edit_email_board(User user) throws Exception {
        String username = jwtTokenUtil.userInfo;
        Map<String, Object> map = new HashMap<>();

        map.put("username", username);
        map.put("email", user.getEmail());
        return memberMapper.edit_email_board(map);
    }

    @Override
    public User edit_pw(User user) throws Exception { // 마이페이지 비밀번호 수정
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String username = jwtTokenUtil.userInfo;
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", encoder.encode(user.getPassword()));
        return memberMapper.edit_pw(map);
    }
}
