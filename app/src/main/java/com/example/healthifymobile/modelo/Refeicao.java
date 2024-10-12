package com.example.healthifymobile.modelo;

//id, nome, preco, calorias, hidratos, lipidos, fibra

import java.util.Date;

public class Refeicao {
    private int id,fkIdPerfil;
    private float preco;
    private String data,metodoRefeicaco,estadoRefeicao;

    public Refeicao(int id,String data, float preco,  String metodoRefeicaco, String estadoRefeicao, int fkIdPerfil) {
        this.id = id;
        this.fkIdPerfil = fkIdPerfil;
        this.preco = preco;
        this.data = data;
        this.metodoRefeicaco = metodoRefeicaco;
        this.estadoRefeicao = estadoRefeicao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkIdPerfil() {
        return fkIdPerfil;
    }

    public void setFkIdPerfil(int fkIdPerfil) {
        this.fkIdPerfil = fkIdPerfil;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMetodoRefeicaco() {
        return metodoRefeicaco;
    }

    public void setMetodoRefeicaco(String metodoRefeicaco) {
        this.metodoRefeicaco = metodoRefeicaco;
    }

    public String getEstadoRefeicao() {
        return estadoRefeicao;
    }

    public void setEstadoRefeicao(String estadoRefeicao) {
        this.estadoRefeicao = estadoRefeicao;
    }
}
