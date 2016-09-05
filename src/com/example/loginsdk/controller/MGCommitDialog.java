package com.example.loginsdk.controller;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.example.loginsdk.util.ResUtils;

/**
 * Created by mitnick.cheng on 2016/9/1.
 */

public class MGCommitDialog extends Dialog {

    public MGCommitDialog(Context context) {
        super(context,ResUtils.getStyle("YYHProgressDialog"));
        int var2 = ResUtils.getValue("layout", "yyh_dialog_login");
        this.setContentView(var2);
        this.setCanceledOnTouchOutside(false);
    }

    public MGCommitDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public MGCommitDialog setMessage(String var1) {
        ((TextView)this.findViewById(ResUtils.getValue("id", "yyh_dialog_msg"))).setText(var1);
        return this;
    }
}
