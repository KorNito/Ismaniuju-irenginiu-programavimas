package com.example.lab5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TextStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextStatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TextStatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TextStatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TextStatisticsFragment newInstance(String param1, String param2) {
        TextStatisticsFragment fragment = new TextStatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        final TextView textview2 = (TextView) view.findViewById(R.id.textView);
        String passedString = getArguments().getString("editText");

        int lowerCaseSymbolCount = 0;
        int upperCaseSymbolCount = 0;
        int vowelCount = 0;

        for (int i = 0; i < passedString.length(); i++) {
            switch (Character.toLowerCase(passedString.charAt(i))) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                    vowelCount++;
                    break;
            }
        }

        for(int i = 0; i < passedString.length(); i++){
            if(Character.isLowerCase(passedString.charAt(i))) {
                lowerCaseSymbolCount++;
            }
            if(Character.isUpperCase(passedString.charAt(i))) {
                upperCaseSymbolCount++;
            }
        }

        // Inflate the layout for this fragment
        String message = "Teksto ilgis " + passedString.length() + " simbolių." + "\n" +
                "Tarp jų yra " + vowelCount + " balsių." + "\n" +
                "Tarp jų yra " + lowerCaseSymbolCount + " mažųjų ir " + upperCaseSymbolCount + " raidžių.";
        textview2.setText(message);
        return view;
    }
}