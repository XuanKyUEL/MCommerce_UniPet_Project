package com.unipet7.mcommerce.utils;

import android.app.Dialog;
import android.content.Context;

import com.airbnb.lottie.LottieAnimationView;
import com.unipet7.mcommerce.R;

import java.util.Objects;

public class LoadingDialog {

    Dialog dialog;
    LottieAnimationView lvLoading;
    public void showLoadingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading_dialog);
        lvLoading = dialog.findViewById(R.id.lav_loading);
        lvLoading.playAnimation();
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void dissmis() {
        if (dialog != null) {
            lvLoading.cancelAnimation();
            dialog.dismiss();
        }
    }
}
