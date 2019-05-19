package com.jingtuo.plugin.model;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * @author JingTuo
 */
public class ListDartPackageModel implements ListModel<DartPackageInfo> {

    @Setter
    @Getter
    private java.util.List<DartPackageInfo> data;

    @Override
    public int getSize() {
        return data == null ? 0 : data.size();
    }

    @Override
    public DartPackageInfo getElementAt(int index) {
        return data.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
