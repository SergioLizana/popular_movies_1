package riviasoftware.popular_movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import riviasoftware.popular_movies.data.Movie;
import riviasoftware.popular_movies.retrofit.utils.ApiUtils;

/**
 * Created by Sergio on 31/03/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridAdapterViewHolder> implements View.OnClickListener  {
    private Context context;

    private List<Movie> data;

    private LayoutInflater inflater;

    private View.OnClickListener listener;


    public GridAdapter(Context context, ArrayList<Movie> data){
            this.context = context;
            this.data = data;
            inflater = LayoutInflater.from(context);
    }

    @Override
    public GridAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.movie_list,parent,false);
        GridAdapterViewHolder holder = new GridAdapterViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(GridAdapterViewHolder holder, int position) {
        String imageURL = ApiUtils.IMAGE_URL_780 + data.get(position).getPosterPath();
        Glide.with(context)
                .load(imageURL)
                .error(R.drawable.defaultmovie)
                .into(holder.mImageView);

        holder.mRlayout.setTag(data.get(position));

        holder.mRlayout.setOnClickListener(this);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Movie movie) {

        int currPosition = data.indexOf(movie);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Movie movie) {

        data.add(position, movie);
        notifyItemInserted(position);
    }

    public void updateMovies(List<Movie> movies) {
        data = movies;
        notifyDataSetChanged();
    }

    public  Movie getMovie(int adapterPosition) {
        return data.get(adapterPosition);
    }
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public class GridAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        RelativeLayout mRlayout;

        public GridAdapterViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.img_row);
            mRlayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout);

        }



    }


}
