package tk.sbschools.cookieclicker;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import java.text.DecimalFormat;

public class MainActivity extends Activity {
    ImageView cookie,bgcshower;
    RelativeLayout background;
    static double cookieCount;
    static double cookiesPerSecond;
    TextView locationTapped;
    static TextView cookieDisp, cpsDisp;
    TranslateAnimation floatUp;
    ScaleAnimation scaleAnimation;
    static ImageButton cursorBTN, grandmaBTN, farmBTN, mineBTN, factoryBTN, transportBTN, alchemyBTN, portalBTN,
            timeMachineBTN, boost0, boost1, boost2;
    static TextView cursorCount, grandmaCount, farmCount, mineCount, factoryCount, transportCount, alchemyCount, portalCount,timeMachineCount;
    static int cursorNum, grandmaNum, farmNum, mineNum, factoryNum, transportNum, alchemyNum, portalNum, timeMachineNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        scaleAnimation = new ScaleAnimation(1.15f,1.0f,1.15f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(100);

        if(savedInstanceState != null) {
            cookieCount = savedInstanceState.getDouble("savedCookies");
            cookiesPerSecond = savedInstanceState.getDouble("savedCPS");
        }else {
            cookieCount = 0;
            cookiesPerSecond = 0;
        }
        locationTapped = (TextView) findViewById(R.id.locTapped);
        cookieDisp = (TextView) findViewById(R.id.textView_cookiedisp);
        cpsDisp = (TextView) findViewById(R.id.textView_cps);

        cookie = (ImageView)findViewById(R.id.imageView_cookie);
        bgcshower = (ImageView)findViewById(R.id.imageView_cookieShower);
        background = (RelativeLayout)findViewById(R.id.background);

        bgcshower.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                while(cookiesPerSecond < 2){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bgcshower.setVisibility(View.VISIBLE);
                        AlphaAnimation fadeIn = new AlphaAnimation(0.0f,0.8f);
                        fadeIn.setDuration(500);
                        bgcshower.startAnimation(fadeIn);
                        fadeIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                TranslateAnimation bgcookieshower = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.3f,Animation.RELATIVE_TO_SELF,0.3f,Animation.RELATIVE_TO_SELF,-0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                                bgcookieshower.setDuration(5000);
                                bgcookieshower.setInterpolator(new LinearInterpolator());
                                bgcookieshower.setRepeatCount(Animation.INFINITE);

                                bgcshower.setAnimation(bgcookieshower);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                    }
                });
            }
        }).start();

        //Mass Button Initialize
        cursorBTN = (ImageButton)findViewById(R.id.imageButton_clicker); cursorBTN.setEnabled(false);
        grandmaBTN = (ImageButton)findViewById(R.id.imageButton_grandma); grandmaBTN.setEnabled(false);
        farmBTN = (ImageButton)findViewById(R.id.imageButton_farm); farmBTN.setEnabled(false);
        mineBTN = (ImageButton)findViewById(R.id.imageButton_mine); mineBTN.setEnabled(false);
        factoryBTN = (ImageButton)findViewById(R.id.imageButton_factory); factoryBTN.setEnabled(false);
        transportBTN = (ImageButton)findViewById(R.id.imageButton_transport); transportBTN.setEnabled(false);
        alchemyBTN = (ImageButton)findViewById(R.id.imageButton_alchemy); alchemyBTN.setEnabled(false);
        portalBTN = (ImageButton)findViewById(R.id.imageButton_portal); portalBTN.setEnabled(false);
        timeMachineBTN = (ImageButton)findViewById(R.id.imageButton_timemachine); timeMachineBTN.setEnabled(false);
        boost0 = (ImageButton)findViewById(R.id.imageButton_cursorboost); boost0.setEnabled(false);
        boost1 = (ImageButton)findViewById(R.id.imageButton_sugarrush); boost1.setEnabled(false);
        boost2 = (ImageButton)findViewById(R.id.imageButton_potion); boost2.setEnabled(false);

        cursorCount = (TextView)findViewById(R.id.textView_clicker);
        grandmaCount = (TextView)findViewById(R.id.textView_grandma);
        farmCount = (TextView)findViewById(R.id.textView_farm);
        mineCount = (TextView)findViewById(R.id.textView_mine);
        factoryCount = (TextView)findViewById(R.id.textView_factory);
        transportCount = (TextView)findViewById(R.id.textView_transport);
        alchemyCount = (TextView)findViewById(R.id.textView_alchemy);
        portalCount = (TextView)findViewById(R.id.textView_portal);
        timeMachineCount = (TextView)findViewById(R.id.textView_timemachine);

        cursorNum = 0;grandmaNum = 0;farmNum = 0;mineNum = 0;factoryNum = 0;transportNum = 0;alchemyNum = 0;portalNum = 0;timeMachineNum = 0;


        new backgroundCPS().start();

        cookie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                locationTapped.setX(cookie.getX() + event.getX());
                locationTapped.setY(cookie.getY() + event.getY());
                //System.out.println(locationTapped.getX() + ", " +  locationTapped.getY());
                //cookieClicked(v);
                return false;
            }
        });
    }

    public static synchronized void addCookies(double c){
        cookieCount += c;
        System.out.print(cookieCount);
        cookieDisp.setText((int)cookieCount + " cookies");
        if(cookieCount>=(Math.pow(1.15,cursorNum) * 15))
            cursorBTN.setEnabled(true);
        else
            cursorBTN.setEnabled(false);
        if(cookieCount>=(Math.pow(1.15,grandmaNum) * 100))
            grandmaBTN.setEnabled(true);
        else
            grandmaBTN.setEnabled(false);
        if(cookieCount>=(Math.pow(1.15,farmNum) * 1100))
            farmBTN.setEnabled(true);
        else
            farmBTN.setEnabled(false);
        if(cookieCount>=(Math.pow(1.15,mineNum) * 12000))
            mineBTN.setEnabled(true);
        else
            mineBTN.setEnabled(false);
        if(cookieCount>=(Math.pow(1.15,factoryNum) * 130000))
            factoryBTN.setEnabled(true);
        else
            factoryBTN.setEnabled(false);
        if(cookieCount>=(Math.pow(1.15,transportNum) * 1400000))
            transportBTN.setEnabled(true);
        else
            transportBTN.setEnabled(false);
        if(cookieCount>=(Math.pow(1.15,alchemyNum) * 20000000))
            alchemyBTN.setEnabled(true);
        else
            alchemyBTN.setEnabled(false);
        if(cookieCount>=(Math.pow(1.15,portalNum) * 330000000))
            portalBTN.setEnabled(true);
        else
            portalBTN.setEnabled(false);
        if(cookieCount>=(Math.pow(1.15,timeMachineNum) * 5100000000f))
            timeMachineBTN.setEnabled(true);
        else
            timeMachineBTN.setEnabled(false);

    }

    public void cookieClicked(View v){
        floatUp = new TranslateAnimation(Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,(float)(-3f + (Math.random()*2-1)));
        floatUp.setDuration(500);
        cookie.startAnimation(scaleAnimation);
        addCookies(1);
        final TextView plusOne = new TextView(MainActivity.this);
        plusOne.setText("+1");
        plusOne.setTextColor(Color.WHITE);
        plusOne.setX(locationTapped.getX()-50);
        plusOne.setY(locationTapped.getY()-50);
        background.addView(plusOne);
        plusOne.startAnimation(floatUp);
        floatUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {            }
            @Override
            public void onAnimationEnd(Animation animation) {
                AlphaAnimation fade = new AlphaAnimation(1.0f,0.0f);
                fade.setDuration(50);
                plusOne.startAnimation(fade);
                fade.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        background.removeView(plusOne);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
            @Override
            public void onAnimationRepeat(Animation animation) {            }
        });
        new ParticleSystem(this, 1, R.drawable.minicookie , 1000)
                .setSpeedModuleAndAngleRange(0.1f, 0.2f,250,290)
                .setRotationSpeed(144)
                .setAcceleration(0.0005f, 90)
                .setFadeOut(250)
                .oneShot(locationTapped, 1); //location
    }

    public class backgroundCPS extends Thread{
        @Override
        public void run() {
            while(true) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addCookies(cookiesPerSecond / 5);
                    }
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void buttonClicked(View v){
        if(v.equals(cursorBTN)){
            if(cursorNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,cursorNum) * 15));
                cursorNum++;

            }else {
                addCookies(-15);
                cursorNum++;
            }
            cursorCount.setText("x"+cursorNum);
            cookiesPerSecond += 0.1;
        }
        if(v.equals(grandmaBTN)){
            if(grandmaNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,grandmaNum) * 100));
                grandmaNum++;
            }else {
                addCookies(-100);
                grandmaNum++;
            }
            grandmaCount.setText("x"+grandmaNum);
            cookiesPerSecond += 1;
        }
        if(v.equals(farmBTN)){
            if(farmNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,farmNum) * 1100));
                farmNum++;
            }else {
                addCookies(-1100);
                farmNum++;
            }
            farmCount.setText("x"+farmNum);
            cookiesPerSecond += 8;
        }
        if(v.equals(mineBTN)){
            if(mineNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,mineNum) * 12000));
                mineNum++;
            }else {
                addCookies(-12000);
                mineNum++;
            }
            mineCount.setText("x"+mineNum);
            cookiesPerSecond += 47;
        }
        if(v.equals(factoryBTN)){
            if(factoryNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,factoryNum) * 130000));
                factoryNum++;
            }else {
                addCookies(-130000);
                factoryNum++;
            }
            factoryCount.setText("x"+factoryNum);
            cookiesPerSecond += 260;
        }
        if(v.equals(transportBTN)){
            if(transportNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,transportNum) * 1400000));
                transportNum++;
            }else {
                addCookies(-1400000);
                transportNum++;
            }
            transportCount.setText("x"+transportNum);
            cookiesPerSecond += 1400;
        }
        if(v.equals(alchemyBTN)){
            if(alchemyNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,alchemyNum) * 20000000));
                alchemyNum++;
            }else {
                addCookies(-20000000);
                alchemyNum++;
            }
            alchemyCount.setText("x"+alchemyNum);
            cookiesPerSecond += 7800;
        }
        if(v.equals(portalBTN)){
            if(portalNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,portalNum) * 330000000));
                portalNum++;
            }else {
                addCookies(-330000000);
                portalNum++;
            }
            portalCount.setText("x"+portalNum);
            cookiesPerSecond += 44000;
        }
        if(v.equals(timeMachineBTN)){
            if(timeMachineNum >= 1) {
                addCookies(-(int) (Math.pow(1.15,timeMachineNum) * 5100000000f));
                timeMachineNum++;
            }else {
                addCookies(-5100000000.0);
                timeMachineNum++;
            }
            timeMachineCount.setText("x"+timeMachineNum);
            cookiesPerSecond += 260000;
        }
        DecimalFormat df = new DecimalFormat("#.0");
        cpsDisp.setText("per second: " + df.format(cookiesPerSecond));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble("savedCookies",cookieCount);
        outState.putDouble("savedCPS",cookiesPerSecond);
        super.onSaveInstanceState(outState);
    }
}
