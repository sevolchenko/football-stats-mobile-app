package ru.vsu.mobile.footballstats.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vsu.mobile.footballstats.adapter.LeagueAdapter
import ru.vsu.mobile.footballstats.adapter.LeagueClickInterface
import ru.vsu.mobile.footballstats.api.service.LeaguesApi
import ru.vsu.mobile.footballstats.databinding.FragmentLeaguesBinding
import ru.vsu.mobile.footballstats.repository.LeaguesRepository
import ru.vsu.mobile.footballstats.viewmodel.AnyViewModelFactory
import ru.vsu.mobile.footballstats.viewmodel.LeaguesViewModel

class LeaguesFragment : Fragment(), LeagueClickInterface {
    private lateinit var adapter: LeagueAdapter
    private lateinit var viewModel: LeaguesViewModel
    private lateinit var binding:  FragmentLeaguesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaguesBinding.inflate(inflater, container, false)
        val leaguesApi = LeaguesApi.getLeaguesApi()!!
        val leaguesRepository = LeaguesRepository(leaguesApi = leaguesApi)
        viewModel = ViewModelProvider(
            this,
            AnyViewModelFactory(
                repository = leaguesRepository
            )
        )[LeaguesViewModel::class.java]
        initRV()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.leagueList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.progressBar.visibility = View.GONE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {

        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.VISIBLE
        }
        viewModel.getAllLeagues()
    }

    private fun initRV() = with(binding) {
        adapter = LeagueAdapter(
            this@LeaguesFragment
        )
        leagueRv.layoutManager = LinearLayoutManager(context)
        leagueRv.adapter = adapter
    }

    override fun onItemClick(id: Int) {
        val action = LeaguesFragmentDirections
            .actionLeaguesFragmentToCurLeagueFragment(
                adapter.currentList[id].seasons.map { it.year }.toIntArray(),
                adapter.currentList[id].league,
                adapter.currentList[id].country.name,
            )
        findNavController().navigate(action)
    }
}