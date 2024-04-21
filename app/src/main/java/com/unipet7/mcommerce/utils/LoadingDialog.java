package com.unipet7.mcommerce.utils;

import android.app.Dialog;
import android.content.Context;

import com.unipet7.mcommerce.R;

import java.util.Objects;

public class LoadingDialog {

    Dialog dialog;
    public void showLoadingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void dissmis() {
        dialog.dismiss();
    }
}
