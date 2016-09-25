package org.androidtown.safereturn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by 김 혁 진 on 2016-06-09.
 */

public class Safemode extends Activity implements SensorEventListener {

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;
    private static final int SHAKE_THRESHOLD = 1500;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;
    private static boolean chek;
    public String text;
    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    @SuppressLint("NewApi") //충돌가능성이있는 코드를 사용할때 사용
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.safemode);
        SharedPreferences pref = getSharedPreferences("pref", //작은데이터베이스 같은거 키값사용
                Activity.MODE_PRIVATE);
        EditText edit1 = (EditText) this.findViewById(R.id.text1);
        CheckBox check1 = (CheckBox) findViewById(R.id.CheckBox1);
        text = pref.getString("editText", ""); // 저장된 값 불러오기
        chek = pref.getBoolean("check1", false);
        if (text.isEmpty()) {
            Toast.makeText(getBaseContext(), "전화번호가 등록되어있지않습니다",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        if (!(chek)) {
            Toast.makeText(getBaseContext(), "안전모드 설정이 되어있지않습니다", Toast.LENGTH_SHORT).show();
            finish();
        }
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager //가속도 센서를 이용한 흔들림감지
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
        finish();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    Double latitude;
    Double longitude;

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis(); //현재 시간을 ms단위로 구함
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) { //gap이 100ms보다 크면..
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];
                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime
                        * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    // 이벤트 발생!!
                    LocationManager locationManager;
                    String context = Context.LOCATION_SERVICE;
                    locationManager = (LocationManager) getSystemService(context);

                    // GPS 이용할떄 설정하고자하는 것을 설정하기위해 사용
                    Criteria criteria = new Criteria(); //내위치 찾는부분
                    criteria.setAccuracy(Criteria.ACCURACY_COARSE);// 정확도(위도와 경도의 정확도를 설정)
                    criteria.setPowerRequirement(Criteria.POWER_LOW); // 전원 소비량
                    criteria.setAltitudeRequired(false); // 고도 사용여부
                    criteria.setBearingRequired(false); // 방향정보를 설정
                    criteria.setSpeedRequired(false); // 속도정보 제공여부
                    criteria.setCostAllowed(true); // 금전적비용들어도 상관없는지


                    String provider = locationManager.getBestProvider(criteria, true); //현재 위치값을 가져오기 위한 프로바이더
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location lastlocation = locationManager.getLastKnownLocation(provider);//마지막으로 얻은 위치보기
                    Toast.makeText(getApplicationContext(),lastlocation.toString(),Toast.LENGTH_SHORT).show();
                    if(lastlocation != null)
                    {
                        latitude = lastlocation.getLatitude();
                        longitude = lastlocation.getLongitude();
                    }
                    //double latitude = location.getLatitude(); // 위도
                    //double longitude = location.getLongitude(); // 경도

                    Geocoder gcK = new Geocoder(getApplicationContext(), Locale.KOREA);//주소를 확인하기위한 지오코더 //좌표를 주소로 바꿔주기위해 사용

                    try {
                        List<Address> addresses = gcK.getFromLocation(latitude,longitude, 1);
                        StringBuilder sb = new StringBuilder();
                        if (addresses.size() > 0) {
                            for (Address addr : addresses) { //위도와 경도를 이용해 주소를 가져온다.
                                sb.append(addr.getMaxAddressLineIndex())
                                        .insert(0, "*긴급상황*\n");
                                for (int i = 0; i < addr
                                        .getMaxAddressLineIndex(); i++)
                                    sb.append(addr.getAddressLine(i)).append(
                                            "<< \n\n");
                            }
                            Address address = addresses.get(0);
                            sb.delete(7, 8);
                            sb.append(address.getCountryName()).append(" ");
                            sb.append(address.getLocality()).append(" ");
                            sb.append(address.getThoroughfare()).append(" ");
                            //sb.append(address.getFeatureName()).append("\n");


                            Toast.makeText(getBaseContext(), sb.toString(),Toast.LENGTH_SHORT).show();
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(text, null, sb.toString() + "에서 도움을 요청하고있습니다. ", null, null);
                            setContentView(R.layout.sub_safemode);

                        }

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                lastX = event.values[DATA_X];

                lastY = event.values[DATA_Y];

                lastZ = event.values[DATA_Z];

            }
        }
    }
}