package ru.vsu.mobile.footballstats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.vsu.mobile.footballstats.R
import ru.vsu.mobile.footballstats.api.model.InfoItem
import ru.vsu.mobile.footballstats.databinding.InfoRvItemBinding

class InfoItemAdapter(
) : ListAdapter<InfoItem, InfoItemAdapter.Holder>(Comparator()) {
    class Holder(binding: InfoRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val name: TextView = binding.nameTv
        private val score: TextView = binding.scoreTv
        private val picture: ImageView = binding.pictureIv
        fun bind(infoItem: InfoItem) {
            name.text = infoItem.name
            Picasso.get().load(infoItem.pic).into(picture)
            if (infoItem.score != -1) {
                score.text = infoItem.score.toString()
            } else {
                score.visibility = View.GONE
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<InfoItem>() {
        override fun areItemsTheSame(oldItem: InfoItem, newItem: InfoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InfoItem, newItem: InfoItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.info_rv_item, parent, false)
        val binding = InfoRvItemBinding.bind(view)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: InfoItemAdapter.Holder, position: Int) {
        holder.bind(getItem(position))
    }
}
