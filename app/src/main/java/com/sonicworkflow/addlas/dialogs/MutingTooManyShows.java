package com.sonicworkflow.addlas.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sonicworkflow.addlas.R;

/**
 * Created by umarbradshaw on 12/10/15.
 */
public class MutingTooManyShows extends Dialog implements DialogInterface.OnCancelListener,DialogInterface.OnClickListener,
        DialogInterface.OnDismissListener {

    Context mContext;
    Button okButton;
    TextView titleMessage;
    TextView messageBody;

    public MutingTooManyShows(Context context) {
        super(context);

        this.mContext= context;
        this.setContentView(R.layout.badmutedialog);
        okButton = (Button) findViewById(R.id.acceptbadMutebutton);
        titleMessage =(TextView) findViewById(R.id.badMuteMessage);
        messageBody =(TextView) findViewById(R.id.textViewBadMuteMessage);

        messageBody.setText("Please select only one show to Mute your device when on commercial.");

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
