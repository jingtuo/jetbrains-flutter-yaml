package com.jingtuo.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NotNull;

/**
 * Search Dart Packages
 * @author JingTuo
 */
public class DartPackagesAction extends AnAction {

    private static final String YAML = "yaml";

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        StringBuffer buffer = new StringBuffer();
        Project project = e.getProject();
        if (project != null) {
            buffer.append("project name: ").append(project.getName()).append("\n");
        }
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
            Document document = editor.getDocument();
            buffer.append("document line count: ").append(document.getLineCount()).append("\n");
            SelectionModel selectionModel = editor.getSelectionModel();
            if (selectionModel.hasSelection()) {
                int start = selectionModel.getSelectionStart();
                int end = selectionModel.getSelectionEnd();
                TextRange range = new TextRange(start, end);
                buffer.append("selection: ").append(document.getText(range)).append("\n");
            }
            CaretModel caretModel = editor.getCaretModel();
            //实际位置
            LogicalPosition logicalPosition = caretModel.getLogicalPosition();
            //可视位置
            VisualPosition visualPosition = caretModel.getVisualPosition();
            //第一行line=0,光标在行首时,column=0
            buffer.append("logical cursor: ")
                    .append(logicalPosition.line)
                    .append(",")
                    .append(logicalPosition.column)
                    .append("\n");

            buffer.append("visual cursor: ")
                    .append(visualPosition.line)
                    .append(",")
                    .append(visualPosition.column)
                    .append("\n");
        }

        Navigatable navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
        if (navigatable!=null){
            buffer.append("navigatable: ").append(navigatable.toString());
        }
        Presentation presentation = e.getPresentation();
        //plugin.xml -> action->text
        String title = presentation.getText();
        Messages.showMessageDialog(project, buffer.toString(), title, Messages.getInformationIcon());
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (virtualFile != null && YAML.equals(virtualFile.getExtension())) {
            e.getPresentation().setEnabledAndVisible(true);
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }
}
