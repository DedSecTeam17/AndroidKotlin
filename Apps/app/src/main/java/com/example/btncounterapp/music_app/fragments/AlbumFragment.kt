package com.example.btncounterapp.music_app.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.adapters.AlbumListAdapter
import com.example.btncounterapp.music_app.adapters.ArtistListAdapter
import com.example.btncounterapp.music_app.models.Artist
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.AlbumResponseItem
import com.example.btncounterapp.music_app.repostory.Responses.models.ArtistResponseItem
import com.example.btncounterapp.music_app.utls.AppActions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var albumRecyclerView: RecyclerView
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
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_album, container, false)
        albumRecyclerView = view.findViewById(R.id.albums)


        getData()
        return view
    }


    fun getData() {
        ServiceBuilder.musicServiceProvider().getAlbums()
            .enqueue(object : Callback<ArrayList<AlbumResponseItem>> {
                override fun onFailure(call: Call<ArrayList<AlbumResponseItem>>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(
                    call: Call<ArrayList<AlbumResponseItem>>,
                    response: Response<ArrayList<AlbumResponseItem>>
                ) {


                    albumRecyclerView.apply {
                        layoutManager = GridLayoutManager(activity, 2).apply {

                        }
                        adapter = AlbumListAdapter(data = response.body()!!)

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
         * @return A new instance of fragment AlbumFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlbumFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
