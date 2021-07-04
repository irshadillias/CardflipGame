package com.irshadillias.singtel.assessment.cardgamefeature.cardgame.viewmodel

import com.irshadillias.singtel.assessment.cardgamefeature.AndroidTest
import com.irshadillias.singtel.assessment.cardgamefeature.utility.CARD_PAIRS_VALUE
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Test

import org.junit.Before

class CardFlipGameViewModelTest : AndroidTest(){
    private lateinit var cardFlipViewModel: CardFlipGameViewModel
    @Before
    fun setUp() {
        cardFlipViewModel = CardFlipGameViewModel()
    }

    @Test
    fun `generate cards for game in random order`() {
        cardFlipViewModel.cardList.observeForever{
            it!!.size shouldEqualTo CARD_PAIRS_VALUE*2
        }
        runBlocking { cardFlipViewModel.generateCard() }
    }

    @Test
    fun `game step increasing for each click`() {
        cardFlipViewModel.cardcount.observeForever {
            it shouldEqualTo 1
        }
        runBlocking { cardFlipViewModel.increaseCardSel() }
    }
}