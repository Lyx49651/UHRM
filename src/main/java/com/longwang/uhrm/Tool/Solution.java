package com.longwang.uhrm.Tool;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//这个工具类用来将字符串形式的数组转化为可以读取的数组
@Service
public class Solution {
    public List<String> translate(String s){
        String[] data = s.split(",");
        List<String> k = new ArrayList<>();
        for(int i=0;i<data.length;i++){
            if(i == 0){
                k.add(data[i].split("\\[")[1]);
            }else if(i == data.length - 1){
                k.add(data[i].split("]")[0].split(" ")[1]);
            }else {
                k.add(data[i].split(" ")[1]);
            }
        }
        return  k;
    }
//    public static void main(String[] args){
//        String test = "[人事部--二级人事助理, 人事部--助理]";
//        Solution a = new Solution();
//        System.out.println(a.translate(test).get(1).length());
//    }
}
