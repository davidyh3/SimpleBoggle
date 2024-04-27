package com.android.simpleboggle.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.simpleboggle.R
import com.android.simpleboggle.call.NewGameCallback
import com.android.simpleboggle.call.SubmitCallback
import com.android.simpleboggle.databinding.ActivityMainBinding
import com.android.simpleboggle.fragment.DashboardFragment
import com.android.simpleboggle.fragment.MainFragment
import com.android.simpleboggle.viewmodel.GameViewModel
import com.gyf.immersionbar.ImmersionBar

class MainActivity : AppCompatActivity(), NewGameCallback, SubmitCallback {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mainFragment by lazy { MainFragment(this) }
    private val dashboardFragment by lazy { DashboardFragment(this) }

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.apply {
            ImmersionBar.with(this@MainActivity).titleBar(toolbar).statusBarDarkFont(true).navigationBarColor(R.color.white).init()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fl_main, mainFragment)
            fragmentTransaction.add(R.id.fl_dashboard, dashboardFragment)
            fragmentTransaction.commitNowAllowingStateLoss()
        }
        gameViewModel.loadLocalDict()
    }

    override fun onSubmitClick(score: Int) {
        dashboardFragment.onSubmitClick(score)
    }

    override fun onNewGameClick() {
        mainFragment.onNewGameClick()
    }
}