package com.irshadillias.singtel.assessment.cardgamefeature.base

import android.content.Context
import android.content.Intent
import com.irshadillias.singtel.assessment.cardgamefeature.cardgame.view.CardFlipGameFragment
import com.irshadillias.singtel.assessment.singtelframework.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardGameActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, CardGameActivity::class.java)
    }
    override fun fragment() = CardFlipGameFragment()

}