package com.example.allen.payme;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AmountInputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AmountInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmountInputFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static AmountInputFragment newInstance() {
        AmountInputFragment fragment = new AmountInputFragment();
        return fragment;
    }

    public AmountInputFragment() {
        // Set up names layout
        LinearLayout amountsLayout = (LinearLayout) getActivity().findViewById(R.id.names_layout);
        Set<String> nameList = MainActivity.amountPaid.keySet();
        int i = 0;
        for (String name : nameList) {
            // Create line X
            LinearLayout line = new LinearLayout(getActivity());
            line.setOrientation(LinearLayout.HORIZONTAL);

            // Create person X text view
            TextView personLabel = new TextView(getActivity());
            personLabel.setText(name + ": ");

            // Create input field
            EditText amountInput = new EditText(getActivity());
            amountInput.setId(i);

            // Set input type to decimal
            amountInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            // Add in text view and input field
            line.addView(personLabel);
            line.addView(amountInput);

            // Add input area to entire layout
            amountsLayout.addView(line);

            i++;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button nextBtn = (Button) getActivity().findViewById(R.id.next_btn_to_calculate);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read content in text fields
                Set<String> nameList = MainActivity.amountPaid.keySet();
                int i = 0;
                for (String name : nameList) {
                    // Get amount values
                    EditText amountInput = (EditText) getActivity().findViewById(i);
                    Double amount = new Double(-1);
                    try {
                        amount = Double.parseDouble(amountInput.getText().toString());
                    } catch (NumberFormatException e) {
                        // Show error input
                    }

                    // Assign amount to names
                    MainActivity.amountPaid.put(name, amount);

                    i++;
                }

                // Create new calculated results fragment
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
        return inflater.inflate(R.layout.fragment_amount_input, container, false);
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
