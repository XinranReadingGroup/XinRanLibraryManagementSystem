package com.xinran.vo;

import java.util.List;

import lombok.Data;

/**
 * @author 高海军 帝奇 Jul 5, 2015 3:29:44 PM
 */

@Data
public class LocationMeta {

    private Long   id;
    private String name;

    private List<LocationMeta> sub;
}
