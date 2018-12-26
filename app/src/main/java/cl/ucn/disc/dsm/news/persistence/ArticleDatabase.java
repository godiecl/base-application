/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import cl.ucn.disc.dsm.news.model.Article;

/**
 *
 */
@Database(entities = {Article.class}, version = 1)
public abstract class ArticleDatabase extends RoomDatabase {

    /**
     * @return the {@link ArticleDao}.
     */
    public abstract ArticleDao getArticleDao();

}
