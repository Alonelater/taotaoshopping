package com.taotao.sso.service;


import com.taotao.pojo.TbUser;
import com.taotao.utils.TaotaoResult;

public interface UserService {
    TaotaoResult checkData(String content,Integer type);
    TaotaoResult createUser(TbUser user);
    TaotaoResult userLogin(String username,String password);
    TaotaoResult getUserByToken(String token);
}
