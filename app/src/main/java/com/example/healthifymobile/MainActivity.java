package com.example.healthifymobile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthifymobile.listener.TableListener;
import com.example.healthifymobile.modelo.SingletonGestorBd;
import com.example.healthifymobile.modelo.TableReserveAPI;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TableListener {
    private Context context;
    private String mesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= getApplicationContext();

        SingletonGestorBd.getInstance(context).setTableListener(this);
        TextView greeting = findViewById(R.id.tvUserNameGreeting);
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("name","");
        greeting.setText(username);
    }

    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    mesa = result.getContents();
                    SingletonGestorBd.getInstance(context).ocuparMesa(mesa, this);
                }
            });

    public void onClickScan(View view) {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setOrientationLocked(true);
        options.setBeepEnabled(true);

        barcodeLauncher.launch(options);
    }

    public void onClickEmenta(View view){
        Intent intent = new Intent(this, EmentaActivity.class);
        startActivity(intent);
    }

    public void onClickPerfil(View view) {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    public void onClickLogout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onClickGetTakeAway(View view) {
        Intent intent = new Intent(this, ListPedidosActivity.class);
        String type = "takeaway";
        intent.putExtra("tableid", type);
        startActivity(intent);
    }

    @Override
    public void onTableReserve(TableReserveAPI reserve,String mesa) {

        if (reserve.getMessage().equals("Ocupou esta mesa! Pode começar o seu pedido!")){
            Intent intent = new Intent(this, ListPedidosActivity.class);
            intent.putExtra("tableid", mesa);
            startActivity(intent);
        }else {
            Toast.makeText(context,reserve.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onTableFree(TableReserveAPI reserve, String mesa) {

    }

    @Override
    public void onSeatedClient(TableReserveAPI reserve, String mesa) {

        if (reserve.getMessage().equals("Ocupou esta mesa! Pode começar o seu pedido!")){
            Intent intent = new Intent(this, ListPedidosActivity.class);
            intent.putExtra("tableid", mesa);
            startActivity(intent);
        }else {
            Toast.makeText(context,reserve.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}