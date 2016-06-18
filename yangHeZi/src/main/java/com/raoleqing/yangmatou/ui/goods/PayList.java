package com.raoleqing.yangmatou.ui.goods;

/**
 * Created by ybin on 2016/6/6.
 */
public class PayList {
    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    private int payment_id;
    private String payment_name;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    private boolean select=false;

    public String getPayment_ico() {
        return payment_ico;
    }

    public void setPayment_ico(String payment_ico) {
        this.payment_ico = payment_ico;
    }

    private String payment_ico;
}
