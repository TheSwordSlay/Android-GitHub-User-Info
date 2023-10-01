package com.fiqri.githubusersinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqri.githubusersinfo.data.response.ItemsItem
import com.fiqri.githubusersinfo.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding

    companion object {
        var ARG_POSITION = "section_number"
        var ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 0)!!
        val username = arguments?.getString(ARG_USERNAME)!!

        val followModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowFragmentModel::class.java)
        followModel.listDataUsers.observe(viewLifecycleOwner) { listDataUsers ->
            setFollowList(listDataUsers)
        }
        followModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


        val layoutManager = LinearLayoutManager(requireActivity())
        binding.listFollow.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.listFollow.addItemDecoration(itemDecoration)

        followModel.getFollowList(username, position)
    }

    private fun setFollowList(usersData: List<ItemsItem>) {
        val adapter = FollowDataAdapter()
        adapter.submitList(usersData)
        binding.listFollow.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}