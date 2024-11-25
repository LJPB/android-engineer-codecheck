/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.data.http.NetworkState
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryInfo
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryDetail
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.ui.component.repository_search.RepositoryList
import jp.co.yumemi.android.code_check.ui.RepositorySearchViewModel
import jp.co.yumemi.android.code_check.ui.ViewModelProvider
import java.util.Date

class OneFragment : Fragment(R.layout.fragment_one) {

    private val repositorySearchViewModel: RepositorySearchViewModel by viewModels { ViewModelProvider.repositorySearch() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentOneBinding.bind(view)
        val context = requireContext()
        binding.repositoryList.setContent {
            val repositoryList by repositorySearchViewModel.repositorySearchResult.collectAsState()
            MaterialTheme {
                RepositoryList(
                    repositoryDetailList = repositoryList.repositories,
                    itemOnClick = { gotoRepositoryFragment(it) }
                )
            }
        }
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action != EditorInfo.IME_ACTION_SEARCH) return@setOnEditorActionListener false
                val searchWord = editText.text.toString()
                if (searchWord.isNotBlank()) {
                    if (NetworkState.isActiveNetwork(context)) {
                        lastSearchDate = Date()
                        repositorySearchViewModel.searchWithWord(searchWord)
                    }
                }
                return@setOnEditorActionListener true
            }
    }

    private fun gotoRepositoryFragment(repositoryDetail: RepositoryDetail) {
        val repositoryInfo = RepositoryInfo(
            name = repositoryDetail.fullName,
            ownerIconUrl = repositoryDetail.owner.avatarUrl,
            language = repositoryDetail.language ?: "",
            stargazersCount = repositoryDetail.stargazersCount,
            watchersCount = repositoryDetail.watchersCount,
            forksCount = repositoryDetail.forksCount,
            openIssuesCount = repositoryDetail.openIssuesCount
        )
        val action = OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(repositoryInfo = repositoryInfo)
        findNavController().navigate(action)
    }
}
