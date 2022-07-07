package com.star.cla.ui.my.no_read_notification

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.star.cla.ui.my.no_read_notification.discount.DiscountFragment
import com.star.cla.ui.my.no_read_notification.personal.PersonalFragment
import com.star.cla.ui.my.no_read_notification.pet.PetFragment

class NoReadPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragments: ArrayList<Fragment> = arrayListOf(
        DiscountFragment(),
        PersonalFragment(),
        PetFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}