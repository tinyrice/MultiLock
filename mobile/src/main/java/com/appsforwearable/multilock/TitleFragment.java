package com.appsforwearable.multilock;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

/**
 * Created by Rice on 4/21/2015.
 */
public class TitleFragment extends Fragment{
    private ImageButton mLeftMenu;
    public static final String PREFS_NAME = "KeyFile";
    public static final String KEY_NAME = "SECRECTKEY";
    public static final String ENCODING_FORMAT = "ISO-8859-1";
    byte[] randomKey;
    String randomKeyAsString;
    Activity mActivity;
    boolean writeKey = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);

        mLeftMenu = (ImageButton) view.findViewById(R.id.id_title_left_btn);
        mLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CryptoModule mCryptoModule = new CryptoModule();
                randomKey = mCryptoModule.initSecretKey();
                try {
                    randomKeyAsString = new String(randomKey,ENCODING_FORMAT);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                writeKey = true;
                Toast.makeText(getActivity(),
                        "Resetting secret Key to:  "+mCryptoModule.showByteArray(randomKey)+" "+writeKey,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Save KEY??",""+writeKey);
        if (writeKey) {
            SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(KEY_NAME, randomKeyAsString);
            editor.commit();
        }
    }
}
