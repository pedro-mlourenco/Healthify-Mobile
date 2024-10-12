package com.example.healthifymobile.modelo;

public class CartaoPagamento {
    private int id, mes, ano, cvv;
    private String nome,numeroCartao;

    public CartaoPagamento(int id, String numeroCartao, int mes, int ano, int cvv,String nome) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.mes = mes;
        this.ano = ano;
        this.cvv = cvv;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}
}
