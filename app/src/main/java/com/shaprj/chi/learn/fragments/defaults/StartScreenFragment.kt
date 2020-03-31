package com.shaprj.chi.learn.fragments.defaults

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import learnwd.shaprj.ru.learnwd.R

class StartScreenFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.start_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val videoView = activity!!.findViewById(R.id.videoView1) as VideoView
        videoView.setMediaController(MediaController(activity))
        videoView.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/earthquake.mp4?alt=media&token=98993371-823d-4b81-adc7-d9d87ec841a9"))
        //        videoView.setVideoURI(Uri.parse("https://streamable.com/zn0kr"));
        videoView.start()
    }

    companion object {

        val instance: StartScreenFragment
            get() = StartScreenFragment()
    }
}
