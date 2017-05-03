package riviasoftware.popular_movies.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import riviasoftware.popular_movies.data.Movie;
import riviasoftware.popular_movies.data.MoviesResponse;
import riviasoftware.popular_movies.data.TrailerResponse;

public interface TMVDatabaseService {

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apikey);

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apikey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apikey, @Query("language") String language, @Query("page") int page, @Query("region") String region);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getTrailerById(@Path("id") int movieId, @Query("api_key") String apikey);



}

