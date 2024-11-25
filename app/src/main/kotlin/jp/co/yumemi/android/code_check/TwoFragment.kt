/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTwoBinding.bind(view)
        val repositoryDetail = args.repositoryDetail
        /*
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
                
         */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
