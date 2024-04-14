package com.unipet7.mcommerce.models;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.unipet7.mcommerce.R;

public class MessageDialog {

    public boolean cancelable = true;
    public boolean hasPositiveBtn = true;
    public boolean hasNegativeBtn = true;

    String title, message, positiveButtonText, negativeButtonText;

    public MessageDialog(String title, String message, String positiveButtonText, String negativeButtonText) {
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public boolean isHasPositiveBtn() {
        return hasPositiveBtn;
    }

    public void setHasPositiveBtn(boolean hasPositiveBtn) {
        this.hasPositiveBtn = hasPositiveBtn;
    }

    public boolean isHasNegativeBtn() {
        return hasNegativeBtn;
    }

    public void setHasNegativeBtn(boolean hasNegativeBtn) {
        this.hasNegativeBtn = hasNegativeBtn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    private View.OnClickListener negativeClickListener;
    private View.OnClickListener positiveClickListener;

    public void setNegativeClickListener(View.OnClickListener listener) {
        this.negativeClickListener = listener;
    }

    public void setPositiveClickListener(View.OnClickListener listener) {
        this.positiveClickListener = listener;
    }

    public View.OnClickListener getNegativeClickListener() {
        return negativeClickListener;
    }

    public View.OnClickListener getPositiveClickListener() {
        return positiveClickListener;
    }
}
