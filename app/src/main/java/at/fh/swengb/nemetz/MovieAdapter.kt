package at.fh.swengb.nemetz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(val clickListener: (movie: Movie) -> Unit): RecyclerView.Adapter<MovieViewHolder>(){

    private var movieList = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val movieItemView = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(movieItemView, clickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var movie = movieList[position]
        holder.bindItem(movie)
    }

    fun updateList(newList: List<Movie>) {
        movieList = newList
        notifyDataSetChanged()
    }

}


class MovieViewHolder(itemView: View, val clickListener: (movie: Movie) -> Unit): RecyclerView.ViewHolder(itemView){
    fun bindItem(movie: Movie){

        itemView.item_movie_title.text = movie.title
        Glide
            .with(itemView)
            .load(movie.posterImagePath)
            .into(itemView.image_movie_poster_item)



        itemView.setOnClickListener{
            clickListener(movie)
        }

    }

}