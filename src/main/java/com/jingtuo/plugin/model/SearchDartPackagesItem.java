package com.jingtuo.plugin.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author JingTuo
 */
@Data
public class SearchDartPackagesItem {

    @SerializedName("package")
    private String packageName;
}
