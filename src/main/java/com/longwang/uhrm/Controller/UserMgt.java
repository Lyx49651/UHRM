package com.longwang.uhrm.controller;

import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Dao.UserDao;
import com.longwang.uhrm.Entity.CandidateInfo;
import com.longwang.uhrm.Entity.User;
import com.longwang.uhrm.Proxy.LogInterface;
import com.longwang.uhrm.Tool.convertdata;
import com.longwang.uhrm.Tool.getSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class UserMgt {
    UserDao userDao;

    @Autowired
    @Qualifier("userDao")
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    private void log(String fun){
        LogInterface logInterface = (LogInterface)applicationContext.getBean("logDaoProxy");
        logInterface.time_function(fun);
    }

    //    用户登录
    @RequestMapping(method = RequestMethod.GET, value = "/user_login")
    public String user_login(Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        return "candidates_login";
    }

    //用户登录信息确认
    @RequestMapping(method = RequestMethod.POST, value = "/personnel_login_check")
    @ResponseBody
    public JSONObject personnel_login_check(@RequestBody HashMap<String, String> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        Boolean res = userDao.login(map.get("phone"), map.get("password"));
        if (res) {
            HttpSession httpSession = httpServletRequest.getSession();//获取session
            String name = userDao.getName(map.get("phone"));
            httpSession.setAttribute("phone", map.get("phone"));
            httpSession.setAttribute("name", name);
            httpSession.setAttribute("type", "user");
            httpSession.setMaxInactiveInterval(40 * 60);//设置session存活时间
            Cookie cookie = new Cookie("name", name);//新建cookie供客户端使用
            cookie.setMaxAge(40 * 60);// 设置存在时间为30分钟
            cookie.setPath("/");//设置作用域68563656
            httpServletResponse.addCookie(cookie);
            model.addAttribute("typeBoolean", Boolean.FALSE);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("result", "pass");
            return jsonObject1;
        } else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("result", "undefined");
            return jsonObject1;
        }
    }

    //用户注册
    @RequestMapping(method = RequestMethod.POST, value = "/userRegister")
    @ResponseBody
    public JSONObject user_register(@RequestBody User user) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        boolean flag = userDao.register(user);
        JSONObject jsonObject = new JSONObject();
        if (flag) {
            jsonObject.put("result", "success");
        }
        return jsonObject;
    }

    //用户找回密码
    @RequestMapping(method = RequestMethod.POST, value = "/userRetrievePassword")
    @ResponseBody
    public JSONObject retrieve_password(@RequestBody HashMap<String, String> hashMap) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        String res = userDao.user_retrieve_password(hashMap.get("telephone"),hashMap.get("name"));
        JSONObject jsonObject = new JSONObject();
        if (res != null) {
            jsonObject.put("password", res);
            jsonObject.put("result", "success");
        } else {
            jsonObject.put("result", "default");
        }
        return jsonObject;
    }

    //查看修改用户个人信息
    @RequestMapping(method = RequestMethod.GET, value = "/userInfo")
    public String user_info(HttpServletRequest httpServletRequest, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        String phone = (String) httpServletRequest.getSession().getAttribute("phone");
        model.addAttribute("User", userDao.getUserByTelephone(phone));
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "user_info";
    }

    //完善,修改用户个人信息
    @RequestMapping(method = RequestMethod.POST,value = "/user_info_change")
    @ResponseBody
    public  JSONObject user_info_change(@RequestBody HashMap<String,String> hashMap,Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        User user = new User();
        user.setSex(hashMap.get("sex"));
        user.setAge(Long.parseLong(hashMap.get("age")));
        user.setAddress(hashMap.get("address"));
        user.setMailAddress(hashMap.get("mailAddress"));
        user.setIDCard(hashMap.get("IDCard"));
        user.setTelephone(hashMap.get("telephone"));
        user.setEducation(hashMap.get("education"));
        boolean flag=userDao.update_user_Info(user);
        JSONObject jsonObject=new JSONObject();
        if(flag){
            jsonObject.put("result","success");
        }else {
            jsonObject.put("result","failure");
        }
        return jsonObject;
    }
    //应聘者报名,获取到岗位信息
    @RequestMapping(method = RequestMethod.POST,value = "/sign_up")
    @ResponseBody
    public JSONObject sign_up(@RequestBody HashMap<String, String> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        String phone = (String) httpServletRequest.getSession().getAttribute("phone");
        User user=userDao.getUserByTelephone(phone);
        CandidateInfo candidateInfo=new CandidateInfo();
        candidateInfo.setIdCandidateInfo(user.getIdUser());
        candidateInfo.setStatus("unverified");
        candidateInfo.setDepartmentPost(map.get("post"));
        userDao.insert_candidate(candidateInfo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","success");
        return jsonObject;
    }
}
