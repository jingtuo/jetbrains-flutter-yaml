package com.jingtuo.plugin.model;


import lombok.Data;

/**
 * @author JingTuo
 */
@Data
public class EnvironmentInfo {
    /**
     * 如：>=1.2.0 <2.0.0
     */
    private String sdk;
}
