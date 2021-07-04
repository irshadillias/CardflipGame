package com.irshadillias.singtel.assessment.cardgamefeature.data

data class CardItem (
    val id: Double,
    val text: String,
    val isResolved:Boolean = false,
    val isFlip:Boolean = false
)

