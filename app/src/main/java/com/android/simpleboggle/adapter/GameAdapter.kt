package com.android.simpleboggle.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.simpleboggle.R
import com.android.simpleboggle.databinding.ItemGameWordBinding
import com.android.simpleboggle.entity.GameWord
import java.util.function.BiConsumer

class GameAdapter : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private val showData = mutableListOf<GameWord>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(list: List<GameWord>) {
        showData.clear()
        showData.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetCheckStatus() {
        for (gameWord in showData) {
            gameWord.isCheck = false
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameWordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val gameWord = showData[position]
        setData(holder.binding, gameWord)
        holder.itemView.setOnClickListener {
            listener?.accept(gameWord, position)
        }
    }

    override fun getItemCount(): Int {
        return showData.size
    }

    private fun setData(binding: ItemGameWordBinding, gameWord: GameWord) {
        binding.apply {
            val context = root.context
            tvWord.text = gameWord.word
            tvWord.setBackgroundColor(ContextCompat.getColor(context, if (gameWord.isCheck) R.color.color_blue else R.color.white))
            tvWord.setTextColor(ContextCompat.getColor(context, if (gameWord.isCheck) R.color.white else R.color.color_333))
        }
    }

    private var listener: BiConsumer<GameWord, Int>? = null

    fun setItemClickListener(listener: BiConsumer<GameWord, Int>) {
        this.listener = listener
    }

    inner class GameViewHolder(val binding: ItemGameWordBinding) : RecyclerView.ViewHolder(binding.root)

}