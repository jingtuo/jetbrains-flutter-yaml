package com.jingtuo.plugin.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 *
 * @author JingTuo
 */
@Data
public class DartPackageSpecInfo {

    /**
     *
     */
    private String version;

    /**
     *
     */
    private String name;

    /**
     * 如：Dart Team <misc@dartlang.org>
     */
    private String author;

    /**
     *
     */
    private String description;
    /**
     * 如：http://www.dartlang.org
     */
    @SerializedName("homepage")
    private String homePage;

    /**
     * http://api.dartlang.org/docs/pkg/json
     */
    private String documentation;

    /**
     *
     */
    private EnvironmentInfo environment;

    /**
     *
     */
    @SerializedName("dev_dependencies")
    private DependenceInfo dependence;
}
