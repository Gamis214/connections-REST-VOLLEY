package com.prueba.loii_example;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText inputNombre,inputDireccion,inputTelefono;
    Button btnInsertar,btnActualizar,btnBorrar,btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNombre = (EditText)findViewById(R.id.txtNombre);
        inputDireccion = (EditText)findViewById(R.id.txtDomicilio);
        inputTelefono = (EditText)findViewById(R.id.txtTelefono);

        btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnBorrar = (Button)findViewById(R.id.btnBorrar);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);

        btnInsertar.setOnClickListener(this);
        btnActualizar.setOnClickListener(this);
        btnBorrar.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnInsertar:
                try {
                    INSERTAR();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnActualizar:
                try {
                    ACTUALIZAR();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnBorrar:
                try {
                    BORRAR();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnBuscar:
                try {
                    BUSCAR();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void BUSCAR() throws JSONException {
        JSONObject json = crearJSON(4);

        sendDataSync sendData = new sendDataSync(this,json,inputDireccion,inputTelefono);

        sendData.sincronizar();

    }

    private void BORRAR() throws JSONException {
        JSONObject json = crearJSON(3);

        sendDataSync sendData = new sendDataSync(this,json);

        sendData.sincronizar();

        cleanInputs();
    }

    private void ACTUALIZAR() throws JSONException {
        JSONObject json = crearJSON(2);

        sendDataSync sendData = new sendDataSync(this,json);

        sendData.sincronizar();

        cleanInputs();
    }

    private void INSERTAR() throws JSONException {
        JSONObject json = crearJSON(1);

        sendDataSync sendData = new sendDataSync(this,json);

        sendData.sincronizar();

        cleanInputs();

    }

    private JSONObject crearJSON(int Type) throws JSONException {

        JSONObject jsonCliente = new JSONObject();
        JSONArray jsonArrCte = new JSONArray();
        JSONObject jsonData = new JSONObject();

        jsonData.put("Nombre",inputNombre.getText().toString());
        jsonData.put("Domiclio",inputDireccion.getText().toString());
        jsonData.put("Telefono",inputTelefono.getText().toString());
        jsonData.put("Type",Type);

        jsonArrCte.put(jsonData);

        jsonCliente.put("DATOS",jsonArrCte);

        return jsonCliente;
    }

    public void cleanInputs(){
        inputNombre.setText("");
        inputDireccion.setText("");
        inputTelefono.setText("");
    }

}
