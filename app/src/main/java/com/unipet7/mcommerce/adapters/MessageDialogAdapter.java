package com.unipet7.mcommerce.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.MessageDialog;

import java.util.Objects;

public class MessageDialogAdapter extends MessageDialog {

    private Context context;
    private Dialog dialog;

    public MessageDialogAdapter(Context context) {
        super("", "", "", "");
        this.context = context;
        this.dialog = new Dialog(context);
    }

    public void showDialog(MessageDialog messageDialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(messageDialog.isCancelable());
        dialog.setContentView(R.layout.dialogmessage);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvTitle = dialog.findViewById(R.id.tv_titel_dialog);
        TextView tvMessage = dialog.findViewById(R.id.tv_message_details_dialog);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog);
        Button btnOk = dialog.findViewById(R.id.btn_ok_dialog);

        tvTitle.setText(messageDialog.getTitle());
        if (messageDialog.getMessage() != null) {
            tvMessage.setText(messageDialog.getMessage());
        } else {
            tvMessage.setVisibility(View.GONE);
        }

        if (!messageDialog.isHasNegativeBtn()) {
            btnCancel.setVisibility(View.GONE);
        } else {
            btnCancel.setText(messageDialog.getNegativeButtonText());
            btnCancel.setOnClickListener(v -> {
                if (messageDialog.getNegativeClickListener() != null) {
                    messageDialog.getNegativeClickListener().onClick(v);
                }
                dialog.dismiss();
            });
        }

        if (!messageDialog.isHasPositiveBtn()) {
            btnOk.setVisibility(View.GONE);
        } else {
            btnOk.setText(messageDialog.getPositiveButtonText());
            btnOk.setOnClickListener(v -> {
                if (messageDialog.getPositiveClickListener() != null) {
                    messageDialog.getPositiveClickListener().onClick(v);
                }
                dialog.dismiss();
            });
        }
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
