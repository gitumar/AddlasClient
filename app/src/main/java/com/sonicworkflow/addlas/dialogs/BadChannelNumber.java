package com.sonicworkflow.addlas.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sonicworkflow.addlas.R;

/**
 * Created by umarbradshaw on 9/30/15.
 */
public class BadChannelNumber extends Dialog implements DialogInterface.OnCancelListener,DialogInterface.OnClickListener,
        DialogInterface.OnDismissListener {

    Context mContext;
    Button okButton;
    TextView titleMessage;
    TextView messageBody;


    public BadChannelNumber(Context context) {
        super(context);


        this.mContext= context;

        this.setContentView(R.layout.badedittextdialog);

        okButton = (Button) findViewById(R.id.acceptbadedittext);
        titleMessage = (TextView) findViewById(R.id.badedittextmessage);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                closeOptionsMenu();
            }
        });

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}
