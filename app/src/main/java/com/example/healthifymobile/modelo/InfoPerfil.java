package com.example.healthifymobile.modelo;

public class InfoPerfil {
    private int idPerfil, nif, porta, telemovel,idUser;
    private String nome, email, rua, cidade, andar, token,role;

    public InfoPerfil(int idPerfil, int nif, String nome, String email, int telemovel,
                      String rua, int porta, String andar, String cidade,int idUser, String token, String role){

        this.idPerfil = idPerfil;
        this.nif = nif;
        this.nome = nome;
        this.email = email;
        this.telemovel = telemovel;
        this.rua = rua;
        this.porta = porta;
        this.andar = andar;
        this.cidade = cidade;
        this.idUser = idUser;
        this.token = token;
        this.role = role;
    }
    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

    public String getToken() {return token;}

    public void setToken(String token) {this.token = token;}

    public int getIdPerfil() {return idPerfil;}

    public void setIdPerfil(int idPerfil) {this.idPerfil = idPerfil;}

    public int getIdUser() {return idUser;}

    public void setIdUser(int idUser) {this.idUser = idUser;}

    public int getNif() {return nif;}

    public void setNif(int nif) {this.nif = nif;}

    public int getPorta() {return porta;}

    public void setPorta(int porta) {this.porta = porta;}

    public int getTelemovel() {return telemovel;}

    public void setTelemovel(int telemovel) {this.telemovel = telemovel;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getRua() {return rua;}

    public void setRua(String rua) {this.rua = rua;}

    public String getCidade() {return cidade;}

    public void setCidade(String cidade) {this.cidade = cidade;}

    public String getAndar() {return andar;}

    public void setAndar(String andar) {this.andar = andar;}

}
