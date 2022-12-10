package com.example.fileexplorer.base

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {


    protected open fun replaceFragment(
        @IdRes containerViewId: Int, fragment: Fragment,
        shouldAddToStack: Boolean = false, tag: String? = null
    ) {
        val transaction = supportFragmentManager
            .beginTransaction()
        if (shouldAddToStack) {
            transaction.addToBackStack(tag)
        }
        transaction.replace(containerViewId, fragment).commitAllowingStateLoss()
    }

}