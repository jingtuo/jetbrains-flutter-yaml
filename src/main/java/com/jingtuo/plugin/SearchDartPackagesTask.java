package com.jingtuo.plugin;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.jingtuo.plugin.model.DartPackageInfo;
import com.jingtuo.plugin.model.DartPackagesRepo;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Search Dart Packages Task
 *
 * @author JingTuo
 */
public class SearchDartPackagesTask extends Task.Backgroundable {

    @Setter
    private String text;

    @Setter
    private int page;

    @Setter
    private OnCompleteListener onCompleteListener;

    public SearchDartPackagesTask(@Nullable Project project) {
        super(project, "Search Dart Packages");
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        List<DartPackageInfo> result = DartPackagesRepo.getInstance().searchPackageInfo(text, page);
        if (onCompleteListener != null) {
            onCompleteListener.onComplete(text, page, result);
        }
    }

    /**
     *
     */
    public interface OnCompleteListener {

        /**
         * Do somethings after complete
         *
         * @param text
         * @param page
         * @param data
         */
        void onComplete(String text, int page, List<DartPackageInfo> data);
    }
}
