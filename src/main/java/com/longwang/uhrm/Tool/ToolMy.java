package com.longwang.uhrm.Tool;

import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Proxy.LogInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ToolMy{
    //此类来进行json数据封装用于统计信息
    //计算年龄区间
    private int caculation_age(String value){
        String[] a = value.split("-");
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        //计算年龄
        int age = Integer.parseInt(formatter.format(date).split("-")[0]) - Integer.parseInt(a[0]);
        if(10 <= age && age< 20){
            return 1;
        }else if(20 <= age && age< 30){
            return 2;
        }else if(30 <= age && age< 40){
            return 3;
        }else if(40 <= age && age< 50){
            return 4;
        }else {
            return 5;
        }
    }
    public JSONObject analysis_json(List<EmployeeArchives> list){
        //统计男女人数
        int numberMen = 0;
        int numberWomen = 0;
        //统计学历人数
        int level = 0;//本
        int level1 = 0;//硕
        int level2 = 0;//博
        //统计年龄
        int number = 0;
        int number1 = 0;
        int number2 = 0;
        int number3 = 0;
        int number4 = 0;
        for(EmployeeArchives employeeArchives:list){
            if(employeeArchives.getEmployeeSex().equals("男")){
                numberMen += 1;
            }else {
                numberWomen += 1;
            }
            if(employeeArchives.getEmployeeEducation().equals("本科")){
                level += 1;
            }else if(employeeArchives.getEmployeeEducation().equals("硕士")){
                level1 += 1;
            }else {
                level2 += 1;
            }
            switch (caculation_age(employeeArchives.getEmployeeBirthday())){
                case 1: number += 1;break;
                case 2: number1 += 1;break;
                case 3: number2 += 1;break;
                case 4: number3 += 1;break;
                default: number4 += 1;break;
            }
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        String[] type = new String[2];
        type[0] = "男士";
        type[1] = "女士";
        int[] value = new int[2];
        value[0] = numberMen;
        value[1] = numberWomen;
        JSONObject[] pipe_data = new JSONObject[2];
        jsonObject1.put("value",numberWomen);
        jsonObject1.put("name","女士");
        jsonObject2.put("value",numberMen);
        jsonObject2.put("name","男士");
        pipe_data[0] = jsonObject1;
        pipe_data[1] = jsonObject2;

        jsonObject.put("title", "性别分布");
        jsonObject.put("type",type);
        jsonObject.put("value",value);
        jsonObject.put("name","人数");
        jsonObject.put("pipe_data",pipe_data);
        //第二部分
        JSONObject jsonObject4 = new JSONObject();
        JSONObject jsonObject5 = new JSONObject();
        JSONObject jsonObject6 = new JSONObject();
        JSONObject jsonObject7 = new JSONObject();
        String[] type1 = new String[3];
        type1[0] = "本科";
        type1[1] = "硕士";
        type1[2] = "博士";
        int[] value1 = new int[3];
        value1[0] = level;
        value1[1] = level1;
        value1[2] = level2;
        JSONObject[] pipe_data1 = new JSONObject[3];
        jsonObject4.put("value",level);
        jsonObject4.put("name","本科");
        jsonObject5.put("value",level1);
        jsonObject5.put("name","硕士");
        jsonObject6.put("value",level2);
        jsonObject6.put("name","博士");
        pipe_data1[0] = jsonObject4;
        pipe_data1[1] = jsonObject5;
        pipe_data1[2] = jsonObject6;

        jsonObject7.put("title", "学历分布");
        jsonObject7.put("type",type1);
        jsonObject7.put("value",value1);
        jsonObject7.put("name","人数");
        jsonObject7.put("pipe_data",pipe_data1);
        //第三部分年龄
        JSONObject jsonObject8 = new JSONObject();
        JSONObject jsonObject9 = new JSONObject();
        JSONObject jsonObject10 = new JSONObject();
        JSONObject jsonObject11 = new JSONObject();
        JSONObject jsonObject12 = new JSONObject();
        JSONObject jsonObject13 = new JSONObject();

        String[] type2 = new String[5];
        type2[0] = "10-20";
        type2[1] = "20-30";
        type2[2] = "30-40";
        type2[3] = "40-50";
        type2[4] = "50<";
        int[] value2 = new int[5];
        value2[0] = number;
        value2[1] = number1;
        value2[2] = number2;
        value2[3] = number3;
        value2[4] = number4;

        JSONObject[] pipe_data2 = new JSONObject[5];
        jsonObject8.put("value",number);
        jsonObject8.put("name","10-20");
        jsonObject10.put("value",number1);
        jsonObject10.put("name","20-30");
        jsonObject11.put("value",number2);
        jsonObject11.put("name","30-40");
        jsonObject12.put("value",number3);
        jsonObject12.put("name","40-50");
        jsonObject13.put("value",number4);
        jsonObject13.put("name","50<");
        pipe_data2[0] = jsonObject8;
        pipe_data2[1] = jsonObject10;
        pipe_data2[2] = jsonObject11;
        pipe_data2[3] = jsonObject12;
        pipe_data2[4] = jsonObject13;

        jsonObject9.put("title", "年龄分布");
        jsonObject9.put("type",type2);
        jsonObject9.put("value",value2);
        jsonObject9.put("name","人数");
        jsonObject9.put("pipe_data",pipe_data2);

        //汇总
        JSONObject[] data = new JSONObject[3];
        data[0] = jsonObject;
        data[1] = jsonObject7;
        data[2] = jsonObject9;
        jsonObject3.put("data", data);
        return jsonObject3;
    }
}
