/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news.model;

import lombok.Getter;

/**
 *
 */
public final class Article {

    @Getter
    private Source source;

    @Getter
    private String author;

    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    private String url;

    @Getter
    private String urlToImage;

    @Getter
    private String publishedAt;

    @Getter
    private String content;

}
