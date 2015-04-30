package com.appsforwearable.multilock;


import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.Key;


public class ContentFragment extends Fragment {
    public static final String PREFS_NAME = "KeyFile";
    public static final String KEY_NAME = "SECRECTKEY";
    public static final String ENCODING_FORMAT = "ISO-8859-1";
    private Activity mActivity;
    EditText mPassword, mToken;
    CheckBox mShowPassword, mShowToken;
    TextView mDebugTextfield1, mDebugTextfield2;

    TokenGen mTokenGen;
    int token;

    byte[] randomKey;
    byte[] encryptData;
    String randomKeyAsString;
    boolean writeKey = false;
    private int mCounter = 100;
//    int encryptedCode;
    String encryptedDataAsString;


    public ContentFragment() {

    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);

        randomKeyAsString = settings.getString(KEY_NAME, "NO KEY");
//       String counterAsString = mCounter+"";


        if (randomKeyAsString == "NO KEY") {
            try {
                mTokenGen = new TokenGen(mCounter, ENCODING_FORMAT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                randomKeyAsString = mTokenGen.getkey();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Toast.makeText(mActivity, "Key not found,initiating", Toast.LENGTH_SHORT).show();

        } else {
            try {
                mTokenGen = new TokenGen(randomKeyAsString, mCounter, ENCODING_FORMAT);
            } catch (UnsupportedEncodingException e) {


            }
        }

        try {
            token = mTokenGen.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }







        /*CryptoModule mCryptoModule = new CryptoModule();

        randomKeyAsString = settings.getString(KEY_NAME, "NO KEY");


        if (randomKeyAsString == "NO KEY") {
            randomKey = mCryptoModule.initSecretKey();
            try {
                randomKeyAsString = new String(randomKey, ENCODING_FORMAT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            writeKey = true;
        } else {
            try {
                randomKey = randomKeyAsString.getBytes(ENCODING_FORMAT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        Key mKey_Binary = mCryptoModule.toKey(randomKey);
        byte[] mc = (mCounter + "").getBytes();


        try {
            encryptData = mCryptoModule.encrypt(mc, mKey_Binary);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            encryptedDataAsString = new String(encryptData, ENCODING_FORMAT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/


        try {
            mDebugTextfield1.setText("Current Key:" + new CryptoModule().showByteArray(randomKeyAsString.getBytes(ENCODING_FORMAT)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("CONTENT", "Encrypted: " + mTokenGen.showEncryptedData());
        mDebugTextfield2.setText("Encrypted: " + token);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_content, container, false);


        mPassword = (EditText) rootview.findViewById(R.id.passwordinputbox);
        mToken = (EditText) rootview.findViewById(R.id.tokeninputbox);
        mShowPassword = (CheckBox) rootview.findViewById(R.id.showPassword);
        mShowToken = (CheckBox) rootview.findViewById(R.id.showToken);
        mDebugTextfield1 = (TextView) rootview.findViewById(R.id.debugtextfield1);
        mDebugTextfield2 = (TextView) rootview.findViewById(R.id.debugtextfield2);

        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                else
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }

        });

        mShowToken.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    mToken.setTransformationMethod(PasswordTransformationMethod.getInstance());
                else
                    mToken.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }

        });

        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();
        if (writeKey) {

            SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(KEY_NAME, randomKeyAsString);
            editor.commit();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_verify) {
            Toast.makeText(mActivity, "Verify", Toast.LENGTH_SHORT).show();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}