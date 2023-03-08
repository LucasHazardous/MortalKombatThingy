package com.github.lucashazardous.mortalkombatthingy

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lucashazardous.mortalkombatthingy.databinding.ImageThingBinding

class MortalKombatCharacter(val text: String, val img: Int, val count: Int)

class MortalKombatViewHolder(
    val binding: ImageThingBinding
) : RecyclerView.ViewHolder(binding.root)

class MortalKombatAdapter(private var dataSet: ArrayList<MortalKombatCharacter>) :
    RecyclerView.Adapter<MortalKombatViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MortalKombatViewHolder {
        val binding = ImageThingBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return MortalKombatViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: MortalKombatViewHolder, position: Int) {
        val img = viewHolder.binding.img
        img.setImageResource(dataSet[position].img)

        val text = viewHolder.binding.txt
        text.text = dataSet[position].text + " " + dataSet[position].count
    }

    override fun getItemCount() = dataSet.size

    fun setItems(dataset: ArrayList<MortalKombatCharacter>) {
        dataSet = dataset
    }
}