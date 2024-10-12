package com.example.healthifymobile.modelo;

public class Review {
    public int id, fkIdRefeicao;
    public float rating;
    public String review;

    public Review(int id,String review, float rating,int fkIdRefeicao){
        this.id = id;
        this.review = review;
        this.rating = rating;
        this.fkIdRefeicao = fkIdRefeicao;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public float getRating() {return rating;}

    public void setRating(float rating) {this.rating = rating;}

    public String getReview() {return review;}

    public void setReview(String review) {this.review = review;}

    public int getFkId() {return fkIdRefeicao;}

    public void setFkId(int fkId) {this.fkIdRefeicao = fkId;}
}
