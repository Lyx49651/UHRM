package com.longwang.uhrm.controller;

import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Dao.EmployeeArchivesDao;
import com.longwang.uhrm.Entity.Contract;
import com.longwang.uhrm.Proxy.LogInterface;
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
import java.util.HashMap;
import java.util.List;

@Controller
public class ContractMgt {

    EmployeeArchivesDao employeeArchivesDao;

    @Autowired
    void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao) {
        this.employeeArchivesDao = employeeArchivesDao;
    }

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    private void log(String fun){
        LogInterface logInterface = (LogInterface)applicationContext.getBean("logDaoProxy");
        logInterface.time_function(fun);
    }

    //跳转到合同查询
    @RequestMapping(method = RequestMethod.GET,value = "/to_contract_query")
    public String to_contract_query(HttpServletRequest request, Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<Contract> list = employeeArchivesDao.findAllContract();
        getSessionInfo.getsessionInfo(request,model);
        model.addAttribute("list",list);
        return "contract_search";
    }

    //查询指定的合同
    @RequestMapping(method = RequestMethod.GET,value = "/contract_info_search")
    public String contract_info_search(HttpServletRequest request, Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        try{
            int id = Integer.parseInt( request.getParameter("idOrName"));
            Contract emp = employeeArchivesDao.getContractById(id);
            model.addAttribute("list",emp);
        }catch(Exception e){
            String name = request.getParameter("idOrName");
            List<Contract> list = employeeArchivesDao.getContractByName(name);
            model.addAttribute("list",list);
        }
        return "contract_search";
    }

    //to合同修改
    @RequestMapping(method = RequestMethod.GET,value = "/contract_info_change")
    public String contract_info_change(HttpServletRequest httpServletRequest,Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        int ContractId = Integer.parseInt(httpServletRequest.getParameter("id"));
        Contract contract = employeeArchivesDao.getContractById(ContractId);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        model.addAttribute("list",contract);
        return "contract_update";
    }

    //合同修改提交
    @RequestMapping(method = RequestMethod.POST,value = "/contract_change")
    @ResponseBody
    public JSONObject contract_change(@RequestBody HashMap<String,String> hashMap){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        Contract contract = new Contract();
        JSONObject jsonObject = new JSONObject();
        contract.setIdContract(Integer.parseInt(hashMap.get("contractId")));
        contract.setEmployeeId(Integer.parseInt(hashMap.get("employeeId")));
        contract.setContractPeriod(hashMap.get("contractPeriod"));
        contract.setEmployeeName(hashMap.get("employeeName"));
        contract.setEmployeeSex(hashMap.get("employeeSex"));
        contract.setSalary(hashMap.get("salary"));
        contract.setPosition(hashMap.get("position"));

        boolean res = employeeArchivesDao.contract_change(contract);

        if(res){
            jsonObject.put("result","success");
        }else{
            jsonObject.put("result","default");
        }

        return jsonObject;
    }

    //删除合同
    @RequestMapping(method = RequestMethod.GET,value = "/contract_delete")
    public String contract_delete(HttpServletRequest httpServletRequest,Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        int idContract = Integer.parseInt(httpServletRequest.getParameter("id"));
        employeeArchivesDao.delete_contract(idContract);

        List<Contract> list = employeeArchivesDao.findAllContract();
        model.addAttribute("list",list);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "contract_search";
    }

    //to新增合同
    @RequestMapping(method = RequestMethod.GET,value = "/to_add_contract")
    public String to_add_contract(HttpServletRequest httpServletRequest,Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "contract_import";
    }

    //新增合同
    @RequestMapping(method = RequestMethod.POST,value = "/add_contract")
    @ResponseBody
    public JSONObject add_contract(@RequestBody HashMap<String,String> hashMap){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        Contract contract = new Contract();
        JSONObject jsonObject = new JSONObject();
        contract.setEmployeeId(Integer.parseInt(hashMap.get("employeeId")));
        contract.setEmployeeName(hashMap.get("employeeName"));
        contract.setEmployeeSex(hashMap.get("employeeSex"));
        contract.setContractPeriod(hashMap.get("contractPeriod"));
        contract.setSalary(hashMap.get("salary"));
        contract.setPosition(hashMap.get("position"));

        boolean res = employeeArchivesDao.add_contract(contract);
        if(res){
            jsonObject.put("result","success");
        }else{
            jsonObject.put("result","default");
        }
        return jsonObject;
    }
}
