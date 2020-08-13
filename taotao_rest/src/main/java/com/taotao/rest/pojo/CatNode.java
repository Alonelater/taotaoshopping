package com.taotao.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CatNode {
    /**
     * 这个就是为了展示商品导航  商品导航分为好几项
     * 创建这个pojo就是为了将我们的从数据库里面的数据转换成json数据 然后方便前台的js代码帮我们展示
     * 一步步来  有点难懂
     */
//    json数据里面有三层 分别是u,表示大分类 所以里面有链接，名字包含
//    n表示大分类里面的二级分类，里面有连接，名字
//    i表示最小分类项
//    但是我们这样封装好的数据是不会被前端js代码识别的 因为他不认识name,url 只认识u,n,i
//    所以引用下面的注解 只是在转换json数据起作用
    @JsonProperty("n")
    private String name;

    @JsonProperty("u")
    private String url;
    @JsonProperty("i")
    private List<?> item;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<?> getItem() {
        return item;
    }

    public void setItem(List<?> item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
