package at.fh.swengb.nemetz

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.item_movie.*
import java.math.RoundingMode
import java.text.DecimalFormat

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_MOVIE_ID_RATING = "MOVIE_ID_RATING_EXTRA"
        val ADD_OR_EDIT_RATING_REQUEST = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getStringExtra(MainActivity.EXTRA_MOVIE_ID)



        if (movieId == null){
            Log.e(getString(R.string.PASSING_ERROR),getString(R.string.PASSING_ERROR_CONTENT))
            finish()
        } else {
            outputMovieDetails(movieId)
            detail_movie_btn_rate.setOnClickListener{
                val intent = Intent(this, MovieRatingActivity ::class.java)

                intent.putExtra(EXTRA_MOVIE_ID_RATING, movieId)
                startActivityForResult(intent, ADD_OR_EDIT_RATING_REQUEST)
            }

        }

    }

    private fun outputMovieDetails(inputId: String){
        MovieRepository.movieDetailById(inputId,
            success = { val movie =  it
                    val myDecimalFormat = DecimalFormat("#.####")
                    myDecimalFormat.roundingMode = RoundingMode.CEILING

                    detail_movie_title.text =  movie.title
                    detail_movie_director_output.text = movie.director.name
                    detail_movie_actors_output.text = movie.actors.joinToString { it.name }
                    detail_movie_genre_output.text = movie.genres.joinToString { it }
                    detail_movie_avg_ratingbar.rating = movie.ratingAverage().toFloat()
                    detail_movie_avg_ratings.text = myDecimalFormat.format(movie.ratingAverage())
                    detail_movie_rating_count.text = movie.reviews.count().toString()
                    detail_movie_release_output.text = movie.release
                    detail_movie_plot_description.text = movie.plot


                    Glide
                        .with(detail_movie_image_poster)
                        .load(movie.posterImagePath)
                        .into(detail_movie_image_poster)

                    Glide
                        .with(detail_movie_image_backdrop)
                        .load(movie.backdropImagePath)
                        .into(detail_movie_image_backdrop)
            },
            error = { Log.e(getString(R.string.API_CALL), it)})
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_OR_EDIT_RATING_REQUEST && resultCode == Activity.RESULT_OK) {

            val movieReturnId = data?.getStringExtra(MovieRatingActivity.EXTRA_MOVIE_ID_RATING_RETURN)
            if(movieReturnId != null){
                outputMovieDetails(movieReturnId)
            }

        }
    }




}
