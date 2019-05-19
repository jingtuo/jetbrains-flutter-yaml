package com.jingtuo.plugin.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author JingTuo
 */
@Data
public class DependenceInfo {

    /**
     * 如：>=0.9.0 <0.11.0"
     */
    @SerializedName("unittest")
    private String unitTest;
}
