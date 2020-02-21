package at.fh.swengb.nemetz

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val EXTRA_MOVIE_ID = "MOVIE_ID_EXTRA"
    }


    val movieAdapter = MovieAdapter(){
        val intent = Intent(this, MovieDetailActivity ::class.java)
        intent.putExtra(EXTRA_MOVIE_ID, it.id)
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movie_recycler_view.layoutManager = GridLayoutManager(this,3)
        movie_recycler_view.adapter = movieAdapter

        MovieRepository.movieList(
            success = {
                movieAdapter.updateList(it)

            },
            error = {
                Log.e(getString(R.string.API_CALL), it)
            }
        )
    }

}
