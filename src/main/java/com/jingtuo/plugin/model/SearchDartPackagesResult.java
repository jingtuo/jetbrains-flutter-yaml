package com.jingtuo.plugin.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author JingTuo
 */
@Data
public class SearchDartPackagesResult {
    private ArrayList<SearchDartPackagesItem> packages;
}
