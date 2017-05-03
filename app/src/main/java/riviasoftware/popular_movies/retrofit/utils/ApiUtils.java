package riviasoftware.popular_movies.retrofit.utils;

import riviasoftware.popular_movies.retrofit.RetrofitClient;
import riviasoftware.popular_movies.retrofit.services.TMVDatabaseService;

public class ApiUtils {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_URL_780 ="http://image.tmdb.org/t/p/w780";
    public static final String IMAGE_URL_ORIGINAL ="http://image.tmdb.org/t/p/original";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    public static TMVDatabaseService getTMVDataService() {
        return RetrofitClient.getClient(BASE_URL).create(TMVDatabaseService.class);
    }
}