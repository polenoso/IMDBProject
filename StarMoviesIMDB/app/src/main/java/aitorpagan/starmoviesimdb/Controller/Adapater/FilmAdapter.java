package aitorpagan.starmoviesimdb.Controller.Adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aitorpagan.starmoviesimdb.Model.Film;
import aitorpagan.starmoviesimdb.R;

/**
 * Created by aitorpagan on 23/2/17.
 */

public class FilmAdapter extends RecyclerView.Adapter {

    List<Film> films;
    Context context;

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public FilmAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view_layout, parent, false);
        FilmHolder vh = new FilmHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FilmHolder filmHolder = (FilmHolder) holder;
        filmHolder.overviewView.setText(getFilms().get(position).getOverview());
        filmHolder.titleView.setText(getFilms().get(position).getTitle());
        //Needs to cast date to only year
        filmHolder.releaseDateView.setText(getFilms().get(position).getRelease_date());
        //Needs to call to download image where setting image to holder.
        //filmHolder.movieImageView.setImageResource(R.drawable.img_example);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }


    private static class FilmHolder extends RecyclerView.ViewHolder{


        public TextView titleView;
        public ImageView movieImageView;
        public TextView releaseDateView;
        public TextView overviewView;
        public FilmHolder(View v){
            super(v);
            titleView = (TextView) v.findViewById(R.id.movie_title);
            movieImageView = (ImageView) v.findViewById(R.id.movie_image);
            releaseDateView = (TextView) v.findViewById(R.id.release_year);
            overviewView = (TextView) v.findViewById(R.id.overview_text);
        }

    }
}
