package org.androidtown.safereturn;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by 김 혁 진 on 2016-06-09.
 */
public class EmergencyCall extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergencycall);

        Button bt1 = (Button) findViewById(R.id.call1b);
        Button bt2 = (Button) findViewById(R.id.call2b);
        Button bt3 = (Button) findViewById(R.id.call3b);
        Button bt4 = (Button) findViewById(R.id.call4b);
        Button bt5 = (Button) findViewById(R.id.call5b);
        Button bt6 = (Button) findViewById(R.id.call6b);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);

    }

    public void onClick(View arg0) {

        switch (arg0.getId()) {

            case R.id.call1b:

                startActivity(new Intent("android.intent.action.CALL",
                        Uri.parse("te l:112")));
                break;

            case R.id.call2b:

                startActivity(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:119")));

                break;
            case R.id.call3b:

                startActivity(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:1366")));

                break;
            case R.id.call4b:

                startActivity(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:1391")));

                break;
            case R.id.call5b:

                startActivity(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:182")));

                break;
            case R.id.call6b:

                startActivity(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:02-2075-8777")));

                break;

        }

    }

}
