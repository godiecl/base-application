/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.news.R;
import cl.ucn.disc.dsm.news.model.Article;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public final class ArticleAdapter extends BaseAdapter {

    /**
     *
     */
    private final List<Article> articles = new ArrayList<>();

    /**
     * Inflater
     */
    private LayoutInflater inflater;

    /**
     * @param context to use.
     */
    public ArticleAdapter(@NonNull final Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * @param list
     */
    public void addArticles(final List<Article> list) {

        this.articles.addAll(list);

    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return this.articles.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Article getItem(final int position) {
        return this.articles.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(final int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = this.inflater.inflate(R.layout.row_article, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final Article article = this.articles.get(position);

        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        holder.image.setImageURI(article.getUrlToImage());

        return convertView;
    }

    /**
     *
     */
    private static class ViewHolder {

        SimpleDraweeView image;
        TextView title;
        TextView description;

        ViewHolder(View view) {
            this.image = view.findViewById(R.id.ra_iv_image);
            this.title = view.findViewById(R.id.ra_tv_title);
            this.description = view.findViewById(R.id.ra_tv_description);
        }

    }
}
