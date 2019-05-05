package com.tstv.newsapp.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.tstv.newsapp.R
import com.tstv.newsapp.internal.SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(this@SplashFragment.activity!!, R.id.nav_host_start_fragment)
        openNextView(navController)
    }

    private fun openNextView(navController: NavController) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
        val isTopicsSelected = sharedPref.getBoolean(SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED, false)
        if (isTopicsSelected) {
            navController.navigate(SplashFragmentDirections.actionToMainActivity())
        }else {
            navController.navigate(SplashFragmentDirections.actionToInterestsFragment())
        }
    }
}
