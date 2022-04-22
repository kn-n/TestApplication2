package ru.kn_n.testapplication2.presentation.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kn_n.testapplication2.presentation.view.main.repositories.FRAGMENT_STATE_ID
import ru.kn_n.testapplication2.presentation.view.main.repositories.RepositoriesFragment
import ru.kn_n.testapplication2.presentation.view.main.repositories.U_ID

class SectionsPagerAdapter(fa: FragmentActivity, private val id: String) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = RepositoriesFragment()
        fragment.arguments = Bundle().apply {
            putInt(FRAGMENT_STATE_ID, position)
            putString(U_ID, id)
        }
        return fragment
    }


}