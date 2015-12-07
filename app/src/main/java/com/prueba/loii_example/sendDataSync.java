package com.prueba.loii_example;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class sendDataSync {

    Context context;
    JSONObject jsonData;
    EditText inputDireccion,inputTelefono;

    public sendDataSync(Context context, JSONObject jsonData){
        this.context = context;
        this.jsonData = jsonData;
    }

    public sendDataSync(Context context, JSONObject json, EditText inputDireccion, EditText inputTelefono) {
        this.context = context;
        this.jsonData = json;
        this.inputDireccion = inputDireccion;
        this.inputTelefono = inputTelefono;
    }

    public void sincronizar(){

        String tag_json_obj = "json_obj_req";

        String url = "http://192.168.5.151/BD_Example/DB_Admin.php";

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Enviando datos...");
        pDialog.setTitle("Sincronizando");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonData, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.i("JSON: ", response.toString());

                try {
                    pDialog.hide();

                    int type = response.getInt("type");

                    switch (type){
                        case 1:
                            Toast.makeText(context,"INSERCION EN SERVIDOR",Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(context,"ACTUALIZACION EN SERVIDOR",Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(context,"BORRADO EN SERVIDOR",Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            inputDireccion.setText(response.getString("Domicilio"));
                            inputTelefono.setText(response.getString("Telefono"));
                            break;
                    }

                } catch (JSONException e) {
                    Toast.makeText(context,"USUARIO NO ENCONTRADO",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MAIN", "Error: " + error.getMessage());

                String json = null;
                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 500:
                            json = new String(response.data);
                            //Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                // hide the progress dialog
                pDialog.hide();

            }
        }){
            /** METODOS PARA PONER HEADERS Y BODY EN EL REQUEST DEL SERVER **/
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8" + getParamsEncoding();
            }
            /***************************************************************************************/

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

}
