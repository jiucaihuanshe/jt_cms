package com.tedu.jsoup.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//忽略未知字段 保证数据获取正常
@JsonIgnoreProperties(ignoreUnknown=true)
public class Book {
    private Integer studentId;

    private Integer code;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}