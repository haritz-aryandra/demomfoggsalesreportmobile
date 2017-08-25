package id.haritz_aryandra.demomfoggdailysalesreport;

/**
 * Created by haritz-pc on 11/08/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
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

import id.haritz_aryandra.demomfoggdailysalesreport.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_login;
    EditText txt_usernama, txt_userkode;
    Intent intent;

    //String kodeuser, saleslokasi;
    String kodeasps, namaasps, saleslokasi;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "login.php";

    private static final String TAG = Login.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_KODEASPS = "kodeasps";
    public final static String TAG_NAMAASPS = "namaasps";
    public final static String TAG_SALESLOKASI = "saleslokasi";


    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        txt_usernama = (EditText) findViewById(R.id.txt_usernama);
        txt_userkode = (EditText) findViewById(R.id.txt_userkode);

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        //id = sharedpreferences.getString(TAG_ID, null);
        //kodeuser = sharedpreferences.getString(TAG_KODEUSER, null);
        kodeasps = sharedpreferences.getString(TAG_KODEASPS, null);
        namaasps = sharedpreferences.getString(TAG_NAMAASPS, null);
        saleslokasi = sharedpreferences.getString(TAG_SALESLOKASI, null);

        if (session) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            //intent.putExtra(TAG_ID, id);
            //intent.putExtra(TAG_KODEUSER, kodeuser);
            intent.putExtra(TAG_KODEASPS, kodeasps);
            intent.putExtra(TAG_NAMAASPS, namaasps);
            intent.putExtra(TAG_SALESLOKASI, saleslokasi);
            finish();
            startActivity(intent);
        }


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String usernama = txt_usernama.getText().toString();
                String userkode = txt_userkode.getText().toString();

                // mengecek kolom yang kosong
                if (usernama.trim().length() > 0 && userkode.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(usernama, userkode);
                    } else {
                        Toast.makeText(getApplicationContext() ,"Tidak Ada Koneksi Internet", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkLogin(final String usernama, final String userkode) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Login ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        //String kodeuser = jObj.getString(TAG_KODEUSER);
                        //String id = jObj.getString(TAG_ID);
                        String kodeasps = jObj.getString(TAG_KODEASPS);
                        String saleslokasi = jObj.getString(TAG_SALESLOKASI);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        //editor.putString(TAG_ID, id);
                        //editor.putString(TAG_KODEUSER, kodeuser);
                        editor.putString(TAG_KODEASPS, kodeasps);
                        editor.putString(TAG_SALESLOKASI, saleslokasi);
                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        //intent.putExtra(TAG_ID, id);
                        //intent.putExtra(TAG_KODEUSER, kodeuser);
                        intent.putExtra(TAG_KODEASPS, kodeasps);
                        intent.putExtra(TAG_SALESLOKASI, saleslokasi);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("usernama", usernama);
                params.put("userkode", userkode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
