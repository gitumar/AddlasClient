package com.sonicworkflow.addlas.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sonicworkflow.addlas.MySharedPreferences;
import com.sonicworkflow.addlas.R;

/**
 * Created by umarbradshaw on 1/24/16.
 */
public class BackupChannelDialog extends Dialog implements DialogInterface.OnCancelListener,DialogInterface.OnClickListener,
        DialogInterface.OnDismissListener {

    Context mContext;
    Button okButton;
    Button cancelButton;
    TextView titleMessage;
    TextView messageBody;
    EditText backupChannelEditText;


    public BackupChannelDialog(Context context) {
        super(context);

        this.mContext= context;
        this.setContentView(R.layout.backupchanneldialog);
        okButton = (Button) findViewById(R.id.acceptbackupChannelbutton);
        cancelButton = (Button) findViewById(R.id.cancelBackupChannelbutton);
        titleMessage =(TextView) findViewById(R.id.backupChannelMessage);
        messageBody =(TextView) findViewById(R.id.textViewbackupChannelMessage);
        backupChannelEditText = (EditText) findViewById(R.id.backupChannelEditText);

        backupChannelEditText.setText(MySharedPreferences.readString(mContext, MySharedPreferences.BACKUP_CHANNEL,null));
        messageBody.setText("Please enter a backup channel.");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!backupChannelEditText.equals(null)){
                    //Store user info in SharedPreferences
                    MySharedPreferences.writeString(mContext, MySharedPreferences.BACKUP_CHANNEL, String.valueOf(backupChannelEditText.getText()));
                }

                dismiss();
                closeOptionsMenu();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
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