/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news.model;

import java.util.List;

import lombok.Getter;

/**
 *
 */
public final class News {

    @Getter
    private String status;


    @Getter
    private int totalResults;

    @Getter
    private List<Article> articles;

}
