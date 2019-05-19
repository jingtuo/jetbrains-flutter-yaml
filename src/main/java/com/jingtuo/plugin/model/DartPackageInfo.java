package com.jingtuo.plugin.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author JingTuo
 */
@Data
public class DartPackageInfo {

    /**
     * 包名
     */
    private String name;

    private DartPackageSubInfo latest;

    private ArrayList<DartPackageSubInfo> versions;
}
