package com.example.healthifymobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthifymobile.listener.NewAccountListener;
import com.example.healthifymobile.listener.PerfilListener;
import com.example.healthifymobile.modelo.InfoPerfil;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.Calendar;

public class EditarPerfilActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NewAccountListener, PerfilListener {

    private TextView dateText;
    private TextView metodoPagamento, tvCidade;
    private EditText etUsername, etEmail, etPassword, etNif, etNome, etTelemovel, etRua, etPorta, etAndar, etCodPostal;
    private Button btnGuardar;
    private final static int MY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        etEmail = findViewById(R.id.eTUser);
        etPassword = findViewById(R.id.eTPassword);
        etNif = findViewById(R.id.eTNIF);
        etNome = findViewById(R.id.etNmeroCartaoAdd);
        etTelemovel = findViewById(R.id.eTTelemovel);
        etRua = findViewById(R.id.eTRua);
        etPorta = findViewById(R.id.eTPorta);
        etAndar = findViewById(R.id.eTAndar);
        tvCidade = findViewById(R.id.etCidade);
        dateText = findViewById(R.id.etData);
        etCodPostal = findViewById(R.id.eTCodPostal);
        btnGuardar = findViewById(R.id.btnGuardar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        Spinner spinnerCidade = findViewById(R.id.spinnerCidade);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidade.setAdapter(adapter);

        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setTextView(spinnerCidade.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        SingletonGestorBd.getInstance(getApplicationContext()).setPerfilListener(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.lightlisse));

        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int userprofilesid = sharedPreferences.getInt("profileId", 0);
        String email = sharedPreferences.getString("email", null);
        String token = sharedPreferences.getString("token", null);

        etNome.setText(sharedPreferences.getString("name", null));
        etTelemovel.setText("" + sharedPreferences.getInt("cellphone", 0));
        tvCidade.setText(sharedPreferences.getString("city", null));
        etRua.setText(sharedPreferences.getString("street", null));
        etPorta.setText("" + sharedPreferences.getInt("door", 0));
        etAndar.setText(sharedPreferences.getString("floor", null));
        etNif.setText("" + sharedPreferences.getInt("nif", 0));
        etEmail.setText(sharedPreferences.getString("email", null));

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNome.getText().toString();
                int telemovel = Integer.parseInt(etTelemovel.getText().toString());
                String cidade = tvCidade.getText().toString();
                String rua = etRua.getText().toString();
                int porta = Integer.parseInt(etPorta.getText().toString());
                String andar =  etAndar.getText().toString();
                int nif = Integer.parseInt(etNif.getText().toString());

                SingletonGestorBd.getInstance(getApplicationContext()).updateProfileAPI(nif, nome, telemovel, rua, porta, andar, cidade, getApplicationContext(), userprofilesid, email, token);
            }
        });

        findViewById(R.id.btnCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    public void setTextView(String text) {
        tvCidade.setText(text);
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.CalendarTheme,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        dateText.setText(date);
    }

    public void onClickAddPagamento(View view) {
        Intent intent = new Intent(this, AddPagamentoActivity.class);
        startActivity(intent);
    }

    public void onClickGoBack(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MY_REQUEST_CODE) {
                if (data != null)
                    metodoPagamento.setText(data.getStringExtra("value"));
            }
        }
    }

    @Override
    public void onValidateRegister(int userid, String username, String email, String password) {

        int nif = Integer.parseInt(etNif.getText().toString());
        String nome = etNome.getText().toString();
        int telemovel = Integer.parseInt(etTelemovel.getText().toString());
        String rua = etRua.getText().toString();
        int porta = Integer.parseInt(etPorta.getText().toString());
        String andar = etAndar.getText().toString();
        String cidade = tvCidade.getText().toString();

        SingletonGestorBd.getInstance(getApplicationContext()).newProfileAPI(nif, nome, telemovel, rua, porta, andar, cidade, userid, getApplicationContext());
    }

    @Override
    public void onValidateNewProfile(Boolean message) {
        if (message == true) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Atualização do perfil falhou!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpdatePerfil(InfoPerfil perfil) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
        SharedPreferences.Editor addShare = sharedPreferences.edit();

        addShare.putString("name",perfil.getNome());
        addShare.putInt("cellphone",perfil.getTelemovel());
        addShare.putString("street",perfil.getRua());
        addShare.putInt("door",perfil.getPorta());
        addShare.putString("floor",perfil.getAndar());
        addShare.putString("city",perfil.getCidade());
        addShare.putInt("nif",perfil.getNif());

        addShare.apply();

        Intent intent = new Intent(this, PerfilActivity.class);
        intent.putExtra("username", perfil.getNome());
        startActivity(intent);
        finish();
    }
}