package com.example.healthifymobile.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class healthifyBdHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bd_healthify";
    private static final int    DB_VERSION = 1;
    private final SQLiteDatabase db;

// fazer tabela encomenda para associar a refeição
    private static final String tableRefeicao = "refeicao";
    private static final String idRefeicao ="id", dataRefeicao="data", precoRefeicao="preco",metodoRefeicaco="metodoRefeicaco",
            estadoRefeicao="estadoRefeicao",fkidPerfil="fkIdPerfil";

// juntar review a refeiçoes
    private static final String tableReview = "review";
    private static final String idReview ="id", criticaReview ="review", ratingReview ="rating", fkIdRefeicao="fkIdRefeicao";

    //tabela onde guarda os cartoes para pagamento
    private static final String tableCartaoPagamento = "cartaoPagamento";
    private static final String idCartao ="id",mesCartao="mes",anoCartao="ano",cvvCartao="cvv";
    private static final String numeroCartao="numeroCartao",nomeCartao="nomeCartao";

    public healthifyBdHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableRefeicao ="create table " + tableRefeicao + " ( "+
                idRefeicao+" int primary key, " +
                dataRefeicao+" text not null, "+
                precoRefeicao+ " float not null, "+
                metodoRefeicaco+ " text not null, "+
                estadoRefeicao+ " text not null, "+
                fkidPerfil+ " int not null) ;";

        sqLiteDatabase.execSQL(createTableRefeicao);

        String createTableReview ="create table " + tableReview + " ( "+
                idReview+" int primary key, " +
                criticaReview +" text, "+
                ratingReview + " float, "+
                fkIdRefeicao+ " int not null) ;";

        sqLiteDatabase.execSQL(createTableReview);

        String createTableCartaoPagamento ="create table " + tableCartaoPagamento + " ( "+
                idCartao+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                numeroCartao +" text not null, "+
                nomeCartao +" text not null, "+
                mesCartao +" int not null, "+
                anoCartao + " int not null, "+
                cvvCartao+ " int not null) ;";

        sqLiteDatabase.execSQL(createTableCartaoPagamento);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String dropTableReview ="drop table if exists " + tableReview;
        sqLiteDatabase.execSQL(dropTableReview);

        String dropTableRefeicao ="drop table if exists " + tableRefeicao;
        sqLiteDatabase.execSQL(dropTableRefeicao);

        String dropTableCartaoPagamento ="drop table if exists " + tableCartaoPagamento;
        sqLiteDatabase.execSQL(dropTableCartaoPagamento);
        onCreate(sqLiteDatabase);
    }

    /**********************Carregar Tabelas*************************/

    //carregar tabelas

    public ArrayList<Refeicao> carregarRefeicoes(){
        ArrayList<Refeicao> refeicoes = new ArrayList<>();
        Cursor cursor = this.db.query(tableRefeicao, new String[]{idRefeicao,dataRefeicao,precoRefeicao,
                metodoRefeicaco,estadoRefeicao,fkidPerfil},null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Refeicao r = new Refeicao(cursor.getInt(0),cursor.getString(1),cursor.getFloat(2),cursor.getString(3)
                        ,cursor.getString(4),cursor.getInt(5));
                refeicoes.add(r);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return refeicoes;
    }

    public ArrayList<Review> carregarReviews(){
        ArrayList<Review> reviews = new ArrayList<>();
        Cursor cursor = db.query(tableReview, new String[]{idReview,criticaReview,ratingReview,fkIdRefeicao},
                null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Review r = new Review(cursor.getInt(0),cursor.getString(1),cursor.getFloat(2),cursor.getInt(3));
                reviews.add(r);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return reviews;
    }

    public ArrayList<CartaoPagamento> carregarCartaoPagamento(){
        ArrayList<CartaoPagamento> cartao = new ArrayList<>();
        Cursor cursor = db.query(tableCartaoPagamento, new String[]{idCartao,numeroCartao,mesCartao,anoCartao,cvvCartao,nomeCartao},
                null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                CartaoPagamento r = new CartaoPagamento(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)
                        ,cursor.getInt(4),cursor.getString(5));
                cartao.add(r);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return cartao;
    }
    //**********************CRUD*************************

    //create
    public void adicionarRefeicao(Refeicao ref) {
        ContentValues values = new ContentValues();

        values.put(idRefeicao, ref.getId());
        values.put(dataRefeicao, ref.getData());
        values.put(precoRefeicao, ref.getPreco());
        values.put(metodoRefeicaco, ref.getMetodoRefeicaco());
        values.put(estadoRefeicao, ref.getEstadoRefeicao());
        values.put(fkidPerfil, ref.getFkIdPerfil());

        long result = this.db.insert(tableRefeicao, null, values);
        if(result==-1){
            System.out.println("DATABASE SAVE FAILLED!!!!!!!!!!!");
        }
    }

    public void adicionarReview(Review rev){
        ContentValues values=new ContentValues();

        values.put(idReview,rev.getId());
        values.put(criticaReview,rev.getReview());
        values.put(ratingReview,rev.getRating());
        values.put(fkIdRefeicao,rev.getFkId());

        long result = this.db.insert(tableReview, null, values);
        if(result==-1){
            System.out.println("DATABASE SAVE FAILLED!!!!!!!!!!!");
        }
        ;
    }

    public Boolean adicionarMetodopagamento(CartaoPagamento cartao){
        ContentValues values=new ContentValues();
        values.put(numeroCartao,cartao.getNumeroCartao());
        values.put(mesCartao,cartao.getMes());
        values.put(anoCartao,cartao.getAno());
        values.put(cvvCartao,cartao.getCvv());
        values.put(nomeCartao,cartao.getNome());

        long result = this.db.insert(tableCartaoPagamento, null, values);
        if(result==-1){
            return false;
        }
        return true;
    }

    //delete
    public void removerRefeicoes(){
        this.db.delete(tableRefeicao,null,null);
    }
    public void removerReviews(){
        this.db.delete(tableReview,null,null);
    }
    public void removerCartoes(){
        this.db.delete(tableCartaoPagamento,null,null);
    }

    public void deleteLocalData(){
        this.db.delete(tableReview,null,null);
        this.db.delete(tableRefeicao,null,null);
        this.db.delete(tableCartaoPagamento,null,null);
    }
}
