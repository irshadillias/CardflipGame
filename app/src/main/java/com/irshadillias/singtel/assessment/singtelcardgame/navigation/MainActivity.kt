package com.irshadillias.singtel.assessment.singtelcardgame.navigation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.irshadillias.singtel.assessment.cardgamefeature.base.CardGameActivity
import com.irshadillias.singtel.assessment.singtelcardgame.R
import com.irshadillias.singtel.assessment.singtelcardgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.gameStatingAction = View.OnClickListener { v->
            navigateToGame()
        }
        navigateToGame()
    }

    private fun navigateToGame(){
        this.startActivity(CardGameActivity.callingIntent(this))
    }



}