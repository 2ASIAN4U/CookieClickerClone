package tk.sbschools.cookieclicker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

public class MainActivity extends Activity {
    ImageView cookie,bgcshower;
    RelativeLayout background;
    static long cookieCount;
    static long cookiesPerSecond;
    TextView locationTapped;
    static TextView cookieDisp;
    TranslateAnimation floatUp;
    ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleAnimation = new ScaleAnimation(1.15f,1.0f,1.15f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(100);

        cookieCount = 0;
        locationTapped = (TextView) findViewById(R.id.locTapped);
        cookieDisp = (TextView) findViewById(R.id.textView_cookiedisp);

        cookie = (ImageView)findViewById(R.id.imageView_cookie);
        bgcshower = (ImageView)findViewById(R.id.imageView_cookieShower);
        background = (RelativeLayout)findViewById(R.id.background);

        bgcshower.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                while(cookiesPerSecond < 3){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
        }).start();

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

    public static synchronized void addCookies(long c){
        cookieCount += c;
        cookieDisp.setText(cookieCount + " cookies");
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

    public static class backgroundTask extends Thread{
        @Override
        public void run() {

        }
    }
}
