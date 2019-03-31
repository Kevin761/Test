package pl.edu.pwr.student.s235958.ballgame;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor sensor;
    BallActivity ballActivity;
    Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BallActivity ballActivity = (BallActivity) findViewById(R.id.ball);
        //Route route = (Route) findViewById(R.id.route);


        Thread t = new Thread(new BallThread(new Handler(), ballActivity));
        t.start();


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        playerLose();


    }

    public void playerLose(){
        if(ballActivity.isGameOver())
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT);
            toast.show();
            ballActivity.resetGame();
        }
    }


    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {

        ballActivity.setVx((int) event.values[0]);
        ballActivity.setVy((int) event.values[1]);

        ballActivity.resetGame();


        /*textView1.setText("X: " + Float.toString(linear_acceleration[0]));
        textView2.setText("Y: " + Float.toString(linear_acceleration[1]));
        textView3.setText("Z: " + Float.toString(linear_acceleration[2]));*/


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
