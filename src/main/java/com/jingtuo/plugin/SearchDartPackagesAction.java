package com.jingtuo.plugin;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Search Dart Packages
 * plugin.xml -> action->presentation.text
 * @author JingTuo
 */
public class SearchDartPackagesAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        SearchDartPackagesDialog dialog = new SearchDartPackagesDialog(project);
        dialog.show();
    }
}
