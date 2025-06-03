package com.corsojava.mvcdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @RequestMapping("/home")
    @ResponseBody
    public String home() {
    	String s =
    	"\n <br/>----------------------------------------- " +
    	"\n <br/>THIS IS HOME!!!" +
    	"\n <br/>----------------------------------------- <br/> \n";
    	System.out.print(s);
        return s;
    }
    
    
    
}
