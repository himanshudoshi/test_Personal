package ca.virginmobile.myaccount.virginmobile.ui.usage.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.virginmobile.myaccount.virginmobile.R
import ca.virginmobile.myaccount.virginmobile.util.OnFragmentInteractionListener

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [UsageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsageFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_usage, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (mListener != null) {
            mListener!!.onFragmentInteraction(Uri.parse(this::class.java.simpleName))
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + getString(R.string.must_impl_onfragmentInteraction_listener))
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment UsageFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): UsageFragment {
            val fragment = UsageFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
