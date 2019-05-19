package com.jingtuo.plugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.util.NotNullFunction;
import com.jingtuo.plugin.listener.BaseKeyListener;
import com.jingtuo.plugin.listener.BaseMouseListener;
import com.jingtuo.plugin.model.DartPackageInfo;
import com.jingtuo.plugin.model.DartPackageSpecInfo;
import com.jingtuo.plugin.model.ListDartPackageModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * 搜索Dart Packages的对话框
 *
 * @author JingTuo
 */
public class SearchDartPackagesDialog extends DialogWrapper implements SearchDartPackagesTask.OnCompleteListener {

    private static final int SIZE = 10;

    private Project project;

    private JBList<DartPackageInfo> jbList;

    private int currentPage = 1;

    private JBTextField searchEdit;

    private int searchPage = 1;

    private SearchDartPackagesTask searchDartPackagesTask;

    public SearchDartPackagesDialog(@Nullable Project project) {
        super(project);
        this.project = project;
        searchDartPackagesTask = new SearchDartPackagesTask(this.project);
        searchDartPackagesTask.setOnCompleteListener(this);
        addKeyListener(new BaseKeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    return;
                }
            }
        });
        init();
        setTitle("Search Dart Packages");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new VerticalLayout(5, SwingConstants.LEFT));
        //搜索输入框及按钮
        JPanel searchContainer = new JPanel(new HorizontalLayout(5, SwingConstants.CENTER));
        searchEdit = new JBTextField();
        searchEdit.setPreferredSize(new Dimension(400, 32));
        searchEdit.setToolTipText("please enter package name");
        searchEdit.setName("package name");
        searchEdit.setTextToTriggerEmptyTextStatus("package name, such as: json");
        searchEdit.requestFocus();
        searchEdit.addKeyListener(new BaseKeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    //执行搜索
                    search(searchEdit.getText(), 1);
                }
            }
        });
        searchContainer.add(searchEdit);

        JButton jButton = new JButton(AllIcons.Actions.Search);
        jButton.setPreferredSize(new Dimension(32, 32));
        jButton.addMouseListener(new BaseMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                search(searchEdit.getText(), 1);
            }
        });
        searchContainer.add(jButton);
        dialogPanel.add(searchContainer);
        //搜索结果
        JBScrollPane scrollPane = new JBScrollPane();
        scrollPane.setPreferredSize(new Dimension(432, 180));
        jbList = new JBList<>();
        jbList.installCellRenderer((NotNullFunction<DartPackageInfo, JComponent>) dom -> {
            JPanel jPanel = new JPanel(new VerticalLayout(5));
            if (dom != null && dom.getLatest() != null) {
                DartPackageSpecInfo specInfo = dom.getLatest().getPubspec();
                JBLabel nameAndVersion = new JBLabel(specInfo.getName() + ":" + specInfo.getVersion());
                nameAndVersion.setToolTipText(specInfo.getDescription());
                jPanel.add(nameAndVersion);
            }
            return jPanel;
        });
        scrollPane.setViewportView(jbList);
        dialogPanel.add(scrollPane);
        searchPage = 1;
        search("", searchPage);
        return dialogPanel;
    }

    /**
     * @param page
     */
    private void search(String text, int page) {
        searchDartPackagesTask.setText(text);
        searchDartPackagesTask.setPage(page);
        searchDartPackagesTask.queue();
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[]{
                new PreviousPageAction(), new NextPageAction(),
                getOKAction(), getCancelAction()
        };
    }

    @Override
    public void onComplete(String text, int page, List<DartPackageInfo> data) {
        if (!searchEdit.getText().equals(text) || searchPage != page) {
            //search text has changed
            return;
        }
        ListDartPackageModel dartPackageModel = new ListDartPackageModel();
        dartPackageModel.setData(data);
        jbList.setModel(dartPackageModel);
        currentPage = page;
    }

    protected class PreviousPageAction extends AbstractAction {

        PreviousPageAction() {
            super("previous");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!checkData(true, currentPage)) {
                return;
            }
            searchPage = currentPage - 1;
            search(searchEdit.getText(), searchPage);
        }
    }

    protected class NextPageAction extends AbstractAction {

        NextPageAction() {
            super("next");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!checkData(false, currentPage)) {
                return;
            }
            searchPage = currentPage + 1;
            search(searchEdit.getText(), searchPage);
        }
    }

    /**
     * @param previous
     * @param page
     * @return
     */
    private boolean checkData(boolean previous, int page) {
        if (previous) {
            if (page == 1) {
                return false;
            }
            return true;
        }
        if (jbList.getModel().getSize() < SIZE) {
            return false;
        }
        return true;
    }

    public DartPackageInfo getSelected() {
        return jbList.getSelectedValue();
    }

}
