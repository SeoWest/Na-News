package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Controller
public class HelloController {
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "좋은 아침");
        return "index";
    }

//    @GetMapping("/api/hello")
//    public String hello(){
//        return "안녕하세요. 현재 서버시간은 "+new Date() +"입니다. \n";
//    }

    @GetMapping("/main")
    public String home() {
        return "hello world spring";
    }

}
