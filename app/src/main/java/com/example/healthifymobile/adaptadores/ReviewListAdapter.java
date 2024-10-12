package com.example.healthifymobile.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthifymobile.R;
import com.example.healthifymobile.modelo.Refeicao;
import com.example.healthifymobile.modelo.Review;

import java.util.ArrayList;

public class ReviewListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Review> reviews;

    public ReviewListAdapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return reviews.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.fragment_review_field, null);

        ReviewListAdapter.ViewHolderLista viewHolder = (ReviewListAdapter.ViewHolderLista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ReviewListAdapter.ViewHolderLista(view, context);
            view.setTag(viewHolder);
        }

        viewHolder.update(reviews.get(i));

        return view;
    }

    private class ViewHolderLista {
        private TextView tvIdReview, tvIdMealReview, tvCorpoReview, tvRatingReview;


        public ViewHolderLista(View view, Context context) {
            tvIdReview = view.findViewById(R.id.tvIdReview);
            tvIdMealReview = view.findViewById(R.id.tvIdMealReview);
            tvCorpoReview = view.findViewById(R.id.tvCorpoReview);
            tvRatingReview = view.findViewById(R.id.tvRatingReview);

        }

        public void update(Review review) {

            tvIdReview.setText(review.getId() + "");
            tvIdMealReview.setText(review.getFkId() + "");
            tvCorpoReview.setText(review.getReview() + "");
            tvRatingReview.setText(review.getRating() + "");

        }
    }
}
