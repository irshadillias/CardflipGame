package com.irshadillias.singtel.assessment.cardgamefeature.data

data class CardItem (
    val id: Int,
    val text: String,
    val isResolved:Boolean = false,
    val isFlip:Boolean = false
)

