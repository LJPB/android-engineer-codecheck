/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.data.http.github.GitHubStatusCodes
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryInfo
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryItem
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.ui.RepositorySearchViewModel
import jp.co.yumemi.android.code_check.ui.ViewModelProvider
import jp.co.yumemi.android.code_check.view.OnItemClickListener
import jp.co.yumemi.android.code_check.view.RepositoryListAdapter
import kotlinx.coroutines.launch
import java.util.Date

class OneFragment : Fragment(R.layout.fragment_one) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOneBinding.bind(view)
        val context = requireContext()

        val repositorySearchViewModel: RepositorySearchViewModel by viewModels { ViewModelProvider.repositorySearch() }

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        val adapter = RepositoryListAdapter(object : OnItemClickListener {
            override fun itemClick(item: RepositoryItem) {
                gotoRepositoryFragment(item)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repositorySearchViewModel.repositorySearchResult.collect {
                    if (it.status == GitHubStatusCodes.SUCCESS) adapter.submitList(it.repositories)
                }
            }
        }

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action != EditorInfo.IME_ACTION_SEARCH) return@setOnEditorActionListener false
                val searchWord = editText.text.toString()
                if (searchWord.isNotBlank()) {
                    repositorySearchViewModel.searchWithWord(searchWord)
                    lastSearchDate = Date()
                }
                return@setOnEditorActionListener true
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    private fun gotoRepositoryFragment(repositoryItem: RepositoryItem) {
        val repositoryInfo = RepositoryInfo(
            name = repositoryItem.fullName,
            ownerIconUrl = repositoryItem.owner.avatarUrl,
            language = repositoryItem.language ?: "",
            stargazersCount = repositoryItem.stargazersCount,
            watchersCount = repositoryItem.watchersCount,
            forksCount = repositoryItem.forksCount,
            openIssuesCount = repositoryItem.openIssuesCount
        )
        val action = OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(repositoryInfo = repositoryInfo)
        findNavController().navigate(action)
    }
}
