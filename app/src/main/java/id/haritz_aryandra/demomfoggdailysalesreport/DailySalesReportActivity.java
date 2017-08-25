package id.haritz_aryandra.demomfoggdailysalesreport;

/**
 * Created by haritz-pc on 12/08/2017.
 */

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog;

import android.util.Log;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.EditText;

import android.view.View;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import id.haritz_aryandra.demomfoggdailysalesreport.app.AppController;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DailySalesReportActivity extends AppCompatActivity implements OnItemSelectedListener {
    ProgressDialog pDialog;
    Button button, button1;
    EditText edt_namatoko, edt_date;
    TextView txt_areasales;
    Spinner sp1, sp2, sp3;

    String areasalesid, areaspgid, absenspgid, namatoko, tglinput;

    DatePickerDialog datePickerDialog;

    int success;
    ConnectivityManager conMgr;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    Intent intent;

    private static final String TAG = MainActivity.class.getSimpleName();

    private static String url_insert 	        = Server.URL + "inputmastersalesreport.php";

    private static final String TAG_SUCCESS     = "success";
    private static final String TAG_MESSAGE     = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailysalesreport);

        button = (Button)findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        sp1 = (Spinner)findViewById(R.id.spinner_namaspg);
        sp2 = (Spinner)findViewById(R.id.spinner_namatoko);
        sp3 = (Spinner)findViewById(R.id.spinner_absensi);
        //edt_namatoko = (EditText)findViewById(R.id.edt_namatoko);
        edt_date = (EditText)findViewById(R.id.edt_date);
        txt_areasales = (TextView) findViewById(R.id.txt_areasales);
        sp1.setOnItemSelectedListener(this);

        // perform click event on edit text for the tanggal need
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(DailySalesReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                edt_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        button1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent = new Intent(DailySalesReportActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3){
        // TODO Auto-generated method stub
        String sap1= String.valueOf(sp1.getSelectedItem());
        Toast.makeText(this, "Area Sales Anda :" + sap1, Toast.LENGTH_SHORT).show();
        if(sap1.contentEquals("Jakarta")) {
            List<String> list = new ArrayList<String>();
            list.add("Kurniati");
            list.add("Eka Ernawati");
            list.add("Ayu Safitri");
            list.add("Tri");
            list.add("Nurhasanah");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter);
        }
        if(sap1.contentEquals("Palembang")) {
            List<String> list = new ArrayList<String>();
            list.add("Desi");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter2);
        }
        if(sap1.contentEquals("Samarinda")) {
            List<String> list = new ArrayList<String>();
            list.add("Rahmona");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter2);
        }
        if(sap1.contentEquals("Semarang")) {
            List<String> list = new ArrayList<String>();
            list.add("Novita");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter2);
        }
        if(sap1.contentEquals("Pontianak")) {
            List<String> list = new ArrayList<String>();
            list.add("Dilla");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter2);
        }
        if(sap1.contentEquals("Medan")) {
            List<String> list = new ArrayList<String>();
            list.add("Widya");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter2);
        }
        if(sap1.contentEquals("Jawa Barat")) {
            List<String> list = new ArrayList<String>();
            list.add("Rahayu");
            list.add("Yulianti");
            list.add("Ria");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
