package com.jingtuo.plugin.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author JingTuo
 */
@Data
public class DartPackageSubInfo {

    /**
     * 下载地址
     */
    @SerializedName("archive_url")
    private String archiveUrl;

    /**
     * 如：0.10.0
     */
    private String version;

    /**
     *
     */
    private DartPackageSpecInfo pubspec;
}
