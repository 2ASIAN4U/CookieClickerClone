package tk.sbschools.cookieclicker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.animation.RotateAnimation;
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
    static TextView cookieDisp, cpsDisp, cSpawner0, cSpawner1;
    TranslateAnimation floatUp;
    ScaleAnimation scaleAnimation;
    static ImageButton cursorBTN, grandmaBTN, farmBTN, mineBTN, factoryBTN, transportBTN, alchemyBTN, portalBTN,
            timeMachineBTN, boost0, boost1, boost2;
    static TextView cursorCount, grandmaCount, farmCount, mineCount, factoryCount, transportCount, alchemyCount, portalCount,timeMachineCount, clickBoostlbl, cookieBoostlbl, potionBoostlbl;
    static int cursorNum, grandmaNum, farmNum, mineNum, factoryNum, transportNum, alchemyNum, portalNum, timeMachineNum;
    static boolean clickBoost;
    static boolean cookieBoost;
    static boolean potionBoost;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        prefs = this.getPreferences(Context.MODE_PRIVATE);
        editor = prefs.edit();

        scaleAnimation = new ScaleAnimation(1.15f,1.0f,1.15f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(100);

        if(getDouble(prefs,"cookieCount",0.0) != 0.0) {
            cookieCount = getDouble(prefs,"cookieCount",0.0);
            cookiesPerSecond = getDouble(prefs,"cpsCount",0.0);
        }else {
            cookieCount = 0;
            cookiesPerSecond = 0;
        }
        locationTapped = (TextView) findViewById(R.id.locTapped);
        cookieDisp = (TextView) findViewById(R.id.textView_cookiedisp);
        cpsDisp = (TextView) findViewById(R.id.textView_cps);
        cSpawner0 = (TextView) findViewById(R.id.CookieSpawner0);
        cSpawner1 = (TextView) findViewById(R.id.CookieSpawner1);

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
        boost0 = (ImageButton)findViewById(R.id.imageButton_cursorboost); boost0.setEnabled(false);boost0.setVisibility(View.INVISIBLE);
        boost1 = (ImageButton)findViewById(R.id.imageButton_sugarrush); boost1.setEnabled(false);boost1.setVisibility(View.INVISIBLE);
        boost2 = (ImageButton)findViewById(R.id.imageButton_potion); boost2.setEnabled(false);boost2.setVisibility(View.INVISIBLE);

        cursorCount = (TextView)findViewById(R.id.textView_clicker);
        grandmaCount = (TextView)findViewById(R.id.textView_grandma);
        farmCount = (TextView)findViewById(R.id.textView_farm);
        mineCount = (TextView)findViewById(R.id.textView_mine);
        factoryCount = (TextView)findViewById(R.id.textView_factory);
        transportCount = (TextView)findViewById(R.id.textView_transport);
        alchemyCount = (TextView)findViewById(R.id.textView_alchemy);
        portalCount = (TextView)findViewById(R.id.textView_portal);
        timeMachineCount = (TextView)findViewById(R.id.textView_timemachine);
        clickBoostlbl = (TextView)findViewById(R.id.textView_cursorboostlbl); clickBoostlbl.setVisibility(View.INVISIBLE);
        cookieBoostlbl = (TextView)findViewById(R.id.textView_sugarrushlbl); cookieBoostlbl.setVisibility(View.INVISIBLE);
        potionBoostlbl = (TextView)findViewById(R.id.textView_potionlbl); potionBoostlbl.setVisibility(View.INVISIBLE);

        cursorNum = 0;grandmaNum = 0;farmNum = 0;mineNum = 0;factoryNum = 0;transportNum = 0;alchemyNum = 0;portalNum = 0;timeMachineNum = 0;

        clickBoost = false;
        cookieBoost = false;
        potionBoost = false;

        new backgroundCPS().start();
        new passiveSave().start();

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

    public class backgroundParticles extends Thread {
        double c;
        public backgroundParticles(double c){
            this.c = c;
        }
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Math.random() > 0.5) {

                        new ParticleSystem(MainActivity.this, 80, R.drawable.backgroundcookie, 10000, R.id.background)
                                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 180, 180)
                                .setRotationSpeed(144)
                                .setAcceleration(0.00005f, 90)
                                .oneShot(cSpawner1, 1);
                    } else {
                        new ParticleSystem(MainActivity.this, 80, R.drawable.backgroundcookie, 10000, R.id.background)
                                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 0, 0)
                                .setRotationSpeed(144)
                                .setAcceleration(0.00005f, 90)
                                .oneShot(cSpawner0, 1);
                    }
                }
            });
        }
    }

    public static synchronized void addCookies(final double c){
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
        if(cookieCount>=200 && !clickBoost && boost0.getVisibility() == View.INVISIBLE){
            ScaleAnimation popAnimation = new ScaleAnimation(0f,1.1f,0f,1.1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            popAnimation.setDuration(100);
            boost0.setVisibility(View.VISIBLE);
            clickBoostlbl.setVisibility(View.VISIBLE);
            boost0.setEnabled(true);
            boost0.startAnimation(popAnimation);
        }
        if(cookieCount>=4000 && !cookieBoost && boost1.getVisibility() == View.INVISIBLE){
            ScaleAnimation popAnimation = new ScaleAnimation(0f,1.1f,0f,1.1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            popAnimation.setDuration(100);
            boost1.setVisibility(View.VISIBLE);
            cookieBoostlbl.setVisibility(View.VISIBLE);
            boost1.setEnabled(true);
            boost1.startAnimation(popAnimation);
        }
        if(cookieCount>=80000 && !potionBoost && boost2.getVisibility() == View.INVISIBLE){
            ScaleAnimation popAnimation = new ScaleAnimation(0f,1.1f,0f,1.1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            popAnimation.setDuration(100);
            boost1.setVisibility(View.VISIBLE);
            potionBoostlbl.setVisibility(View.VISIBLE);
            boost1.setEnabled(true);
            boost1.startAnimation(popAnimation);
        }


    }

    public void cookieClicked(View v){
        floatUp = new TranslateAnimation(Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,(float)(-3f + (Math.random()*2-1)));
        floatUp.setDuration(500);
        cookie.startAnimation(scaleAnimation);

        double cookiesToAdd = 1;
        if(clickBoost){
            cookiesToAdd += cookiesPerSecond/8;
        }
        if(cookieBoost){
            cookiesToAdd += cookiesPerSecond/8;
        }
        if(potionBoost){
            cookiesToAdd += cookiesPerSecond/8;
        }
        addCookies(cookiesToAdd);
        new backgroundParticles(cookiesToAdd).start();
        final TextView plusOne = new TextView(MainActivity.this);
        DecimalFormat df = new DecimalFormat("#.0");
        plusOne.setText("+" + cookiesToAdd);
        if(cookiesToAdd >= 1.1){
            plusOne.setText("+" + df.format(cookiesToAdd));
        }
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
        //From the Leonids Framework
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

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    public class passiveSave extends Thread{
        @Override
        public void run() {
            while(true) {
                putDouble(editor,"cookieCount",cookieCount);
                putDouble(editor,"cpsCount",cookiesPerSecond);
                try {
                    Thread.sleep(30000);
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
            cursorCount.startAnimation(scaleAnimation);
            cookiesPerSecond += 0.1;
            ImageView cursorImg = new ImageView(MainActivity.this);
            cursorImg.setImageResource(R.drawable.clicker);
            cursorImg.setX(cookie.getX()+cookie.getWidth()/2-60);
            cursorImg.setY(cookie.getY()-100);

            background.addView(cursorImg);

            cursorImg.animate().rotation(180).start();
            RotateAnimation rotateAnimation = new RotateAnimation(360, 0, RotateAnimation.ABSOLUTE, (cookie.getX()+cookie.getWidth()/2), RotateAnimation.ABSOLUTE, (cookie.getY()+cookie.getHeight()/2));
            rotateAnimation.setDuration(36000);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
            cursorImg.startAnimation(rotateAnimation);
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
            grandmaCount.startAnimation(scaleAnimation);
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
            farmCount.startAnimation(scaleAnimation);
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
            mineCount.startAnimation(scaleAnimation);
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
            factoryCount.setAnimation(scaleAnimation);
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
            transportCount.startAnimation(scaleAnimation);
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
            alchemyCount.startAnimation(scaleAnimation);
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
            portalCount.startAnimation(scaleAnimation);
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
            timeMachineCount.startAnimation(scaleAnimation);
            cookiesPerSecond += 260000;
        }
        if(v.equals(boost0)){
            ScaleAnimation goneAnimation = new ScaleAnimation(1.1f,0f,1.1f,0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            goneAnimation.setDuration(100);
            boost0.startAnimation(goneAnimation);
            boost0.setImageResource(R.drawable.checkmark);
            boost0.setEnabled(false);
            clickBoost = true;
        }
        if(v.equals(boost1)){
            ScaleAnimation goneAnimation = new ScaleAnimation(1.1f,0f,1.1f,0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            goneAnimation.setDuration(100);
            boost1.startAnimation(goneAnimation);
            boost1.setImageResource(R.drawable.checkmark);
            boost1.setEnabled(false);
            cookieBoost = true;
        }
        if(v.equals(boost2)){
            ScaleAnimation goneAnimation = new ScaleAnimation(1.1f,0f,1.1f,0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            goneAnimation.setDuration(100);
            boost2.startAnimation(goneAnimation);
            boost2.setImageResource(R.drawable.checkmark);
            boost2.setEnabled(false);
            potionBoost = true;
        }
        DecimalFormat df = new DecimalFormat("#.0");
        cpsDisp.setText("per second: " + df.format(cookiesPerSecond));
    }
}
