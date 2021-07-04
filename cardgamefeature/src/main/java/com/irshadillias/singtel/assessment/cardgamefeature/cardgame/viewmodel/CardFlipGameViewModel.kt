package com.irshadillias.singtel.assessment.cardgamefeature.cardgame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irshadillias.singtel.assessment.cardgamefeature.data.CardItem
import com.irshadillias.singtel.assessment.cardgamefeature.utility.CARD_PAIRS_VALUE
import com.irshadillias.singtel.assessment.singtelframework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardFlipGameViewModel @Inject constructor() : BaseViewModel() {

    private val _cardList: MutableLiveData<MutableList<CardItem>> = MutableLiveData()
    val cardList: LiveData<MutableList<CardItem>> = _cardList
    private val _cardSelCount : MutableLiveData<Int> = MutableLiveData()
    val cardcount: LiveData<Int> = _cardSelCount

    fun generateCard(){
        var dataset : MutableList<CardItem> = ArrayList()
        for (cardIndex in 1..CARD_PAIRS_VALUE){
            dataset.add(CardItem(cardIndex*2, cardIndex.toString()) )
            dataset.add(CardItem((cardIndex*2)+1, cardIndex.toString()) )
        }
        dataset.shuffle()
        _cardSelCount.value = 0
        _cardList.value = dataset
    }

    fun increaseCardSel (){
       var cardCount:Int? =  cardcount.value;
        if (cardCount != null) {
            _cardSelCount.value = cardCount+1
        }
    }


}