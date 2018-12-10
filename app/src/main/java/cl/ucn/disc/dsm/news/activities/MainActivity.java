/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news.activities;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // RecyclerView
        {
            final RecyclerView recyclerView = findViewById(R.id.am_rv_articles);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

            final ArticleAdapter articleAdapter = new ArticleAdapter(this);
            recyclerView.setAdapter(articleAdapter);

            AsyncTask.execute(() -> {

                try {

                    final List<Article> articles = NewsController.getArticles();
                    log.debug("Size: {}", articles.size());

                    articleAdapter.addArticles(articles);

                    runOnUiThread(articleAdapter::notifyDataSetChanged);

                } catch (IOException e) {
                    log.error("Error", e);
                }

            });

        }

    }
}
