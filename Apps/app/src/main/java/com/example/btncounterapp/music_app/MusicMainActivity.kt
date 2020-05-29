package com.example.btncounterapp.music_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.btncounterapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.MenuItem

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.fragment.app.Fragment
import com.example.btncounterapp.music_app.fragments.AlbumFragment
import com.example.btncounterapp.music_app.fragments.ArtistFragment
import com.example.btncounterapp.music_app.fragments.TracksFragment


class MusicMainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_main)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation.selectedItemId = 0
        openFragment(ArtistFragment.newInstance("", ""))

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.artist -> {
                    openFragment(ArtistFragment.newInstance("", ""))
                    true
                }
                R.id.album -> {
                    openFragment(AlbumFragment.newInstance("", ""))
                    true
                }
                R.id.tracks -> {
                    openFragment(TracksFragment.newInstance("", ""))
                    true
                }
                else -> {
                    openFragment(ArtistFragment.newInstance("", ""))
                    true
                }
            }
        }
    }

    fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}
