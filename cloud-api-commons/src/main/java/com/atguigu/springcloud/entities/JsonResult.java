package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public JsonResult(Integer code,String msg){
        this(code,msg,null);
    }
}
