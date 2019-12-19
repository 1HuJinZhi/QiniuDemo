package com.hujz.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/12
 *     desc   : 快速实现懒加载
 * </pre>
 */
class ViewPagerAdapter(
    fm: FragmentManager,
    private val fragmentList: List<Fragment>,
    private val titleList: List<String>? = null,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fm, behavior) {

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titleList == null || position >= titleList.size)
            super.getPageTitle(position)
        else
            titleList[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}