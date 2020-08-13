package com.taotao.sso.service.impl;

import com.github.pagehelper.StringUtil;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;


    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${REDIS_USER_SESSION_EXPIRE}")
    private Integer REDIS_USER_SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String content, Integer type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (1 == type) {
            criteria.andUsernameEqualTo(content);
        } else if (2 == type) {
            criteria.andPhoneEqualTo(content);
        } else {
            criteria.andEmailEqualTo(content);
        }
        List<TbUser> list = userMapper.selectByExample(example);
        if (null == list || list.size() == 0) {

            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //给密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password) {

            //查询用户
            TbUserExample example = new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(username);
            List<TbUser> list = userMapper.selectByExample(example);
            if (null==list||list.size()==0){
                return TaotaoResult.build(400,"用户名或密码错误");
            }
            //比对密码
            TbUser tbUser = list.get(0);
            if (!tbUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
                return TaotaoResult.build(400,"用户名或密码错误");
            }
            //设置令牌
            String token = UUID.randomUUID().toString();
            //为了安全 清除用户的密码
            tbUser.setPassword(null);
        try {
            //存入redis
            jedisClient.set(REDIS_USER_SESSION_KEY+":"+token, JsonUtils.objectToJson(tbUser));
            jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,REDIS_USER_SESSION_EXPIRE);

        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok(token);

    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        if (StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"此身份登录已经过期，请重新登录");

        }
        //重新更新token的过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,REDIS_USER_SESSION_EXPIRE);
        return TaotaoResult.ok(JsonUtils.jsonToPojo(json,TbUser.class));

    }
}
