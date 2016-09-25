package org.androidtown.safereturn;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 기본화면 출력
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE}, 1);
        }
        // 버튼 처리
        Button safemodeButton = (Button) findViewById(R.id.safemode);
        Button taxinumButton = (Button) findViewById(R.id.taxinum);
        Button saferegistButton = (Button) findViewById(R.id.regist);
        Button callcenterButton = (Button) findViewById(R.id.callcenter);
        Button mylocation = (Button)findViewById(R.id.mylocation);
        Button Train = (Button) findViewById(R.id.train);


        // 메인화면의 버튼 Click 처리

        // 택시 번호전송
        taxinumButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Taxi.class);
                startActivity(intent);
            }
        });

        //내위치 전송
        mylocation.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyLocation.class);
                startActivity(intent);
            }
        });

        // 안심전화번호 등록
        saferegistButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SafeRegist.class);
                startActivity(intent);
            }
        });

        // 전화센터 버튼
        callcenterButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, EmergencyCall.class);
                startActivity(intent);
            }
        });

        // 안전모드 실행
        safemodeButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Safemode.class);
                startActivity(intent);
            }
        });

        Train.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Train.class);
                startActivity(intent);
            }
        });

    }
}














