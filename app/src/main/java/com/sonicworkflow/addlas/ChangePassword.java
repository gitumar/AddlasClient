package com.sonicworkflow.addlas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sonicworkflow.addlas.network_stuff.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by umarbradshaw on 11/5/15.
 */
public class ChangePassword extends Activity {

    private static final String TAG = ChangePassword.class.getSimpleName();
    private Button btnChangePass;
    private Button btnCancel;
    private EditText inputNewPassword;
    private String newPassWord;
    private EditText inputEmail;
    private String email;
    private static String change_password_tag = "changepassword";
    Activity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        myActivity = this;

        btnChangePass = (Button) findViewById(R.id.btnChangePass);
        btnCancel = (Button) findViewById(R.id.btntocancelchangepass);
        inputNewPassword = (EditText) findViewById(R.id.input_change_password);
        inputEmail = (EditText) findViewById(R.id.emailChangePassword);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        StartActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize forgotpassword to equal email address
                newPassWord = inputNewPassword.getText().toString();
                email = inputEmail.getText().toString();

                // Check for empty data in the form
                if (newPassWord.trim().length() > 0) {
                    // send reset password to user
                    sendChangePassword(newPassWord, email);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private void sendChangePassword(final String newPassWord, final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_password_change";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Password Reset Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Launch Login activity again
                        Intent intent = new Intent(getApplicationContext(),
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Reset Password Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", change_password_tag);
                params.put("newpass", newPassWord);
                params.put("email", email);

                return params;
            }

        };

        // Adding request to request queue
        Volley.newRequestQueue(myActivity).add(strReq);
    }
}
