package com.example.healthifymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthifymobile.modelo.SingletonGestorBd;
import com.example.healthifymobile.utils.InputFilterMinMax;

public class ReviewActivity extends AppCompatActivity {

    private Button btnsaltar, btnpublish;
    private EditText etrating, etcomentario;
    private int rating;
    private int ratingMin=1, ratingMax=5;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        btnsaltar = findViewById(R.id.btnSaltar);
        btnpublish = findViewById(R.id.btnPublish);
        etrating = findViewById(R.id.eTRating);
        etcomentario = findViewById(R.id.eTComentario);

        etrating.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "5")});

        context = getApplicationContext();
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int userprofilesid = sharedPreferences.getInt("profileId", 0);


        btnpublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comentario = etcomentario.getText().toString();
                int fkIdRefeicao = 1;

                try {
                    rating = Integer.parseInt(etrating.getText().toString());
                    if (rating >= ratingMin && rating <= ratingMax){
                        SingletonGestorBd.getInstance(getApplicationContext()).addReview(comentario, rating, fkIdRefeicao, userprofilesid, context);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(context, "A classificação tem de ser entre 0 e 5", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException nfe) {
                    Toast.makeText(context, "A classificação é obrigatória", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onClickSkip(View view) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", name);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        return;
    }

}