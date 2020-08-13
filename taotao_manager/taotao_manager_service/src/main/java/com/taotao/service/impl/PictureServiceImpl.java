package com.taotao.service.impl;

import com.taotao.service.PictureService;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class PictureServiceImpl implements PictureService {
    //由于下面的参数 所以我们将我们的文件交给我们的spring 然后通过springmvc 帮我们将properties里面的文件全部读取完毕

    //    <context:property-placeholder location="classpath:properties/*.properties" />

    //接下来我们就是将他们拿到
    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;//ftp地址
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;//ftp端口
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;//ftp用户名
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;//ftp密码
    @Value("${FTP_ROOTPATH}")
    private String FTP_ROOTPATH;//上传到服务器的根路径
    @Value("${FTP_IMAGE_BASE_URL}")
    private String FTP_IMAGE_BASE_URL;//访问图片基础路径


    @Override
    public Map uploadPicture(MultipartFile multipartFile) {
        Map map = new HashMap();
        try {
            //这里我们做个改变 我们将我们的图片信息 全部放在我们tomcat服务器下面
            //我们获取我们图片文件的后缀名
            String oldName = multipartFile.getOriginalFilename();
            String exeName = oldName.substring(oldName.lastIndexOf("."));


            //现在我们准备我们的图片的新名字 IDUtils 是我们准备的一个工具类
            String newName = IDUtils.genImageName();
            newName = newName + exeName;
            //现在我们将我们图片上传到我们的图片服务器里面
            //里面需要很多参数  所以我们要将一些非固定的参数放在我们的配置文件里面
            String dir = new DateTime().toString("/yyyy/MM/dd");
            boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_ROOTPATH, dir, newName, multipartFile.getInputStream());
           if (!result){
               map.put("error","1" );
               map.put("message","文件上传发生异常失败" );
               return  map;
           }
            map.put("error", 0);
           //上传成功要得到一个类似这个的地址http://192.168.1.120/picture/2020/6/18/naozhong3.jpg 这样才能找到上传的图片
            map.put("url", FTP_IMAGE_BASE_URL+dir+"/"+newName);

            return map;

        } catch (IOException e) {
            e.printStackTrace();
           map.put("error","1" );
           map.put("message","文件上传发生异常失败" );

            return map;
        }

    }
}
