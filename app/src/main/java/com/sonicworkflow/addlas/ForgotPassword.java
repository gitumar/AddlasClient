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
 * Created by umarbradshaw on 11/4/15.
 */
public class ForgotPassword extends Activity {

    private static final String TAG = ForgotPassword.class.getSimpleName();
    private Button btnReset;
    private Button btnCancel;
    private EditText inputEmail;
    private String email;
    private static String forgotpass_tag = "forgotpass";
    Activity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        myActivity = this;

        btnReset = (Button) findViewById(R.id.btnResetPass);
        btnCancel = (Button) findViewById(R.id.btntocancelforgotpass);
        inputEmail = (EditText) findViewById(R.id.emailReset);

        // Reset button Click Event
        btnReset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //initialize forgotpassword to equal email address
                email = inputEmail.getText().toString();

                // Check for empty data in the form
                if (email.trim().length() > 0) {
                    // send reset password to user
                    sendPassReset(email);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter your email address!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Forgot Password Screen
        btnCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    private void sendPassReset(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_password_reset";


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
                params.put("tag", "forgotpass");
                params.put("forgotpassword", email);

                return params;
            }

        };

        // Adding request to request queue
        Volley.newRequestQueue(myActivity).add(strReq);
    }
}
