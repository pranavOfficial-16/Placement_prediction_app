package com.example.placementprediction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText cgpa,iq,profile_score;
    Button btn;
    TextView output;
    String URL = "https://student-placement-webapp.herokuapp.com/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cgpa = findViewById(R.id.input_cgpa);
        iq = findViewById(R.id.input_iq);
        profile_score = findViewById(R.id.input_profile_score);
        btn = findViewById(R.id.predict);
        output = findViewById(R.id.result);

        btn.setOnClickListener(v -> {
            @SuppressLint("SetTextI18n")
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("placement");
                    if(data.equals("1")){
                        output.setText("Placement Hoga");
                    }else{
                        output.setText("Placement Nahi Hoga");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(MainActivity.this, "There is an error", Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("cgpa", cgpa.getText().toString());
                    params.put("iq", iq.getText().toString());
                    params.put("profile_score", profile_score.getText().toString());
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(stringRequest);
        });
    }
}
