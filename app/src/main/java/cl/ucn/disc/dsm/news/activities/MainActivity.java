/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.news.NewsController;
import cl.ucn.disc.dsm.news.R;
import cl.ucn.disc.dsm.news.adapters.ArticleAdapter;
import cl.ucn.disc.dsm.news.model.Article;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class MainActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        {
            final Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        // ListView of data
        {
            final ListView listView = findViewById(android.R.id.list);

            final View empty = findViewById(android.R.id.empty);
            listView.setEmptyView(empty);

            final ArticleAdapter articleAdapter = new ArticleAdapter(this);
            listView.setAdapter(articleAdapter);

            AsyncTask.execute(() -> {

                try {

                    final List<Article> articles = NewsController.getArticles();
                    log.debug("Size: {}", articles.size());

                    articleAdapter.addArticles(articles);

                    runOnUiThread(() -> articleAdapter.notifyDataSetChanged());

                } catch (IOException e) {
                    log.error("Error", e);
                }

            });


        }
    }
}
