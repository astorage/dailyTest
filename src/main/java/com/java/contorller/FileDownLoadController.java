package com.java.contorller;

import com.java.filedownload.FileCsvDownLoad;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Author: Boris
 * Date: 2018/11/21 15:34
 * Description:
 */
@RestController
public class FileDownLoadController {

    /**
     * 内存集合数据写入通过csv工具下载
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/file/download")
    public String download(HttpServletResponse response) throws Exception{
        FileCsvDownLoad.doExport(response);

        return "this is a web test";
    }

    /**
     * 下载本地文件
     * @param response
     * @throws Exception
     */
    @RequestMapping("/file/download1")
    public void download1(HttpServletResponse response) throws Exception{
        FileCsvDownLoad.downloadFile(response);
    }


    @RequestMapping("/file/download2")
    public void download2(HttpServletResponse response) throws Exception{
        FileCsvDownLoad.downloadRemoteFile(response);
    }

    /**
     * 下载远程linux上的文件
     * @param response
     * @throws Exception
     */
    @RequestMapping("/file/download3")
    public void download3(HttpServletResponse response) throws Exception{
        FileCsvDownLoad.downloadRemoteLinuxFile(response);
    }

}
