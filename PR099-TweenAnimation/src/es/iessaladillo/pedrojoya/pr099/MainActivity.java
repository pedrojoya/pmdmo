package es.iessaladillo.pedrojoya.pr099;

import java.util.ArrayList;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    private ImageView imgIcono;
    private TextView lblMensaje;
    private ArrayList<Animation> animaciones = new ArrayList<Animation>();
    private int numAnim = 0;
    private ObjectAnimator anim;
    private AnimatorSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        cargarAnimaciones();
    }

    private void getVistas() {
        ((Button) findViewById(R.id.btnAnimar)).setOnClickListener(this);
        imgIcono = (ImageView) findViewById(R.id.imgIcono);
        lblMensaje = (TextView) findViewById(R.id.lblAnimacion);
    }

    private void cargarAnimaciones() {
        // animaciones.add(AnimationUtils.loadAnimation(this, R.anim.scale));
        // animaciones.add(AnimationUtils.loadAnimation(this,
        // R.anim.translate));
        // animaciones.add(AnimationUtils.loadAnimation(this, R.anim.rotate));
        // animaciones.add(AnimationUtils.loadAnimation(this, R.anim.alpha));
        // animaciones.add(AnimationUtils.loadAnimation(this, R.anim.set));
        // animaciones.add(AnimationUtils.loadAnimation(this,
        // R.anim.secuencia));
        // animaciones.add(AnimationUtils.loadAnimation(this,
        // R.anim.translate_anticipate));
        // animaciones.add(AnimationUtils.loadAnimation(this,
        // R.anim.translate_overshoot));
        // animaciones.add(AnimationUtils.loadAnimation(this,
        // R.anim.translate_cycle));
        // animaciones.add(AnimationUtils.loadAnimation(this,
        // R.anim.translate_bounce));
        // animaciones.add(AnimationUtils.loadAnimation(this,
        // R.anim.translate_anticipate_repeat_restart));
        // animaciones.add(AnimationUtils.loadAnimation(this,
        // R.anim.translate_linear_repeat_reverse));
        animaciones.add(AnimationUtils.loadAnimation(this,
                R.anim.scale_fillbefore));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnAnimar:
            // imgIcono.startAnimation(animaciones.get(numAnim++
            // % animaciones.size()));
            // Animator anim = AnimatorInflater.loadAnimator(this,
            // R.animator.rotate_3d);
            // anim.setTarget(imgIcono);
            // anim.start();
            // Animator anim = ObjectAnimator.ofFloat(imgIcono,
            // View.TRANSLATION_X, 30.0f);
            // anim.setDuration(1000);
            // anim.start();
            // Animator anim1 = ObjectAnimator.ofFloat(lblMensaje,
            // View.TRANSLATION_Y, 20);
            // anim1.setDuration(1000);
            // Animator anim2 = ObjectAnimator.ofFloat(imgIcono,
            // View.ROTATION_X,
            // 360);
            // anim2.setDuration(1000);
            // AnimatorSet set = new AnimatorSet();
            // set.playTogether(anim1, anim2);
            // set.setDuration(1000);
            // set.start();
            // Animator anim1 = ObjectAnimator.ofFloat(lblMensaje,
            // View.TRANSLATION_Y, 20);
            // anim1.setDuration(1000);
            // Animator anim2 = ObjectAnimator.ofFloat(imgIcono,
            // View.ROTATION_X,
            // 360);
            // anim2.setDuration(1000);
            // AnimatorSet set = new AnimatorSet();
            // set.playSequentially(anim1, anim2);
            // set.start();
            // if (set == null) {
            // ObjectAnimator anim1 = ObjectAnimator.ofFloat(imgIcono,
            // View.ROTATION_X, 360);
            // anim1.setRepeatMode(ObjectAnimator.RESTART);
            // anim1.setRepeatCount(ObjectAnimator.INFINITE);
            // ObjectAnimator anim2 = ObjectAnimator.ofFloat(imgIcono,
            // View.ROTATION_Y, 360);
            // anim2.setRepeatMode(ObjectAnimator.RESTART);
            // anim2.setRepeatCount(ObjectAnimator.INFINITE);
            // set = new AnimatorSet();
            // set.playTogether(anim1, anim2);
            // set.start();
            // } else if (set.isPaused()) {
            // set.resume();
            // } else {
            // set.pause();
            // }
            imgIcono.setImageResource(R.drawable.moon);
            AnimationDrawable frameAnimation = (AnimationDrawable) imgIcono
                    .getDrawable();
            frameAnimation.start();
            break;

        default:
            break;
        }

    }

}