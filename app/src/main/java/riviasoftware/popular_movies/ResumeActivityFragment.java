package riviasoftware.popular_movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riviasoftware.popular_movies.constants.PopularMoviesConstants;
import riviasoftware.popular_movies.data.Movie;
import riviasoftware.popular_movies.data.Trailer;
import riviasoftware.popular_movies.data.TrailerResponse;
import riviasoftware.popular_movies.retrofit.services.TMVDatabaseService;
import riviasoftware.popular_movies.retrofit.utils.ApiUtils;



public class ResumeActivityFragment extends Fragment {


    @BindView(R.id.image_photo_poster)
    ImageView poster;
    @BindView(R.id.trailer)
    ImageView trailer;
    @BindView(R.id.play_trailer)
    ImageButton playTrailer;
    @BindView(R.id.movie_title)
    TextView title;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.puntuacion)
    TextView puntuacion;
    @BindView(R.id.overview)
    TextView overview;


    private TMVDatabaseService tmvDatabaseService;
    private Movie movie;
    private List<Trailer> trailerResponse;
    private Unbinder unbinder;


    public ResumeActivityFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_resume_movie, container, false);
        movie = getActivity().getIntent().getParcelableExtra("movie");
        tmvDatabaseService = ApiUtils.getTMVDataService();
        unbinder = ButterKnife.bind(this, view);
        loadTrailers();

        String imageURL = ApiUtils.IMAGE_URL_780 + movie.getPosterPath();
        Glide.with(getActivity().getApplicationContext()).load(imageURL).error(R.drawable.defaultmovie).into(poster);
        String imageURL2 = ApiUtils.IMAGE_URL_780 + movie.getBackdropPath();
        Glide.with(getActivity().getApplicationContext()).load(imageURL2).error(R.drawable.defaultmovie).into(trailer);
        playTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = ApiUtils.YOUTUBE_URL + trailerResponse.get(0).getKey();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
            }
        });
        title.setText(movie.getTitle());


        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(movie.getReleaseDate());


        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMMM yyyy");
        releaseDate.setText(fmtOut.format(date).toUpperCase());
        puntuacion.setText(String.valueOf(movie.getVoteAverage()));
        overview.setText(movie.getOverview());
        return view;

    }



    public void loadTrailers(){
        tmvDatabaseService.getTrailerById(movie.getId(), PopularMoviesConstants.apiKEY).enqueue(new Callback<TrailerResponse>() {

            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {

                if (response.isSuccessful()){
                    trailerResponse = response.body().getResults();
                }else{
                    int statusCode  = response.code();
                    Log.d("MainActivity", "error loading from API status code: "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
