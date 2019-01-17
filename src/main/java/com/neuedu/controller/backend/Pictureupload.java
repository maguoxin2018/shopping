package com.neuedu.controller.backend;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.IProductservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping(value ="/manage/product")
public class Pictureupload {

    @Autowired
    IProductservice iProductservice;

    //   通过浏览器访问
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String upload(){
        return "Pictureupload";
    }
    //   通过页面访问
    @RequestMapping(value = "/upload",method = RequestMethod.POST )
    @ResponseBody
    public ServerResponse upload1(@RequestParam(value = "upload_file",required = false)MultipartFile file){
        String path = "F:\\photo";
        return iProductservice.upload(file,path);
    }

}
