package com.unipet7.mcommerce.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.MessageDialog;

import java.util.Objects;

public class MessageDialogAdapter extends MessageDialog {

    Context context;

    Dialog dialog;

    public MessageDialogAdapter(Context ccontext,String title, String message, String positiveButtonText, String negativeButtonText) {
        super(title, message, positiveButtonText, negativeButtonText);
        this.context = ccontext;
    }

    public void startDialog() {
        // Start dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogmessage);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        // Set dialog title
        TextView tvTitle = dialog.findViewById(R.id.tv_titel_dialog);
        TextView tvMessage = dialog.findViewById(R.id.tv_message_details_dialog);
        Button btnPositive = dialog.findViewById(R.id.btn_ok_dialog);
        Button btnNegative = dialog.findViewById(R.id.btn_cancel_dialog);


        tvTitle.setText(getTitle());
        tvMessage.setText(getMessage());

        if (isHasNegativeBtn()) {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setText(getPositiveButtonText());
        } else {
            btnPositive.setVisibility(View.GONE);
        }

        if (isHasPositiveBtn()) {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setText(getNegativeButtonText());
        } else {
            btnNegative.setVisibility(View.GONE);
        }

        dialog.setCancelable(isCancelable());
        dialog.show();
        Log.d("Dialog", "Dialog started");
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    public void messageDialogPositiveBtnListener(View.OnClickListener listener) {
        Button btnPositive = dialog.findViewById(R.id.btn_ok_dialog);
        btnPositive.setOnClickListener(listener);
        dismissDialog();
    }

    public void messageDialogNegativeBtnListener() {
        Button btnNegative = dialog.findViewById(R.id.btn_cancel_dialog);
        btnNegative.setOnClickListener(v -> dialog.dismiss());
    }
}
