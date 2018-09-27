package com.java.contorller;

import com.java.service.TestImpalaJdbc41;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Author: Boris
 * Date: 2018/7/30 12:47
 * Copyright (C), 2017-2018,
 * Description:
 */
@RestController
public class TestController {

    @RequestMapping("/test/test")
    public String test(){
        return "this is a web test";
    }

    public static void main(String[] args) {
        System.out.println("ALTER TABLE user_category MODIFY COLUMN category_id INT(11) NOT NULL UNSIGNED ZEROFILL AUTO_INCREMENT COMMENT '用户分组id';".substring(77));
    }
    @RequestMapping("/executeShell")
    public String executeShell() throws Exception{
        String path ="/home/boris/test/test.sh";
        String[] param = {"haha","222"};
        String[] cmd = new String[]{path};
        cmd = ArrayUtils.addAll(cmd, param);
        ProcessBuilder pb = new ProcessBuilder("/bin/chmod", "755", path);
        pb.start();

        Process ps = Runtime.getRuntime().exec(cmd);
        ps.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line = br.readLine()) != null){
            sb.append(line).append("\n");
        }

        String result = sb.toString();
                return result;
    }

    @RequestMapping("/testQueryImpala")
    public String testQueryImpala(){
        return TestImpalaJdbc41.queryImpala();
    }


}
