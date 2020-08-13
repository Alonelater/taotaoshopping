package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface PictureService {
    Map uploadPicture(MultipartFile multipartFile);
}
