package com.tedu.jsoup.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//忽略未知字段 保证数据获取正常
@JsonIgnoreProperties(ignoreUnknown=true)
public class Section {
    private Integer sectionId;//该属性是为了主外键约束

    private String singuptype;

    private Integer singupnum;

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSinguptype() {
        return singuptype;
    }

    public void setSinguptype(String singuptype) {
        this.singuptype = singuptype;
    }

    public Integer getSingupnum() {
        return singupnum;
    }

    public void setSingupnum(Integer singupnum) {
        this.singupnum = singupnum;
    }
}