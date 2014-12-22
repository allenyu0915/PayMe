package com.example.allen.payme;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalculatedResults.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalculatedResults#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatedResults extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatedResults.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatedResults newInstance(String param1, String param2) {
        CalculatedResults fragment = new CalculatedResults();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalculatedResults() {
        // Required empty public constructor
    }

    private Map<String, Double> calculateBalance() {

        // Calculate total amount
        double total = 0;
        Set<String> nameList = MainActivity.amountPaid.keySet();
        for (String name : nameList) {
            total += MainActivity.amountPaid.get(name);
        }

        // Calculate amount per person
        double amountPerPerson = total / MainActivity.numPersons;

        // Calculate balance
        Map<String, Double> balance = new Map<String, Double>() {
            @Override
            public void clear() {

            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @NonNull
            @Override
            public Set<Entry<String, Double>> entrySet() {
                return null;
            }

            @Override
            public Double get(Object key) {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @NonNull
            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Double put(String key, Double value) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends Double> map) {

            }

            @Override
            public Double remove(Object key) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public Collection<Double> values() {
                return null;
            }
        };

        for (String name : nameList) {
            balance.put(name, MainActivity.amountPaid.get(name) - amountPerPerson);     // positive balance if you paid more
        }
        return balance;
    }

    private void payDebt (Map<String, Double> balance, Map<String, ArrayList<Double>> amountToPay, String payee) {
        ArrayList<Double> debt = new ArrayList<Double>();
        for (int i = 0; i < balance.size(); i++) {
            debt.add(new Double(0));
        }
        Set<String> nameList = MainActivity.balance.keySet();
        while (balance.get(payee) < -0.001) {
            for (String paidTo : nameList) {
                if (balance.get(paidTo) > 0) {
                    double difference = balance.get(payee) + balance.get(paidTo);
                    double balancePaidTo = balance.get(paidTo);
                    double balancePayee = balance.get(payee);
                    if (difference < -0.001) {                      // payee owes more than paidTo is owed
                        balance.put(paidTo, new Double(0));
                        balance.put(payee, balancePayee + balancePaidTo);
                        debt.add(balancePaidTo);
                    } else if (difference > 0.001) {               // payee owes less than paidTo is owed
                        balance.put(paidTo, balancePaidTo + balancePayee);
                        balance.put(payee, new Double(0));
                        debt.add(-balancePayee);
                    } else {
                        balance.put(paidTo, new Double(0));
                        balance.put(payee, new Double(0));
                        debt.add(balancePaidTo);
                    }
                }
            }
        }
        amountToPay.put(payee, debt);
    }

    private void displayOutput(Map<String, ArrayList<Double>> amountToPay, ArrayList<String> names) {
        Set<String> nameList = MainActivity.amountToPay.keySet();
        for (String name : nameList) {
            ArrayList<Double> toPay = amountToPay.get(name);
            for (int j = 0; j < amountToPay.size(); j++) {
                if (toPay.get(j) > 0) {
                    // display output
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Find balance of each person
        MainActivity.balance = calculateBalance();

        // Display how much to pay each other
        Set<String> nameList = MainActivity.balance.keySet();
        for (String name : nameList) {
            if (MainActivity.balance.get(name) < 0) {
                // calculates who owes who how much
                payDebt(MainActivity.balance, MainActivity.amountToPay, name);
            }
        }

        // Display output
        displayOutput(MainActivity.amountToPay, nameList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculated_results, container, false);
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
