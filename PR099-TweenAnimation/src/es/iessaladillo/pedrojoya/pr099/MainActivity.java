package es.iessaladillo.pedrojoya.pr099;

import java.util.ArrayList;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
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
        // animaciones.add(AnimationUtils.loadAnimation(this, R.anim.fill));
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
            // View.TRANSLATION_X, imgIcono.getTranslationX() + 50.0f);
            // anim.setDuration(500);
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

            // Animator anim1 = ObjectAnimator.ofFloat(imgIcono,
            // View.TRANSLATION_Y, 100);
            // Animator anim2 = ObjectAnimator.ofFloat(imgIcono,
            // View.ROTATION_X,
            // 360);
            // AnimatorSet set = new AnimatorSet();
            // set.playTogether(anim1, anim2);
            // set.setDuration(2000);
            // set.setInterpolator(new AccelerateDecelerateInterpolator());
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
            // imgIcono.setImageResource(R.drawable.moon);
            // AnimationDrawable frameAnimation = (AnimationDrawable) imgIcono
            // .getDrawable();
            // frameAnimation.start();

            // // Se obtiene un animador que anime la propiedad RotationX del
            // botón
            // // btnBoton de manera que pase de 0 grados a 360 grados.
            // ObjectAnimator animador = ObjectAnimator.ofFloat(imgIcono,
            // View.ROTATION_X, 0.0f, 360.0f);
            // // Se termina de configurar el animador estableciendo su
            // duración,
            // // el interpolador que debe usarse, el modo de repetición y el
            // // número de repeticiones adicionales.
            // animador.setDuration(2000);
            // animador.setInterpolator(new AccelerateInterpolator());
            // animador.setRepeatMode(ObjectAnimator.REVERSE);
            // animador.setRepeatCount(3);
            // // Se establece el listener de la animación.
            // animador.addListener(new AnimatorListener() {
            // // Cuando se inicia la animación.
            // @Override
            // public void onAnimationStart(Animator animation) {
            // lblMensaje.setText("Comienza la animación");
            // }
            //
            // // Cuando se inicia una repetición.
            // @Override
            // public void onAnimationRepeat(Animator animation) {
            // lblMensaje.setText("Se inicia una repetición");
            // }
            //
            // // Cuando finaliza la animación.
            // @Override
            // public void onAnimationEnd(Animator animation) {
            // lblMensaje.setText("Animación finalizada");
            // }
            //
            // // Cuando se cancela la animación.
            // @Override
            // public void onAnimationCancel(Animator animation) {
            // // TODO Auto-generated method stub
            // }
            // });
            // // Se inicia la animación.
            // animador.start();

            // float scale = getResources().getDisplayMetrics().density;
            // ObjectAnimator anim1 = ObjectAnimator.ofFloat(imgIcono,
            // View.TRANSLATION_X, 0.0f, 100.0f * scale);
            // ObjectAnimator anim2 = ObjectAnimator.ofFloat(imgIcono,
            // View.TRANSLATION_Y, 0.0f, 100.0f * scale);
            // ObjectAnimator anim3 = ObjectAnimator.ofFloat(imgIcono,
            // View.TRANSLATION_X, 100.0f * scale, 0.0f);
            // ObjectAnimator anim4 = ObjectAnimator.ofFloat(imgIcono,
            // View.TRANSLATION_Y, 100.0f * scale, 0.0f);
            // ObjectAnimator anim5 = ObjectAnimator.ofFloat(imgIcono,
            // View.SCALE_X, 1.0f, 2.0f);
            // ObjectAnimator anim6 = ObjectAnimator.ofFloat(imgIcono,
            // View.SCALE_Y, 1.0f, 2.0f);
            // AnimatorSet animSet = new AnimatorSet();
            // animSet.play(anim1).before(anim2);
            // animSet.play(anim3).after(anim2);
            // animSet.play(anim3).with(anim4).with(anim5).with(anim6);
            // animSet.play(anim1).after(100);
            // animSet.setDuration(1000);
            // animSet.setInterpolator(new LinearInterpolator());
            // animSet.start();

            // lblMensaje.setText(imgIcono.getLeft() + "");
            // imgIcono.animate().translationX(100).translationY(100).scaleX(2)
            // .scaleY(2).setDuration(4000)
            // .setInterpolator(new LinearInterpolator()).rotationY(180)
            // .setListener(new AnimatorListener() {
            //
            // @Override
            // public void onAnimationStart(Animator animation) {
            // // TODO Auto-generated method stub
            //
            // }
            //
            // @Override
            // public void onAnimationRepeat(Animator animation) {
            // // TODO Auto-generated method stub
            //
            // }
            //
            // @Override
            // public void onAnimationEnd(Animator animation) {
            // lblMensaje.setText(imgIcono.getTranslationX() + "");
            // imgIcono.animate().translationX(0).translationY(0)
            // .scaleX(1).scaleY(1).setDuration(4000)
            // .setInterpolator(new LinearInterpolator())
            // .rotationY(0)
            // .setListener(new AnimatorListener() {
            //
            // @Override
            // public void onAnimationStart(
            // Animator arg0) {
            // // TODO Auto-generated method stub
            //
            // }
            //
            // @Override
            // public void onAnimationRepeat(
            // Animator arg0) {
            // // TODO Auto-generated method stub
            //
            // }
            //
            // @Override
            // public void onAnimationEnd(Animator arg0) {
            // imgIcono.clearAnimation();
            //
            // }
            //
            // @Override
            // public void onAnimationCancel(
            // Animator arg0) {
            // // TODO Auto-generated method stub
            //
            // }
            // });
            //
            // }
            //
            // @Override
            // public void onAnimationCancel(Animator animation) {
            // // TODO Auto-generated method stub
            //
            // }
            // });

            // ObjectAnimator animador = ObjectAnimator.ofObject(imgIcono,
            // "backgroundColor", new ArgbEvaluator(), Color.RED,
            // Color.GREEN);
            // animador.setDuration(4000);
            // animador.setInterpolator(new LinearInterpolator());
            // animador.start();

            // Se obtiene el rectángulo original de visualizaciónd de la vista
            // y se crean los rectángulos para los valores de la animación.
            Rect rectOriginal = new Rect();
            imgIcono.getLocalVisibleRect(rectOriginal);
            Rect rectDesde = new Rect(rectOriginal);
            Rect rectHacia = new Rect(rectOriginal);
            // Se modifican los rectángulos de la animación.
            rectDesde.bottom = rectDesde.top + rectOriginal.height() / 4;
            rectHacia.left = rectHacia.left + rectOriginal.width() / 4;
            rectHacia.right = rectHacia.right - rectOriginal.width() / 4;
            // Se crea el animador.
            ObjectAnimator animador = ObjectAnimator.ofObject(imgIcono,
                    "clipBounds", new RectEvaluator(), rectDesde, rectHacia);
            animador.setDuration(2000);
            animador.setInterpolator(new LinearInterpolator());
            // Se inicia la animación.
            animador.start();
            break;

        default:
            break;
        }

    }
}