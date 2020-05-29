package com.example.btncounterapp.music_app.fragments

import com.example.btncounterapp.music_app.repostory.Responses.models.ArtistResponseItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.adapters.ArtistListAdapter
import com.example.btncounterapp.music_app.models.Artist
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.utls.AppActions
//import com.example.btncounterapp.music_app.repostory.Responses.ArtistResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ArtistFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ArtistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArtistFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var artistRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    var data = mutableListOf<Artist>(
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url")


    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_artist, container, false)
        artistRecyclerView = view.findViewById(R.id.artists)


        getData();

        return view
    }


    fun getData() {
        ServiceBuilder.musicServiceProvider().getArtists()
            .enqueue(object : Callback<ArrayList<ArtistResponseItem>> {
                override fun onFailure(call: Call<ArrayList<ArtistResponseItem>>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(
                    call: Call<ArrayList<ArtistResponseItem>>,
                    response: Response<ArrayList<ArtistResponseItem>>
                ) {
//                    response.body().get(0).

                    artistRecyclerView.apply {
                        layoutManager = GridLayoutManager(activity, 2)
                        adapter = ArtistListAdapter(data = response.body()!!, action = AppActions.ARTIST)

                    }

                }
            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ArtistFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArtistFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
