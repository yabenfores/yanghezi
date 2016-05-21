package com.raoleqing.yangmatou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;


import com.raoleqing.yangmatou.R;

import java.util.List;



public class ValuePickerView extends RelativeLayout {

    public static final int LEVEL_NUM_ONE = 1;
    public static final int LEVEL_NUM_TWO = 2;
    public static final int LEVEL_NUM_THREE = 3;

    private ValuePicker vp1, vp2, vp3;
    private Button mBtnConfirm, mBtnCancel;
    private OnClickSelectListener onClickSelectListener;
    // 单独一级or两级or三级
    private int levelNum;

    private int scrollState[] = {NumberPicker.OnScrollListener.SCROLL_STATE_IDLE, NumberPicker.OnScrollListener.SCROLL_STATE_IDLE,
            NumberPicker.OnScrollListener.SCROLL_STATE_IDLE};

    public ValuePickerView(Context context) {
        super(context);
        initView(context);
    }

    public ValuePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ValuePickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public interface OnClickSelectListener {
        void onConfirm(int[] selectPosition, ValueEntity[] selectValue);

        void onCancel();
    }

    public void setOnClickSelectListener(OnClickSelectListener onClickSelectListener) {
        this.onClickSelectListener = onClickSelectListener;
    }

    private void initView(Context context) {
        if (isInEditMode()) return;
        View view = LayoutInflater.from(context).inflate(R.layout.v_valuepicker, null);
        vp1 = (ValuePicker) view.findViewById(R.id.vp_um_valuepicker_1);
        vp2 = (ValuePicker) view.findViewById(R.id.vp_um_valuepicker_2);
        vp3 = (ValuePicker) view.findViewById(R.id.vp_um_valuepicker_3);
        vp1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        vp2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        vp3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        vp1.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if (scrollState != NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    vp2.setEnabled(false);
                    vp3.setEnabled(false);
                } else {
                    vp2.setEnabled(true);
                    vp3.setEnabled(true);
                }
                ValuePickerView.this.scrollState[0] = scrollState;
            }
        });
        vp2.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if (scrollState != NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    vp1.setEnabled(false);
                    vp3.setEnabled(false);
                } else {
                    vp1.setEnabled(true);
                    vp3.setEnabled(true);
                }
                ValuePickerView.this.scrollState[1] = scrollState;
            }
        });
        vp3.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if (scrollState != NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    vp1.setEnabled(false);
                    vp2.setEnabled(false);
                } else {
                    vp1.setEnabled(true);
                    vp2.setEnabled(true);
                }
                ValuePickerView.this.scrollState[2] = scrollState;
            }
        });
        mBtnConfirm = (Button) view.findViewById(R.id.btn_um_valuepicker_confirm);
        mBtnCancel = (Button) view.findViewById(R.id.btn_um_valuepicker_cancel);
        addView(view, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScrolling())
                    return;
                hide();
                if (onClickSelectListener != null) {
                    onClickSelectListener.onConfirm(getSelectedPosition(), getSelectedValue());
                }
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSelectListener != null) {
                    onClickSelectListener.onCancel();
                }
                hide();
            }
        });
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            }
        });
        hide();
    }

    public <T extends ValueEntity> void show(List<T> list, int selectPosition) {
        show(list, new int[]{selectPosition}, 1);
    }

    public <T extends ValueEntity> void show(List<T> list, int[] selectPosition, final int levelNum) {
        if (list == null || list.size() == 0) return;
        this.levelNum = levelNum;
        vp1.setList(list);
        vp1.notifyUpdate();
        int position1 = 0, position2 = 0;
        if (selectPosition != null) {
            if (selectPosition.length > 0)
                vp1.setValue(selectPosition[0]);
            if (selectPosition.length > 1)
                position1 = selectPosition[1];
            if (selectPosition.length > 2)
                position2 = selectPosition[2];
        } else {
            vp1.setValue(0);
        }
        if (levelNum < LEVEL_NUM_TWO) {
            vp2.setVisibility(GONE);
            vp3.setVisibility(GONE);
        } else {
            setChildData(vp2, vp1.getSelectedValue(), position1);
            vp1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                    if (scrollState[0] != NumberPicker.OnScrollListener.SCROLL_STATE_IDLE)
//                        return;
                    setChildData(vp2, vp1.getSelectedValue(), 0);
                    if (ValuePickerView.this.levelNum < LEVEL_NUM_THREE)
                        vp3.setVisibility(GONE);
                    else if (vp2.isShown() && vp2.getSelectedValue() != null)
                        setChildData(vp3, vp2.getSelectedValue(), 0);
                    else {
                        vp3.setData(null, 0);
                        vp3.setVisibility(VISIBLE);
                    }
                }
            });
            if (levelNum < LEVEL_NUM_THREE) {
                vp3.setVisibility(GONE);
            } else {
                setChildData(vp3, vp2.getSelectedValue(), position2);
                vp2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                        if (isScrolling())
//                            return;
                        setChildData(vp3, vp2.getSelectedValue(), 0);
                    }
                });
            }
        }
        show();
    }

    public ValueEntity[] getSelectedValue() {
        ValueEntity vpValue[] = new ValueEntity[levelNum];
        if (vp1.getSelectedValue() != null && !vp1.getSelectedValue().getValue().equals(ValuePicker.EMPTY_VALUE)) {
            vpValue[0] = vp1.getSelectedValue();
        }
        if (levelNum > 1 && vp2.getSelectedValue() != null && !vp2.getSelectedValue().getValue().equals(ValuePicker.EMPTY_VALUE)) {
            vpValue[1] = vp2.getSelectedValue();
        }
        if (levelNum > 2 && vp3.getSelectedValue() != null && !vp3.getSelectedValue().getValue().equals(ValuePicker.EMPTY_VALUE)) {
            vpValue[2] = vp3.getSelectedValue();
        }
        return vpValue;
    }

    public int[] getSelectedPosition() {
        int position[] = new int[levelNum];
        if (vp1.getSelectedValue() != null) {
            position[0] = vp1.getValue();
        }
        if (levelNum > 1 && vp2.getSelectedValue() != null) {
            position[1] = vp2.getValue();
        }
        if (levelNum > 2 && vp3.getSelectedValue() != null) {
            position[2] = vp3.getValue();
        }
        return position;
    }

    private boolean isShow = false;

    public void hide() {
        isShow = false;
        setVisibility(GONE);
    }

    public void show() {
        isShow = true;
        setVisibility(VISIBLE);
    }

    public boolean isShow() {
        return isShow;
    }

    private void setChildData(ValuePicker vp, ValueEntity parent, int position) {
        if ((vp == vp2 && levelNum < LEVEL_NUM_TWO) || (vp == vp3 && levelNum < LEVEL_NUM_THREE)) {
            vp.setVisibility(GONE);
            return;
        }
        if (parent != null && parent.getChildList() != null && parent.getChildList().size() > 0) {
            vp.setData(parent.getChildList(), position);
            vp.setVisibility(VISIBLE);
        } else {
            vp.setData(null, 0);
            vp.setVisibility(VISIBLE);
        }
    }

    private boolean isScrolling() {
        return scrollState[0] != NumberPicker.OnScrollListener.SCROLL_STATE_IDLE ||
                scrollState[1] != NumberPicker.OnScrollListener.SCROLL_STATE_IDLE ||
                scrollState[2] != NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;
    }
}
