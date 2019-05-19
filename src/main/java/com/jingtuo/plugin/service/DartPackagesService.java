package com.jingtuo.plugin.service;

import com.jingtuo.plugin.model.DartPackageInfo;
import com.jingtuo.plugin.model.SearchDartPackagesResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Dart Packages Service
 * @author JingTuo
 */
public interface DartPackagesService {

    /**
     * Search Dart Packages
     * @param text
     * @param page
     * @return
     */
    @GET("search")
    Call<SearchDartPackagesResult> search(@Query("q") String text, @Query("page") int page);

    @GET("packages/{name}")
    Call<DartPackageInfo> getPackageInfo(@Path("name") String name);
}
