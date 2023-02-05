package com.lc.reggie.controller;


import com.lc.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    //动态获取yml文件配置的的路径
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {//file是临时文件
    log.info("{}",file);

        //获取上传的文件的文件名
        String fileName = file.getOriginalFilename();
        //获取上传的文件的后缀名
        String hzNAme = fileName.substring(fileName.lastIndexOf("."));
        //获取uuid
        String uuid = UUID.randomUUID().toString();
        //拼接一个新的文件名
        fileName = uuid+hzNAme;

        //创建photoPath所对应的File对象
        File files = new File(basePath);
        //判断file所对应的目录是否存在
        if(!files.exists()){
            files.mkdir();
        }
        //上传文件的路径
        String finalPath = basePath+File.separator+fileName;
        //上传文件
        file.transferTo(new File(finalPath));
        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam("name") String name, HttpServletResponse response)  {
        try {
            //输入流，通过输入流读取文件内容
            File file = new File(basePath +File.separator+ name);
            FileInputStream fileInputStream = new FileInputStream(file);

            //上传的文件类型
           response.setContentType("image/jpeg");
            byte[] bytes = new byte[fileInputStream.available()];
            int read = fileInputStream.read(bytes);
            //输出流，通过输出流将文件写回浏览器，再浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes,0,read);

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
