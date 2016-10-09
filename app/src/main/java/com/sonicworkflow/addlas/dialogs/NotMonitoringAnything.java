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
public class NotMonitoringAnything extends Dialog implements DialogInterface.OnCancelListener,DialogInterface.OnClickListener,
        DialogInterface.OnDismissListener {

    Context mContext;
    Button okButton;
    TextView titleMessage;
    TextView messageBody;

    public NotMonitoringAnything(Context context) {
        super(context);

        this.mContext= context;

        this.setContentView(R.layout.notmonitoringanythingdialog);

        okButton = (Button) findViewById(R.id.acceptnotmonitoring);
        titleMessage = (TextView) findViewById(R.id.notmonitoringmessage);
        messageBody =(TextView) findViewById(R.id.monitorMessageBody);
        messageBody.setText("Please select the channels you wish to monitor.");


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
