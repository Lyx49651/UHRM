package com.longwang.uhrm;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.longwang.uhrm.Entity.Dao.EmployeeArchivesDao;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashMap;

@Controller
public class ViewController {
    EmployeeArchivesDao employeeArchivesDao;
    @Autowired
    @Qualifier("employeeDao")
    void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao){
        this.employeeArchivesDao = employeeArchivesDao;
    }
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(true);
        return templateResolver;
    }
    @Bean
    public SpringTemplateEngine templateEngine(){
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
        // speed up execution in most scenarios, but might be incompatible
        // with specific cases when expressions in one template are reused
        // across different data types, so this flag is "false" by default
        // for safer backwards compatibility.
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
    //主页面
    @RequestMapping(method = RequestMethod.GET,value = "/index")
    public String test(Model model){
        System.out.println(11111);
        return "home";
    }
//    用户登录
    @RequestMapping(method = RequestMethod.GET,value = "/user_login")
    public String user_login(Model model){
        return "candidates_login";
    }
//    员工登录
    @RequestMapping(method = RequestMethod.GET,value = "/personnel_login")
    public String personnel_login(Model model){

        return "employee_login";
    }
    //用户登录信息确认
    @RequestMapping(method = RequestMethod.POST,value = "/user_login_check")
        //@ResponseBody
        public String user_login_check(@RequestParam("phone") String phone, @RequestParam("password") String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println(phone + password);
        Boolean res =  employeeArchivesDao.authenticate(1, password);
        return "redirect:index";
    }
    //员工登录信息确认
    @RequestMapping(method = RequestMethod.POST,value = "/employee_login_check")
    @ResponseBody
    public JSONObject employee_login_check(@RequestBody HashMap<String, String> map , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println(1);
        System.out.println(map.get("id") + map.get("password"));
        Boolean res =  employeeArchivesDao.authenticate(Integer.parseInt(map.get("id")), map.get("password"));
//        if(res){
////            HttpSession httpSession = httpServletRequest.getSession();//获取session
////            httpSession.setAttribute("username",id);
////            httpSession.setMaxInactiveInterval(2*60);//设置session存活时间
////            Cookie cookie = new Cookie("sessionId",String.valueOf(id));//新建cookie供客户端使用
////            cookie.setMaxAge(2*60);// 设置存在时间为30分钟
////            cookie.setPath("/");//设置作用域
////            httpServletResponse.addCookie(cookie);
////        }
        //请注意，因为session和cookie会给测试工作带来很大的复杂性，因此在整个开发过程，没有直接用到的，均暂时注解掉

//        return "redirect:index";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","pass");
        return jsonObject;
    }
}
