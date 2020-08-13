package com.taotao.controller;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class FtpTest {

    //这两天为了搭建一个图片服务器真的是心累  现在我们简单测试一下 看看图片服务器有没有成功 成功就用
    // 不成功就不用 还是用原来传到tomcat目录下好了

    public static void main(String[] args) throws IOException {
        //1.创建一个ftp服务器
        FTPClient ftpClient = new FTPClient();
        //创建连接
        ftpClient.connect("192.168.182.129",21);
        //登录服务器
        ftpClient.login("ftpuser","ftpuser" );
        ftpClient.changeWorkingDirectory("/home/ftpuser/images");
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //上传文件
        //第一个参数表示上传到服务器的名字  第二个表示文件的流对象
        FileInputStream inputStream = new FileInputStream(new File("D:\\电脑壁纸\\like.jpg"));
        ftpClient.storeFile("2.jpg",inputStream );
        ftpClient.logout();



    }
}
