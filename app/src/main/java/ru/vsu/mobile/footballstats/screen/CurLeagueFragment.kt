package ru.vsu.mobile.footballstats.screen

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import ru.vsu.mobile.footballstats.R
import ru.vsu.mobile.footballstats.adapter.InfoItemAdapter
import ru.vsu.mobile.footballstats.api.service.StatsApi
import ru.vsu.mobile.footballstats.databinding.FragmentCurLeagueBinding
import ru.vsu.mobile.footballstats.repository.StatsRepository
import ru.vsu.mobile.footballstats.utils.DataBaseHelper
import ru.vsu.mobile.footballstats.viewmodel.AnyViewModelFactory
import ru.vsu.mobile.footballstats.viewmodel.StatsViewModel

class CurLeagueFragment : Fragment() {

    private lateinit var adapter: InfoItemAdapter
    private lateinit var viewModel: StatsViewModel
    private val args by navArgs<CurLeagueFragmentArgs>()
    private lateinit var binding: FragmentCurLeagueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurLeagueBinding.inflate(inflater, container, false)
        val statsApi = StatsApi.getStatsApi()!!
        val statsRepository = StatsRepository(statsApi = statsApi)
        viewModel = ViewModelProvider(
            this,
            AnyViewModelFactory(
                repository = statsRepository
            )
        )[StatsViewModel::class.java]
        selectStar()
        setCurLeague()
        fillSelections()
        initRV()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.infoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.progressBar.visibility = View.GONE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {

        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.VISIBLE
        }
        binding.filledSeason.setOnItemClickListener { parent, view, position, id ->
            onClicked()
        }
        binding.filledType.setOnItemClickListener { parent, view, position, id ->
            onClicked()
        }
        binding.filledSeason.setText(args.seasons.last().toString())
        binding.filledType.setText("Состав")
        fillSelections()
        onClicked()
        binding.addFavoriteIv.setOnClickListener {
            val db = DataBaseHelper(requireActivity() , null)
            val isActive = db.getFavById(args.league.id)
            if (isActive) {
                db.deleteFav(args.league.id)
            } else {
                db.addFav(args.league.id)
            }
            selectStar()
        }
    }

    private fun selectStar() {
        val db = DataBaseHelper(requireActivity() , null)
        if (db.getFavById(args.league.id)) {
            binding.addFavoriteIv.setImageResource(R.drawable.ic_star_active)
        } else {
            binding.addFavoriteIv.setImageResource(R.drawable.ic_star_inncavtive)
        }
    }

    private fun onClicked() {
        val typeText = binding.filledType.text.toString()
        val season = binding.filledSeason.text.toString().toInt()
        when (typeText) {
            "Состав" -> viewModel.getAllTeams(season = season, args.league.id)
            "Бомбардиры" -> viewModel.getAllTopScorers(season = season, args.league.id)
            "Ассистенты" -> viewModel.getAllTopAssists(season = season, args.league.id)
        }
    }

    private fun fillSelections() = with(binding) {
        val typeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            listOf("Состав", "Бомбардиры", "Ассистенты")
        )
        filledType.setAdapter(typeAdapter)
        val seasonAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            args.seasons.toList().sortedByDescending { it }
        )
        filledSeason.setAdapter(seasonAdapter)
    }

    private fun setCurLeague() = with(binding) {
        val curLeague = args.league
        leagueNameTv.text = curLeague.name
        countryNameTv.text = args.countryName
        Picasso.get().load(curLeague.logo).into(leagueIv)
    }

    private fun initRV() = with(binding) {
        adapter = InfoItemAdapter()
        infoRv.layoutManager = LinearLayoutManager(context)
        infoRv.adapter = adapter
    }

}