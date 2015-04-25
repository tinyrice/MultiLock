package com.appsforwearable.multilock;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.Key;

public class MainActivity extends Activity {
    public static final String ENCODING_FORMAT = "ISO-8859-1";

    private TextView mTokendisplay;
    private Button mGetToken;
    byte[] randomKey,encryptData;
    String randomKeyAsString;
    String encryptedDataAsString;
    int encryptedCode;
    int mCounter = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTokendisplay = (TextView) stub.findViewById(R.id.tokendisplay);
                mGetToken = (Button) stub.findViewById(R.id.gettoken);




                mGetToken.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final CryptoModule mCryptoModule = new CryptoModule();
                        randomKey = mCryptoModule.initSecretKey();
                        try {
                            randomKeyAsString = new String(randomKey,ENCODING_FORMAT);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Key mKey_Binary = mCryptoModule.toKey(randomKey);
                        byte[] mc= (mCounter+"").getBytes();


                        try {
                            encryptData = mCryptoModule.encrypt(mc,mKey_Binary);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {
                            encryptedDataAsString = new String(encryptData,ENCODING_FORMAT);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        encryptedCode=Math.abs(encryptedDataAsString.hashCode());

                        mTokendisplay.setText(""+encryptedCode);



                    }
                });





            }
        });







    }


}
