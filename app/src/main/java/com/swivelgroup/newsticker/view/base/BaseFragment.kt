package com.swivelgroup.newsticker.view.base


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.utils.Constants
import com.swivelgroup.newsticker.utils.showAlertDialog
import kotlinx.android.synthetic.main.head_line_fragment.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(activity).apply {
            setText(R.string.hello_blank_fragment)
        }
    }

    fun handleErrors(errorCode: String?){
        val errorMessage = when(errorCode){
            Constants.error_api_key_diabled -> getString(R.string.error_api_key_disabled)
            Constants.error_api_key_exhausted -> getString(R.string.error_api_key_exhausted)
            Constants.error_api_key_missing -> getString(R.string.error_api_key_missing)
            Constants.error_api_key_invalid -> getString(R.string.error_api_key_invalid)
            Constants.error_parameter_missing -> getString(R.string.error_parameter_missing)
            Constants.error_parameter_invalid -> getString(R.string.error_parameter_invalid)
            Constants.error_sources_does_not_exist -> getString(R.string.error_sources_does_not_exist)
            Constants.error_sources_too_many -> getString(R.string.error_sources_too_many)
            Constants.error_rate_limited -> getString(R.string.error_rate_limited)
            Constants.error_unexpected -> getString(R.string.error_unexpected)
            else -> getString(R.string.error_common)
        }

        showAlertDialog(activity!!, "Info", errorMessage)
    }

//    fun handleError(throwable: Throwable?, isConnected: Boolean){
//
//        var errorMessage = getString(R.string.error_common)
//
//        if (!isConnected) {
//            errorMessage = activity!!.getString(R.string.error_no_connection)
//        } else {
//            when (throwable) {
//                is ConnectException -> {
//                    errorMessage = activity!!.getString(R.string.error_failed_to_connect_to_server)
//                }
//                is TimeoutException -> {
//                    errorMessage = activity!!.getString(R.string.error_timeout)
//                }
//                is SocketTimeoutException -> {
//                    errorMessage = activity!!.getString(R.string.error_timeout)
//                }
//            }
//        }
//    }
}
