package ru.kn_n.testapplication2.presentation.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.kn_n.testapplication2.R
import ru.kn_n.testapplication2.databinding.FragmentMainBinding
import ru.kn_n.testapplication2.di.Scopes
import ru.kn_n.testapplication2.presentation.presenter.MainPresenter
import ru.kn_n.testapplication2.presentation.presenter.RepositoriesPresenter
import toothpick.Toothpick

const val USER_ID = "id"

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class MainFragment : MvpAppCompatFragment(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = Toothpick.openScope(Scopes.APP_SCOPE).getInstance(
        MainPresenter::class.java)

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        arguments?.takeIf { it.containsKey(USER_ID) }?.apply {
            viewPager = binding.viewPager
            adapter = SectionsPagerAdapter(requireActivity(), getString(USER_ID)!!)
            viewPager.adapter = adapter
        }
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = this.resources.getString(TAB_TITLES[position])
        }.attach()

        binding.signOut.setOnClickListener {
            signOut()
        }
        return binding.root
    }

    override fun signOut() {
        mainPresenter.signOut(requireContext())
    }
}