/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.news.model.Article;
import cl.ucn.disc.dsm.news.model.News;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 *
 */
@Slf4j
public final class NewsController {

    /**
     *
     */
    public interface NewsService {

        /**
         * @return the {@link Call} of {@link News}.
         */
        @Headers({"X-Api-Key: 8dd673e94a9e4086a41b4cde0b6aa1c5"})
        @GET("top-headlines?sources=techcrunch,ars-technica,engadget,buzzfeed,wired&pageSize=100")
        Call<News> getTopHeadlines();

    }

    /**
     * Des-Serializador GSON
     */
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            //.serializeNulls()
            //.setPrettyPrinting()
            .create();

    /**
     * Interceptor
     */
    private static final HttpLoggingInterceptor interceptor;

    static {
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    /**
     * The Client
     */
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();

    /**
     * Retrofit
     */
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    /**
     * The Service
     */
    private static final NewsService newsService = retrofit.create(NewsService.class);

    /**
     * @return the {@link List} of {@link Article}.
     */
    public static List<Article> getArticles() throws IOException {

        // Call
        final Call<News> newsCall = newsService.getTopHeadlines();

        // News
        final News news = newsCall.execute().body();
        log.debug("News status: {}, totalResult: {}", news.getStatus(), news.getTotalResults());

        return news.getArticles();

    }
}
