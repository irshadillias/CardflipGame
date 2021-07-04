package com.irshadillias.singtel.assessment.cardgamefeature.cardgame.adapter

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.irshadillias.singtel.assessment.cardgamefeature.R
import com.irshadillias.singtel.assessment.cardgamefeature.data.CardItem
import com.irshadillias.singtel.assessment.cardgamefeature.databinding.ListItemCardGameBinding
import java.util.*


/**
 * Adapter for the [RecyclerView] in [GalleryFragment].
 */

class CardItemAdapter (private val cardListerner : CardGameListener):
    ListAdapter<CardItem,CardItemAdapter.CardItemViewHolder>(CardDiffCallback()) {
    lateinit var mParent: ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {
        mParent = parent;
        return CardItemViewHolder(
            ListItemCardGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            cardListerner,
            itemCount
        )
    }

    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
        val card = getItem(position)
        if (card != null) {
            (holder as CardItemViewHolder).bind(card)

        }
    }

    class CardItemViewHolder(
        private val binding: ListItemCardGameBinding,
        private val cardListerner : CardGameListener,
        private val listCount:Int
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            var selectCard: String = "-1"
            var previousCardFront:View? = null
            var previousCardback:View?   = null
            var matches:Int = 0
        }
        var  mSetRightOut: AnimatorSet =
            AnimatorInflater.loadAnimator(binding.root.context, R.animator.out_animation) as AnimatorSet
        var  mSetLeftIn: AnimatorSet =
            AnimatorInflater.loadAnimator(binding.root.context, R.animator.in_animation) as AnimatorSet
        var mPrevSetRightOut =
            AnimatorInflater.loadAnimator(binding.root.context, R.animator.out_animation) as AnimatorSet
        var mPrevmSetLeftIn =
            AnimatorInflater.loadAnimator(binding.root.context, R.animator.in_animation) as AnimatorSet


        init {
            binding.setFrontclick { view ->
                if(view.alpha > 0){
                    cardListerner.cardSelected()
                    flipcard(view.alpha < 1)
                    if(selectCard.equals("-1")){
                        selectCard = binding.cardNumber.text.toString()
                        previousCardFront = binding.cardfront
                        previousCardback  = binding.cardback
                    }else if(!selectCard.equals(binding.cardNumber.text.toString())){
                        Handler().postDelayed({
                            resetPair()
                        }, 2000)
                    }else{
                        matches++
                        println("${matches} >>>> ${listCount/2}")
                        if(matches >= listCount/2){
                            cardListerner.gameSuccess()
                        }
                        cleanSele()
                    }
                }
            }
        }

        private fun flipcard(isBack : Boolean){
            if (!isBack) {
                mSetRightOut.setTarget(binding.cardfront)
                mSetLeftIn.setTarget(binding.cardback)
                mSetRightOut.start()
                mSetLeftIn.start()
            } else {
                mSetRightOut.setTarget(binding.cardback)
                mSetLeftIn.setTarget(binding.cardfront)
                mSetRightOut.start()
                mSetLeftIn.start()
            }
        }

        private fun resetPair(){
            flipBackrevious()
            flipcard(true)
            cleanSele()
        }

        private fun flipBackrevious(){
            if(previousCardFront != null && previousCardback != null){
                mPrevSetRightOut.setTarget(previousCardback)
                mPrevmSetLeftIn.setTarget(previousCardFront)
                mPrevSetRightOut.start()
                mPrevmSetLeftIn.start()
            }
        }

        private fun cleanSele(){
            selectCard = "-1"
            previousCardFront = null
            previousCardback  = null
        }

        fun bind(item: CardItem) {
            changeCameraDistance()
            binding.apply {
                card = item
                executePendingBindings()
            }
        }

        private fun changeCameraDistance() {
            val distance = 8000
            val scale: Float = binding.root.context.getResources().getDisplayMetrics().density * distance
            binding.cardfront.setCameraDistance(scale)
            binding.cardback.setCameraDistance(scale)
        }
    }
}

private class CardDiffCallback : DiffUtil.ItemCallback<CardItem>() {

    override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
        return oldItem == newItem
    }
}
