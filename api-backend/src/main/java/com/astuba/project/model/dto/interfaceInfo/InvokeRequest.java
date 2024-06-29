package com.astuba.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用请求
 *
 * @author <a href="https://github.com/liastuba">程序员鱼皮</a>
 * @from <a href="https://astuba.icu">编程导航知识星球</a>
 */
@Data
public class InvokeRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;

}