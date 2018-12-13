package com.example.umar.boi;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class bookList extends RecyclerView.Adapter<bookList.bookListViewHolder> implements Filterable {


    String chobi;
    bookListViewHolder bookListViewHolder;
    private Context context;
    private List<Posts> postsList;
    private List<Posts> search;


    public bookList(Context context, List<Posts> postsList) {
        this.context = context;
        this.postsList = postsList;
        search = new ArrayList<>(postsList);
    }

    @NonNull
    @Override
    public bookListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, viewGroup, false);
        bookListViewHolder = new bookListViewHolder(view);
        return bookListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull bookListViewHolder bookListViewHolder, int position) {

        Posts post = postsList.get(position);

        bookListViewHolder.textViewTitle.setText(post.getName());
        bookListViewHolder.textViewAuthor.setText(post.getAuthor());
        bookListViewHolder.textViewPrice.setText(post.getPrice());
        Picasso.get().load(post.getImage_URL()).into(bookListViewHolder.imageView);
        bookListViewHolder.textViewRating.setText(post.getRating());
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    @Override
    public Filter getFilter() {
        return filtered;
    }

    private Filter filtered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<Posts> filteredList = new ArrayList<>();

           if(constraint == null || constraint.length()==0)
           {
               filteredList.addAll(search);
           }

           else {

               String filterBy = constraint.toString().toLowerCase().trim();

               for(Posts post: search)
                   if (post.getName().trim().toLowerCase().contains(filterBy))
                   {
                       filteredList.add(post);

                   }
           }

           FilterResults results = new FilterResults();
           results.values = filteredList;

           return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            postsList.clear();
            postsList.addAll((List)results.values);
            notifyDataSetChanged();


        }
    };


    class bookListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView textViewTitle, textViewRating, textViewPrice,textViewAuthor;

        public bookListViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);


        }
    }


}


