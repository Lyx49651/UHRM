package com.longwang.uhrm.Tool;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//这个工具类用来将字符串形式的数组转化为可以读取的数组
@Service
public class Solution {
    public List<String> translate(String s){
        String[] data = s.split(",");
        //System.out.println(Arrays.toString(data));
        List<String> k = new ArrayList<>();
        if(data.length == 0) return k;
        for(int i=0;i<data.length;i++){
            if(i == 0){

                if(data.length == 1){
                    k.add(data[i].split("\\[")[1].split("]")[0]);
                }else {
                    k.add(data[i].split("\\[")[1]);
                }
            }else if(i == data.length - 1){
                k.add(data[i].split("]")[0].split(" ")[1]);
            }else {
                k.add(data[i].split(" ")[1]);
            }
        }
        return  k;
    }
    public static void main(String[] args){
        String test = "[ 杨馨琪]";
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String[] a = time.toString().split(" ")[0].split("-");
        a[0] = String.valueOf(Integer.parseInt(a[0]) - 21);
        Solution t = new Solution();
        System.out.println(t.translate(test));
    }
}
