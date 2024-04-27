package com.android.simpleboggle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.simpleboggle.R
import com.android.simpleboggle.adapter.GameAdapter
import com.android.simpleboggle.call.NewGameCallback
import com.android.simpleboggle.call.SubmitCallback
import com.android.simpleboggle.databinding.FragmentMainBinding
import com.android.simpleboggle.entity.GameWord
import com.android.simpleboggle.viewmodel.GameViewModel
import com.android.simpleboggle.viewmodel.GameViewModel.Companion.VOWELS

class MainFragment(private val submitCallback: SubmitCallback) : Fragment(), NewGameCallback {

    private val mBinding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    private val gameViewModel: GameViewModel by activityViewModels()

    private val gameAdapter = GameAdapter()

    private val recordClickGameWord = mutableListOf<GameWord>()

    private var lastClickGameWord: GameWord? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            rvContent.adapter = gameAdapter
            gameAdapter.setItemClickListener(this@MainFragment::clickCheck)

            btClear.setOnClickListener {
                clearRecord()
            }

            btNext.setOnClickListener {
                onNewGameClick()
            }

            btSubmit.setOnClickListener {
                submitData()
            }
        }
        reloadGameData()
    }

    private fun clickCheck(gameWord: GameWord, position: Int) {
        if (!gameWord.isCheck) {
            if (lastClickGameWord != null) {
                val lastGameWord = lastClickGameWord!!
                val currentRow = gameWord.row
                val currentColumn = gameWord.column
                if (currentRow < lastGameWord.row - 1 || currentRow > lastGameWord.row + 1 || currentColumn < lastGameWord.column - 1 || currentColumn > lastGameWord.column + 1) {
                    Toast.makeText(requireContext(), R.string.hint_1, Toast.LENGTH_SHORT).show()
                    return
                }
            }
            lastClickGameWord = gameWord
            gameWord.isCheck = true
            gameAdapter.notifyItemChanged(position)
            recordClickGameWord.add(gameWord)
            previewWord()
        }
    }

    private fun previewWord() {
        if (recordClickGameWord.isEmpty()) {
            mBinding.tvWord.text = ""
        } else {
            val list = recordClickGameWord.map { it.word }
            val word = java.lang.String.join("", list)
            mBinding.tvWord.text = word
        }
    }

    private fun clearRecord() {
        recordClickGameWord.clear()
        lastClickGameWord = null
        gameAdapter.resetCheckStatus()
        previewWord()
    }


    private fun submitData() {
        if (recordClickGameWord.size < 4) {
            Toast.makeText(requireContext(), R.string.hint_3, Toast.LENGTH_SHORT).show()
            return
        }
        val vowels = VOWELS.toMutableList()
        val list = recordClickGameWord.filter { vowels.contains(it.word) }

        /*

        if (list.size < 2) {
            Toast.makeText(requireContext(), R.string.hint_2, Toast.LENGTH_SHORT).show()
            return
        }

         */

        gameViewModel.submitData(recordClickGameWord) {
            onNewGameClick()
            submitCallback.onSubmitClick(it)
        }
    }

    private fun reloadGameData() {
        gameViewModel.loadGameData {
            gameAdapter.submitData(it)
        }
    }

    override fun onNewGameClick() {
        recordClickGameWord.clear()
        lastClickGameWord = null
        reloadGameData()
        previewWord()
    }
}