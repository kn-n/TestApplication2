package ru.kn_n.testapplication2.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = RepositoriesFragment()
        fragment.arguments = Bundle().apply {
            putInt(FRAGMENT_STATE_ID, position)
        }
        return fragment
    }


}