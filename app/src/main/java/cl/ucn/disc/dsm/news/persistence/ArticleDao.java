/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cl.ucn.disc.dsm.news.model.Article;

/**
 *
 */
@Dao
public interface ArticleDao {

    /**
     *
     * @return the List of Article.
     */
    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAll();

    /**
     *
     * @param article
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

}
