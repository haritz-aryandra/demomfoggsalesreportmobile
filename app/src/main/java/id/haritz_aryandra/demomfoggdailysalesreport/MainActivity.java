package id.haritz_aryandra.demomfoggdailysalesreport;

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
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    Button btn_logout, btn_news_feed, btn_photos;
    TextView txt_kodeuser, txt_saleslokasi;
    //String kodeuser, saleslokasi;
    String kodeasps, namaasps, saleslokasi;
    SharedPreferences sharedpreferences;

    //public static final String TAG_KODEUSER = "kodeuser";
    public static final String TAG_KODEASPS = "kodeasps";
    public static final String TAG_NAMAASPS = "namaasps";
    public static final String TAG_SALESLOKASI = "saleslokasi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_kodeuser = (TextView) findViewById(R.id.txt_kodeuser);
        txt_saleslokasi = (TextView) findViewById(R.id.txt_saleslokasi);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_photos = (Button) findViewById(R.id.btn_photos);
        btn_news_feed = (Button) findViewById(R.id.btn_news_feed);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        //kodeuser = getIntent().getStringExtra(TAG_KODEUSER);
        kodeasps = getIntent().getStringExtra(TAG_KODEASPS);
        saleslokasi = getIntent().getStringExtra(TAG_SALESLOKASI);

        txt_kodeuser.setText("Nama ASPS : " + kodeasps);
        txt_saleslokasi.setText("Branch Asps : " + saleslokasi);

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                //editor.putString(TAG_ID, null);
                editor.putString(TAG_KODEASPS, null);
                editor.putString(TAG_NAMAASPS, null);
                editor.putString(TAG_SALESLOKASI, null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        btn_news_feed.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, DailySalesReportActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_photos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                //editor.putString(TAG_ID, null);
                //editor.putString(TAG_KODEUSER, null);
                editor.putString(TAG_KODEASPS, null);
                editor.putString(TAG_NAMAASPS, null);
                editor.putString(TAG_SALESLOKASI, null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, Login.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
