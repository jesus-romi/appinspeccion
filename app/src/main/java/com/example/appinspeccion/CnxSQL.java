package com.example.appinspeccion;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class CnxSQL {
    Connection con;
    @SuppressLint("NewApi")
    public Connection conclass()
    {
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String CnxURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            CnxURL = "jdbc:jtds:sqlserver://172.16.20.12;databasename=Epicor905;user=Reporteador;password=Sistemas;";
            con = DriverManager.getConnection(CnxURL);
        }
        catch (Exception ex)
        {
            Log.e("Error!", ex.getMessage());
        }

        return con;
    }
}
