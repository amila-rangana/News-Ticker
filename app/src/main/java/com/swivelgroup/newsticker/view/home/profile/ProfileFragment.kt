package com.swivelgroup.newsticker.view.home.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.databinding.ProfileFragmentBinding
import com.swivelgroup.newsticker.utils.PreferenceManager
import com.swivelgroup.newsticker.utils.showToast
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        preferenceManager = PreferenceManager(activity!!)

        val layoutBinding = ProfileFragmentBinding.inflate(
            inflater, container, false)
        layoutBinding.lifecycleOwner = this
        layoutBinding.viewModel = viewModel

        return layoutBinding.root
    }

    private fun setObserver(){
        viewModel.liveNewPreference.observe(this, Observer { newPref ->
            viewModel.liveAddBtnStatus.value = newPref.isNotEmpty()
            enableInviteButton(newPref.isNotEmpty())
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener {
            if (!viewModel.liveUsername.value.isNullOrEmpty()) {
                preferenceManager.saveUserName(viewModel.liveUsername.value!!)
                showToast(activity!!, getString(R.string.text_saved))
            }
        }

        btnAddPrefs.setOnClickListener {
            if (!viewModel.liveNewPreference.value.isNullOrEmpty()){
                preferenceManager.savePrefs(viewModel.liveNewPreference.value!!)
                showToast(activity!!, getString(R.string.text_saved))
                viewModel.liveNewPreference.value = ""
                showPrefs()
            }
        }

        setObserver()
        enableInviteButton(false)
    }

    override fun onStart() {
        super.onStart()
        showPrefs()
        showUserName()
    }

    private fun showUserName(){
        viewModel.liveUsername.value = preferenceManager.getUserName()
    }

    private fun showPrefs(){
        val arrayAdapter = UserPrefsRecyclerViewAdapter(preferenceManager.getPref())
        recyclerViewPreferences.setHasFixedSize(true)
        recyclerViewPreferences.layoutManager = LinearLayoutManager(activity)
        recyclerViewPreferences.adapter = arrayAdapter
    }

    private fun enableInviteButton(status: Boolean){
        viewModel.liveAddBtnStatus.value = status
        if (status) {
            btnAddPrefs.alpha = 1f
        }else {
            btnAddPrefs.alpha = 0.3f
        }
    }
}
