package com.longwang.uhrm.Tool;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class getSessionInfo {
    public static void getsessionInfo(HttpServletRequest request, Model model){
        HttpSession httpSession = request.getSession();
        String type = (String) httpSession.getAttribute("type");
        String name = (String) httpSession.getAttribute("name");
        if(name != null && type.equals("employee")){
            model.addAttribute("typeBoolean",true);
            model.addAttribute("name",httpSession.getAttribute("name"));
        }else if(name != null && type.equals("user")){
            model.addAttribute("typeBoolean",false);
            model.addAttribute("name",httpSession.getAttribute("name"));
        }
    }
}
