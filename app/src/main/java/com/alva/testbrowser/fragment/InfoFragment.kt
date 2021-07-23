package com.alva.testbrowser.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alva.testbrowser.R
import com.alva.testbrowser.databinding.FragmentInfoBinding
import com.alva.testbrowser.util.NewsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.MaterialAutoCompleteTextView

private val types = listOf(
    "T1348647853363",
    "T1467284926140",
    "T1348648517839",
    "T1348649079062"
)

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<NewsViewModel>()
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = types.size
            override fun createFragment(position: Int): Fragment {
                requireActivity().findViewById<MaterialAutoCompleteTextView>(R.id.searchEdit)
                    .addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            viewModel.getPagingData(s.toString(), types[position])
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }
                    })
                return NewsFragment(position).also { viewModel.getPagingData("", types[position]) }
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_title_tt)
                1 -> getString(R.string.tab_title_jx)
                2 -> getString(R.string.tab_title_yl)
                else -> getString(R.string.tab_title_yd)
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}