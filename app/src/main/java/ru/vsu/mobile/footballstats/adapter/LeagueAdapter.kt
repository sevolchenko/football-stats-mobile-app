package ru.vsu.mobile.footballstats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.squareup.picasso.Picasso
import ru.vsu.mobile.footballstats.MainActivity
import ru.vsu.mobile.footballstats.R
import ru.vsu.mobile.footballstats.api.model.league.LeagueResponse
import ru.vsu.mobile.footballstats.databinding.LeagueRvItemBinding

class LeagueAdapter(
    private val leagueClickInterface: LeagueClickInterface
) : ListAdapter<LeagueResponse, LeagueAdapter.Holder>(Comparator()) {
    class Holder(binding: LeagueRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val leagueName: TextView = binding.leagueName
        private val leagueLogo: ImageView = binding.leagueLogo
        private val countryFlag: ImageView = binding.countryFlag
        fun bind(leagueResponse: LeagueResponse) {
            leagueName.text = leagueResponse.league.name
            Picasso.get().load(leagueResponse.league.logo).into(leagueLogo)
            if (leagueResponse.country.name == "World") {
                countryFlag.setImageResource(R.drawable.ic_earth)
            } else {
                val imageLoader = ImageLoader.Builder(MainActivity.getContext())
                    .components {
                        add(SvgDecoder.Factory())
                    }.build()
                val request = ImageRequest.Builder(MainActivity.getContext())
                    .data(leagueResponse.country.flag)
                    .target(countryFlag)
                    .build()
                imageLoader.enqueue(request)
            }
        }
    }

    class Comparator: DiffUtil.ItemCallback<LeagueResponse>() {
        override fun areItemsTheSame(oldItem: LeagueResponse, newItem: LeagueResponse): Boolean {
            return oldItem.league.id == newItem.league.id
        }

        override fun areContentsTheSame(oldItem: LeagueResponse, newItem: LeagueResponse): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.league_rv_item, parent, false)
        val binding = LeagueRvItemBinding.bind(view)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            leagueClickInterface.onItemClick(position)
        }
    }
}

interface LeagueClickInterface {
    fun onItemClick(id: Int)
}