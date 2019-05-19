package com.jingtuo.plugin.model;

import com.intellij.openapi.diagnostic.Logger;
import com.jingtuo.plugin.service.DartPackagesService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JingTuo
 */
public class DartPackagesRepo {

    private static final String BASE_URL = "https://pub.dartlang.org/api/";
    private static final Logger LOGGER = Logger.getInstance(DartPackagesRepo.class);

    private DartPackagesService service;

    private DartPackagesRepo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DartPackagesService.class);
    }

    public static DartPackagesRepo getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DartPackagesRepo INSTANCE = new DartPackagesRepo();

        private SingletonHolder() {

        }
    }

    /**
     * @param text
     * @param page
     * @return
     */
    public List<SearchDartPackagesItem> search(String text, int page) {
        Call<SearchDartPackagesResult> call = service.search(text, page);
        try {
            Response<SearchDartPackagesResult> response = call.execute();
            if (response.isSuccessful()) {
                SearchDartPackagesResult result = response.body();
                if (result != null) {
                    return result.getPackages();
                }
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }


    /**
     * @param name
     * @return
     */
    public DartPackageInfo getPackageInfo(String name) {
        Call<DartPackageInfo> call = service.getPackageInfo(name);
        try {
            Response<DartPackageInfo> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }

    /**
     * merge{@link #search(String, int)}和{@link #getPackageInfo(String)}
     *
     * @param text
     * @param page
     * @return
     */
    public List<DartPackageInfo> searchPackageInfo(String text, int page) {
        List<SearchDartPackagesItem> items = search(text, page);
        List<DartPackageInfo> result = new ArrayList<>();
        for (SearchDartPackagesItem item : items) {
            String packageName = item.getPackageName();
            if (packageName.contains(":")) {
                /**
                 * such as：dart:io
                 */
                packageName = packageName.split(":")[1];
            }
            DartPackageInfo packageInfo = getPackageInfo(packageName);
            if (packageInfo == null) {
                packageInfo = new DartPackageInfo();
                packageInfo.setName(packageName);
            }
            result.add(packageInfo);
        }
        return result;
    }
}
