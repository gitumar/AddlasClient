package com.sonicworkflow.addlas.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sonicworkflow.addlas.R;

/**
 * Created by umarbradshaw on 2/2/16.
 */
public class BadAudibleChannel extends Dialog implements DialogInterface.OnCancelListener,DialogInterface.OnClickListener,
        DialogInterface.OnDismissListener {

    Context mContext;
    Button okButton;
    TextView titleMessage;
    TextView messageBody;

    public BadAudibleChannel(Context context) {
        super(context);

        this.mContext= context;
        this.setContentView(R.layout.badaudibledialog);
        okButton = (Button) findViewById(R.id.acceptbadAudiblebutton);
        titleMessage =(TextView) findViewById(R.id.badAudibleMessage);
        messageBody =(TextView) findViewById(R.id.textViewBadAudibleMessage);

        messageBody.setText("Please select only one show to audible notify on your device.");

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
