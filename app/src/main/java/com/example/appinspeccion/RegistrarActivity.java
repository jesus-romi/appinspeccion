package com.example.appinspeccion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistrarActivity extends AppCompatActivity {
    Connection cnx;
    Button btnConsultar;
    TextView txtLote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        btnConsultar = findViewById(R.id.btnConsultar);
        txtLote = findViewById(R.id.txtLote);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsultarSql();
            }
        });
    }

    public void ConsultarSql()
    {
        CnxSQL c = new CnxSQL();
        cnx = c.conclass();
        if (c != null)
        {
            try {
                String query = "SELECT TOP 1\n" +
                        "UD.KEY2 [LOTE],\n" +
                        "P.PARTNUM [PARTE],\n" +
                        "P.PARTDESCRIPTION [DESCRIPCION],\n" +
                        "FAM.SHORTCHAR02 [FAMILIA],\n" +
                        "UD.NUMBER03 [PESO],\n" +
                        "C.NAME [CLIENTE],\n" +
                        "UD.SHORTCHAR16 [OT]\n" +
                        "FROM UD10 (NOLOCK) UD\n" +
                        "INNER JOIN PART (NOLOCK) P ON UD.COMPANY = P.COMPANY AND UD.CHARACTER01 = P.PARTNUM\n" +
                        "LEFT JOIN UD01 (NOLOCK) FAM ON P.COMPANY = FAM.COMPANY AND FAM.KEY1 = 'FAMILIAS' AND P.SHORTCHAR01 = FAM.KEY2\n" +
                        "INNER JOIN JOBPROD (NOLOCK) JP ON UD.COMPANY = JP.COMPANY AND UD.SHORTCHAR16 = JP.JOBNUM\n" +
                        "INNER JOIN ORDERHED (NOLOCK) OH ON JP.COMPANY = OH.COMPANY AND JP.ORDERNUM = OH.ORDERNUM\n" +
                        "INNER JOIN CUSTOMER (NOLOCK) C ON OH.COMPANY = C.COMPANY AND OH.CUSTNUM = C.CUSTNUM\n" +
                        "WHERE UD.COMPANY = 'TEC01' AND UD.KEY2 = '" + txtLote.getText().toString() + "'" +
                        "ORDER BY UD.NUMBER03 DESC\n";
                Statement smt = cnx.createStatement();
                ResultSet set = smt.executeQuery(query);
                while (set.next())
                {
                    ((TextView)findViewById(R.id.txtParte)).setText(set.getString("PARTE"));
                    ((TextView)findViewById(R.id.txtDescripcion)).setText(set.getString("DESCRIPCION"));
                    ((TextView)findViewById(R.id.txtFamilia)).setText(set.getString("FAMILIA"));
                    ((TextView)findViewById(R.id.txtPeso)).setText(set.getString("PESO"));
                    ((TextView)findViewById(R.id.txtCliente)).setText(set.getString("CLIENTE"));
                    ((TextView)findViewById(R.id.txtOT)).setText(set.getString("OT"));

                }
                cnx.close();
            } catch (Exception ex)
            {
                Log.e("Error!", ex.getMessage());
            }
        }

        //((TextView)findViewById(R.id.txtParte)).setText("Hola Mundo");

    }
}