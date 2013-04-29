package com.eventpool.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/20/12
 * Time: 5:27 PM
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String getIndexPage() {
    	System.out.println("Index ...");
        return "index";
    }
    
    @RequestMapping("/home")
    public String getIndex() {
    	System.out.println("home ...");
        return "home";
    }
}
