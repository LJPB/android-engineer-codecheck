/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryInfo
import jp.co.yumemi.android.code_check.data.structure.github.RepositoryDetail
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.ui.screen.repository_search.RepositorySearchViewModel
import jp.co.yumemi.android.code_check.ui.ViewModelProvider
import jp.co.yumemi.android.code_check.ui.screen.repository_search.RepositorySearchScreen

class OneFragment : Fragment(R.layout.fragment_one) {

    private val repositorySearchViewModel: RepositorySearchViewModel by viewModels { ViewModelProvider.repositorySearch() }

    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOneBinding.bind(view)
        binding.repositorySearchScreen.setContent {
            MaterialTheme {
                Surface {
                    RepositorySearchScreen(
                        viewModel = repositorySearchViewModel,
                        repositoryOnClick = this::gotoRepositoryFragment
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
