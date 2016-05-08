package com.raoleqing.yangmatou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/8.
 */
public class ValuePicker extends NumberPicker {
    public static final String EMPTY_VALUE = "-";
    public static final String EMPTY_KEY = "0";

    public ValuePicker(Context context) {
        super(context);
    }

    public ValuePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValuePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    List<ValueEntity> list;
    String[] displayVal;

    public <T extends ValueEntity> void setData(List<T> list, int position) {
        this.list = new ArrayList<ValueEntity>();
        if (list != null && list.size() > 0) {
            for (T entity : list) {
                this.list.add(entity);
            }
        }
        if (this.list.size() == 0) {
            this.list.add(new ValueEntity() {
                @Override
                public String getKey() {
                    return EMPTY_KEY;
                }

                @Override
                public String getValue() {
                    return EMPTY_VALUE;
                }

                @Override
                public Object getObject() {
                    return null;
                }

                @Override
                public void setKey(Object key) {

                }

                @Override
                public void setValue(Object value) {

                }

                @Override
                public void setObject(Object obj) {

                }

                @Override
                public List getChildList() {
                    return null;
                }
            });
        }
        this.setDisplayedValues(null);
        displayVal = new String[this.list.size()];
        for (int i = 0, length = this.list.size(); i < length; i++) {
            displayVal[i] = this.list.get(i).getValue();
        }
        this.setMinValue(0);
        this.setMaxValue(this.list.size() - 1);
        if (position < 0) position = 0;
        this.setValue(position);
        this.setDisplayedValues(displayVal);
        this.setWrapSelectorWheel(true);
    }

    public ValueEntity getSelectedValue() {
        if (list == null || list.size() == 0) return null;
        return list.get(this.getValue());
    }

    public <T extends ValueEntity> void setList(List<T> list) {
        if (list == null || list.size() == 0) return;
        this.list = new ArrayList<ValueEntity>();
        for (T entity : list) {
            this.list.add(entity);
        }
    }

    public ValueEntity getValueEntity(int position) {
        if (list == null || list.size() == 0 || position > list.size() - 1) return null;
        return list.get(position);
    }

    public void notifyUpdate() {
        setData(list, 0);
    }


}
