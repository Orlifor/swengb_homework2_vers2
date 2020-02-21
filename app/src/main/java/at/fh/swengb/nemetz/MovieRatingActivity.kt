package at.fh.swengb.nemetz

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import at.fh.swengb.nemetz.MovieRepository.rateMovie
import kotlinx.android.synthetic.main.activity_movie_rating.*

class MovieRatingActivity : AppCompatActivity() {

    companion object{
        val EXTRA_MOVIE_ID_RATING_RETURN = "MOVIE_ID_RATING_RETURN_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_rating)

        val movieId = intent.getStringExtra(MovieDetailActivity.EXTRA_MOVIE_ID_RATING)

        if (movieId == null) {
            finish()

        } else {
            MovieRepository.movieDetailById(movieId,
                success = { rating_movie_title.text =  it.title },
                error = { Log.e(getString(R.string.API_CALL), it)})

            rating_movie_btn_rateThisMovie.setOnClickListener {

                val myMovieReviewObject = Review(
                    rating_movie_ratingbar.rating.toDouble(),
                    rating_movie_feedback.text.toString()
                )

                MovieRepository.rateMovie( movieId, myMovieReviewObject,
                    success = {Log.i(getString(R.string.API_CALL), getString(R.string.added_review))},
                    error = {Log.e(getString(R.string.API_CALL), it)})

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_MOVIE_ID_RATING_RETURN, movieId)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

        }
    }
}
