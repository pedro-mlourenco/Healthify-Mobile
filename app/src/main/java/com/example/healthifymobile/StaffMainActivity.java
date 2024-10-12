package com.example.healthifymobile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthifymobile.listener.RelogioPontoListener;
import com.example.healthifymobile.listener.TableListener;
import com.example.healthifymobile.modelo.SingletonGestorBd;
import com.example.healthifymobile.modelo.TableReserveAPI;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class StaffMainActivity extends AppCompatActivity implements RelogioPontoListener, TableListener {

    private Context context;
    private int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);
        context = getApplicationContext();
        TextView greeting = findViewById(R.id.tvUserNameGreeting);
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("name","");
        greeting.setText(username);
        profileId = sharedPreferences.getInt("profileId", 0);

        SingletonGestorBd.getInstance(context).setRelogioPontoListener(this);
        SingletonGestorBd.getInstance(context).setTableListener(this);

        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setOrientationLocked(true);
        options.setBeepEnabled(true);

        final Button btnSentarCliente = findViewById(R.id.btnSentarCliente);
        btnSentarCliente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registarLauncher.launch(options);
            }
        });

        final Button btnReservarMesa = findViewById(R.id.btnReservarMesa);
        btnReservarMesa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reservarLauncher.launch(options);
            }
        });

        final Button btnLibertarMesa = findViewById(R.id.btnLibertarMesa);
        btnLibertarMesa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                libertarLauncher.launch(options);
            }
        });
    }

    private final ActivityResultLauncher<ScanOptions> reservarLauncher = registerForActivityResult(new ScanContract(),
            result -> {

                if (result.getContents() == null) {
                    Toast.makeText(StaffMainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String mesa = result.getContents();

                    SingletonGestorBd.getInstance(getApplicationContext()).reservarMesa(mesa, this);
                }
            });

    private final ActivityResultLauncher<ScanOptions> libertarLauncher = registerForActivityResult(new ScanContract(),
            result -> {

                if (result.getContents() == null) {
                    Toast.makeText(StaffMainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String mesa = result.getContents();

                    SingletonGestorBd.getInstance(getApplicationContext()).libertarMesa(mesa, this);
                }
            });

    private final ActivityResultLauncher<ScanOptions> registarLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(StaffMainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String mesa = result.getContents();
                    SingletonGestorBd.getInstance(context).ocuparMesa(mesa, this);
                }
            });

    public void onClickRelogioPonto(View view) {
        SingletonGestorBd.getInstance(context).setRegistarPonto(profileId, context);
    }

    public void onClickLogout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void onClickPerfil(View view) {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefreshPonto(Boolean relogioPonto) {
        if (relogioPonto) {
            Toast.makeText(getApplicationContext(), "Registo de ponto bem sucedido!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Registo de ponto falhou!\nPor favor tente de novo ", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onTableReserve(TableReserveAPI reserve, String mesa) {
        Toast.makeText(context,reserve.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTableFree(TableReserveAPI reserve, String mesa) {
        Toast.makeText(context,reserve.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSeatedClient(TableReserveAPI reserve, String mesa) {
        if (reserve.getMessage().equals("Ocupou esta mesa! Pode começar o seu pedido!")){
            Intent intent = new Intent(this, ListPedidosActivity.class);
            intent.putExtra("tableid", mesa);
            intent.putExtra("method", "cash");
            startActivity(intent);
        }else {
            Toast.makeText(context,reserve.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}