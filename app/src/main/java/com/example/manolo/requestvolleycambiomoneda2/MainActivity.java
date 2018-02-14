package com.example.manolo.requestvolleycambiomoneda2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tv1, tvCantidadAlCambio;
    EditText etCantidadOriginal;
    double cambio;

    private static final String URL="https://openexchangerates.org/api/latest.json?app_id=3b104210d52d43bc95d0bf35a9a818c7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=(TextView)findViewById(R.id.tv1);
        tvCantidadAlCambio=(TextView)findViewById(R.id.tvCantidadAlCambio);
        etCantidadOriginal=(EditText)findViewById(R.id.etCantidadOrigen);

        RequestQueue request = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

            String cad="";

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObjectPrincipal=new JSONObject(response.toString(0));

                    cad="Moneda base: " + jsonObjectPrincipal.getString("base");

                    cad += "\nCambio a euros (EUR): " +jsonObjectPrincipal.getJSONObject("rates").getString("EUR");
                    cambio= Double.parseDouble(jsonObjectPrincipal.getJSONObject("rates").getString("EUR"));

                    Date date=new Date(Long.parseLong(jsonObjectPrincipal.getString("timestamp"))*1000);

                    cad += "\nFecha: " + date;

                    //Log.d("RESPONSE", response.toString(0));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                tv1.setText(cad);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(jsonObjectRequest);

    }

    public void cambiar(View v){
        tvCantidadAlCambio.setText(""+Double.parseDouble(etCantidadOriginal.getText().toString())*cambio);
    }
}
