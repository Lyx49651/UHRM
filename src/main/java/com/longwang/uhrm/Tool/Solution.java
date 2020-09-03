package com.longwang.uhrm.Tool;

public class Solution {
    //1
    public Boolean get_result(int num){

        if(num % 2 == 0){
            return false;
        }else {
            return true;
        }
    }
    //2
    private Boolean get(int num){
        if(0<= num && num<= 225){
            return true;
        }else {
            return false;
        }
    }
    public Boolean vaildIPAddress(String IP){
        int k;
        String[] a = IP.split(".");
        for (String h:a){
            System.out.println(h);
        }
        for(String temp:a){
            if(get_result(Integer.parseInt(temp.substring(0, 1)))){

            }else {
                return false;
            }
            if (get(Integer.parseInt(temp))){
                }else {
                    return false;
                }
        }
        return true;
    }
    public static void main(String[] args){
        String test = "172.316.254.1";
        Solution demo = new Solution();
        Solution demo1 = new Solution();
        System.out.println(demo1.vaildIPAddress(test));
    }
}
