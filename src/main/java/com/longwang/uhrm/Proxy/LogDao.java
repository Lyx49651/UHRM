package com.longwang.uhrm.Proxy;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;

public class LogDao  implements LogInterface{
    //输出函数名和执行的时间
    @Override
    public void time_function(String fun){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        System.out.println("时间:" + time.toString() + " 执行路由函数名为:" + fun +" 的函数.");
//        try {
//            FileOutputStream fos=new FileOutputStream("E:\\Project\\UHRM\\src\\main\\resources\\static\\log\\log.txt",true);
//            BufferedOutputStream bos=new BufferedOutputStream(fos);
//            String content="时间:" + time.toString() + " 执行路由函数名为:" + fun +" 的函数.\n";
//            bos.write(content.getBytes(),0,content.getBytes().length);
//            bos.flush();
//            bos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
