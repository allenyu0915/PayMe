package com.example.allen.payme;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NameInputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NameInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NameInputFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static NameInputFragment newInstance() {
        NameInputFragment fragment = new NameInputFragment();
        return fragment;
    }

    public NameInputFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button nextBtn = (Button) getActivity().findViewById(R.id.next_btn_to_amount_input);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read content in text fields
                for (int i = 0; i < MainActivity.numPersons; i++) {
                    // Get text field
                    EditText nameInput = (EditText) getActivity().findViewById(i);
                    String name = nameInput.getText().toString().trim();

                    // Assign name to map of amount paid
                    MainActivity.amountPaid.put(name, new Double(0));
                }

                // Create new amount input fragment
                AmountInputFragment nameInput = new AmountInputFragment();

                // Replace fragment with transaction manager
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, nameInput);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_input, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
