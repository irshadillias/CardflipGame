package com.irshadillias.singtel.assessment.cardgamefeature.cardgame.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.irshadillias.singtel.assessment.cardgamefeature.R
import com.irshadillias.singtel.assessment.cardgamefeature.cardgame.adapter.CardGameListener
import com.irshadillias.singtel.assessment.cardgamefeature.cardgame.adapter.CardItemAdapter
import com.irshadillias.singtel.assessment.cardgamefeature.cardgame.viewmodel.CardFlipGameViewModel
import com.irshadillias.singtel.assessment.cardgamefeature.data.CardItem
import com.irshadillias.singtel.assessment.cardgamefeature.databinding.FragmentCardgameBinding
import com.irshadillias.singtel.assessment.singtelframework.base.BaseFragment
import com.irshadillias.singtel.assessment.singtelframework.exception.Failure
import com.irshadillias.singtel.assessment.singtelframework.extension.failure
import com.irshadillias.singtel.assessment.singtelframework.extension.observe
import com.irshadillias.singtel.assessment.singtelframework.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFlipGameFragment: BaseFragment() {
    private val mCardFlipGameViewModel by viewModels<CardFlipGameViewModel>()
    lateinit var cardAdapter:CardItemAdapter
    override fun layoutId() = R.layout.fragment_cardgame

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCardgameBinding.inflate(inflater, container, false).
            apply {
                viewModel =  mCardFlipGameViewModel
                lifecycleOwner = viewLifecycleOwner
                resetClick = View.OnClickListener { v->
                    mCardFlipGameViewModel.generateCard()
                }
            }
        context ?: return binding.root
        cardAdapter = CardItemAdapter( object: CardGameListener{
            override fun cardSelected() {
                mCardFlipGameViewModel.increaseCardSel()
            }
            override fun gameSuccess() {
                val alertDialog: AlertDialog? = activity?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.setTitle(R.string.title_success)
                    builder.setMessage(getString(R.string.success_msg,mCardFlipGameViewModel.cardcount.value))
                    builder.apply {
                        setPositiveButton(R.string.sucess_ok
                        ) { dialog, id -> mCardFlipGameViewModel.generateCard() }
                    }
                    builder.create()
                }
                alertDialog?.show()

            }

        })
        binding.cardList.adapter = cardAdapter

        mCardFlipGameViewModel.generateCard()
        with(mCardFlipGameViewModel) {
            observe(cardList, ::renderCardList)
            failure(failure, ::handleFailure)
        }
        return binding.root
    }


    private fun renderCardList(cardlist: List<CardItem>?){
        cardAdapter.submitList(cardlist)
        cardAdapter.notifyDataSetChanged()
    }

    private fun handleFailure(failure: Failure?) {
        context?.toast("cardgneration failed !!!");
    }

}