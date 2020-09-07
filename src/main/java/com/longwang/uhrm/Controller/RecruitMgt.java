package com.longwang.uhrm.Controller;

import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Dao.*;
import com.longwang.uhrm.Entity.*;
import com.longwang.uhrm.Proxy.LogInterface;
import com.longwang.uhrm.Tool.Solution;
import com.longwang.uhrm.Tool.getSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Controller
public class RecruitMgt {

    RecruitmentNoticeDao recruitmentNoticeDao;
    CollectTableDao collectTableDao;
    Solution solution;
    DepartmentDao departmentDao;
    PositionDao positionDao;
    UserDao userDao;
    EmployeeArchivesDao employeeArchivesDao;

    @Autowired
    void setRecruitmentNoticeDao(RecruitmentNoticeDao recruitmentNoticeDao) {
        this.recruitmentNoticeDao = recruitmentNoticeDao;
    }

    @Autowired
    void setCollectTableDao(CollectTableDao collectTableDao) {
        this.collectTableDao = collectTableDao;
    }

    @Autowired
    void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Autowired
    void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Autowired
    void setPositionDao(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Autowired
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao) {
        this.employeeArchivesDao = employeeArchivesDao;
    }

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    private void log(String fun){
        LogInterface logInterface = (LogInterface)applicationContext.getBean("logDaoProxy");
        logInterface.time_function(fun);
    }

    //跳转到招聘系统的功能页面
    @RequestMapping(method = RequestMethod.GET, value = "/recruitment_system")
    public String recruitment_system(HttpServletRequest httpServletRequest, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<RecruitmentNotice> test = recruitmentNoticeDao.findAll();
        for (com.longwang.uhrm.Entity.RecruitmentNotice recruitmentNotice : test) {
            recruitmentNotice.setStringTime(recruitmentNotice.getTime().toString());
        }
        List<CollectTable> test1 =  collectTableDao.findAllPassed();
        for(CollectTable collectTable: test1){
            //System.out.println(collectTable.getDepartmentIdDepartment());
            collectTable.setDepartmentName(departmentDao.getName(collectTable.getDepartment_idDepartment()));
            collectTable.setNamePost(positionDao.getPostName(collectTable.getIdPost()));
        }
        model.addAttribute("plan", test1);
        model.addAttribute("list", test);

        List<User> users = userDao.getUserPassed();//new ArrayList<>();
        model.addAttribute("list1", users);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "Recruitment_system_functions";
    }

    //接收前端的招聘通知，存入数据库
    @RequestMapping(method = RequestMethod.POST, value = "/recruitment_notice")
    @ResponseBody
    public JSONObject recruitment_notice(@RequestBody HashMap<String, String> map) {
//        System.out.println(map.get("title") + map.get("content"));
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        com.longwang.uhrm.Entity.RecruitmentNotice recruitmentNotice = new com.longwang.uhrm.Entity.RecruitmentNotice();
        recruitmentNotice.setTitle(map.get("title"));
        recruitmentNotice.setContent(map.get("content"));
        Timestamp time = new Timestamp(System.currentTimeMillis());
        recruitmentNotice.setTime(time);
        recruitmentNoticeDao.addRecruitmentNotice(recruitmentNotice);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        return jsonObject;
    }
    //审核招聘计划
    @RequestMapping(method = RequestMethod.GET, value = "/recruitment_plan_check")
    public String recruitment_plan_check(HttpServletRequest httpServletRequest,Model model) {

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志

        List<CollectTable> test = collectTableDao.findAllSaved();
        for(int i=0;i<test.size();i++){
            test.get(i).setDepartmentName(departmentDao.getDepartmentById(test.get(i).getDepartment_idDepartment()).getNameDepartment());
            test.get(i).setNamePost(positionDao.getPost(test.get(i).getIdPost()).getPostName());
        }
        model.addAttribute("plan",test);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "recruitment_plan_check";
    }

    //招聘计划审核结果
    @RequestMapping(method = RequestMethod.POST, value = "/recruitment_plan_check_result")
    @ResponseBody
    public JSONObject recruitment_plan_check_result(@RequestBody HashMap<String, Object> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<String> member = solution.translate(map.get("member").toString());//现有人数
        List<String> max = solution.translate(map.get("max").toString());//编制人数
        List<String> form = solution.translate(map.get("form_member").toString());//原计划招聘人数
        List<String> rec = solution.translate(map.get("recruitment").toString());//审核后人数
        List<String> state = solution.translate(map.get("state").toString());//审核后状态
        List<String> depart = solution.translate(map.get("depart").toString());//部门和岗位名
        for(int i=0;i<member.size();i++){
            if(state.get(i).contains("#")){
                //System.out.println(state.get(i).split("#")[0]);
                collectTableDao.deleteById(Integer.parseInt(state.get(i).split("#")[0]));
            }else {
                collectTableDao.updatePassed(Integer.parseInt(state.get(i)), rec.get(i));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }

    //跳转到信息审核页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_namelist_check")
    public String recruitment_namelist_check(HttpServletRequest httpServletRequest,Model model){

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志

        List<User> users = userDao.getUserByCandiate();
        model.addAttribute("list",users);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "recruitment_name_list_check";
    }

    //接收审核后的报名应聘者
    @RequestMapping(method = RequestMethod.POST, value = "/modify_users")
    @ResponseBody
    public JSONObject modify_users(@RequestBody HashMap<String, Object> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        System.out.println(map.get("out_list"));
        List<String> out = solution.translate(map.get("out_list").toString());
        for(int i=0;i<out.size();i++){
            if(out.get(i).contains("#")){
                userDao.update_unpassed(Integer.parseInt(out.get(i).split("#")[0]));
            }else {
                userDao.update_passed(Integer.parseInt(out.get(i)));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }

    //跳转面试与笔试成绩审核页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_info_save")
    public String recruitment_info_save(HttpServletRequest httpServletRequest,Model model){

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志

        List<CandidateInfo> candidateInfos = userDao.userpassed_get();
        model.addAttribute("list", candidateInfos);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "select_recruitment_grade";
    }

    //接收经过笔试和面试成绩审核的信息
    @RequestMapping(method = RequestMethod.POST, value = "/modify_users_selected")
    @ResponseBody
    public JSONObject modify_users_selected(@RequestBody HashMap<String, Object> map) {
//        System.out.println(map.get("out_list"));
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<String> re = solution.translate(map.get("out_list").toString());
        for(int i=0;i<re.size();i++){
            if(re.get(i).contains("#")){
                userDao.user_untested(Integer.parseInt(re.get(i).split("#")[0]));
            }else {
                userDao.usertested(Integer.parseInt(re.get(i)));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }

    //跳转到招聘人归档页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_to_employee")
    public String recruitment_to_employee(HttpServletRequest httpServletRequest,Model model){

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<User> users = userDao.archive();
        List<String> depart = userDao.get_post(users);
        for (int i=0;i<users.size();i++){
            users.get(i).setDepart_name_sign_up(depart.get(i).split("--")[0]);
            users.get(i).setPost_name_sign_up(depart.get(i).split("--")[1]);
        }
        model.addAttribute("list", users);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "Archive";
    }

    //归档
    @RequestMapping(method = RequestMethod.POST, value = "/archive_data")
    @ResponseBody
    public JSONObject archive_data(@RequestBody HashMap<String, Object> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
//        System.out.println(map.get("title"));
//        System.out.println(map.get("name"));
        List<String> name = solution.translate(map.get("name").toString());
        List<String> sex = solution.translate(map.get("sex").toString());
        List<String> age = solution.translate(map.get("age").toString());
        List<String> address = solution.translate(map.get("address").toString());
        List<String> telephone = solution.translate(map.get("telephone").toString());
        List<String> education = solution.translate(map.get("education").toString());
        List<String> IdCard = solution.translate(map.get("IdCard").toString());
        List<String> title = solution.translate(map.get("title").toString());
        List<String> technicalGrade = solution.translate(map.get("technicalGrade").toString());
        List<String> depart = solution.translate(map.get("department").toString());
        List<String> post = solution.translate(map.get("post").toString());
        System.out.println(sex);
        System.out.println(age);
        System.out.println(address);
        System.out.println(telephone);
        System.out.println(education);
        for(int i=0;i<name.size();i++){
            EmployeeArchives temp = new EmployeeArchives();
            temp.setEmployeeName(name.get(i));
            temp.setEmployeeSex(sex.get(i));
            Timestamp time = new Timestamp(System.currentTimeMillis());
            String[] a = time.toString().split(" ")[0].split("-");
            a[0] = String.valueOf(Integer.parseInt(a[0]) - Integer.parseInt(age.get(i)));
            temp.setEmployeeBirthday(a[0] + "-" + a[1] + "-" + a[2]);
            temp.setEmployeeAddress(address.get(i));
            temp.setEmployeePhoneNumber(Integer.parseInt(telephone.get(i)));
            temp.setEmployeeEducation(education.get(i));
            temp.setEmployeeIdentity(IdCard.get(i));
            temp.setEmployeeTitle(title.get(i));
            temp.setEmployeeTechnicalGrade(technicalGrade.get(i));
            temp.setEmployeeDepartment(depart.get(i));
            temp.setEmployeePost(post.get(i));
            temp.setEmployeeId(employeeArchivesDao.max_id()+1);
            userDao.delete_tested();
            employeeArchivesDao.Archive(temp);
            temp.setPassword(userDao.delete_phone(telephone.get(i)));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }

    //跳转到上报招聘计划页面
    @RequestMapping(method = RequestMethod.GET, value = "/recruitment_plan_make")

    public String recruitment_plan_make(HttpServletRequest httpServletRequest,Model model) {

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        model.addAttribute("DepartmentList", departmentDao.getAll());
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "recruitment_plan_make";
    }
}
