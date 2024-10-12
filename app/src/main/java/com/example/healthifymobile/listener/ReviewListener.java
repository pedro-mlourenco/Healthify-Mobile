package com.example.healthifymobile.listener;

import com.example.healthifymobile.modelo.CartAPI;
import com.example.healthifymobile.modelo.Review;

import java.util.ArrayList;

public interface ReviewListener {
    void onRefreshReview(ArrayList<Review> review);
}
