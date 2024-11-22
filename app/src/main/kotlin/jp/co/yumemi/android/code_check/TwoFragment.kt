/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        _binding = FragmentTwoBinding.bind(view)

        val repositoryInfo = args.repositoryInfo

        binding.ownerIconView.load(repositoryInfo.ownerIconUrl)
        binding.nameView.text = repositoryInfo.name
        binding.languageView.text = getString(R.string.written_language, repositoryInfo.language)
        binding.starsView.text = "${repositoryInfo.stargazersCount} stars"
        binding.watchersView.text = "${repositoryInfo.watchersCount} watchers"
        binding.forksView.text = "${repositoryInfo.forksCount} forks"
        binding.openIssuesView.text = "${repositoryInfo.openIssuesCount} open issues"
    }
}
