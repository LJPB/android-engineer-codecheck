/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding
import jp.co.yumemi.android.code_check.ui.ViewModelProvider
import jp.co.yumemi.android.code_check.ui.screen.repository_detail.RepositoryDetailScreen
import jp.co.yumemi.android.code_check.ui.screen.repository_detail.RepositoryDetailViewModel

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("検索した日時", lastSearchDate.toString())
        _binding = FragmentTwoBinding.bind(view)
        val repositoryDetail = args.repositoryDetail

        // TODO FragmentをComposeに完全に置き換えた時にもうすこしうまくやる
        val viewModel: RepositoryDetailViewModel by viewModels {
            ViewModelProvider.repositoryDetail(
                repositoryDetail
            )
        }
        binding.repositoryDetailScreen.setContent {
            val adaptiveInfo = currentWindowAdaptiveInfo()
            MaterialTheme {
                Surface {
                    RepositoryDetailScreen(
                        viewModel = viewModel,
                        windowWidthSizeClass = adaptiveInfo.windowSizeClass.windowWidthSizeClass
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
