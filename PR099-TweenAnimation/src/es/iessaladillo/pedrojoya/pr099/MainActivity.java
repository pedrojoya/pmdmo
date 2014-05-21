package es.iessaladillo.pedrojoya.pr099;

import java.util.ArrayList;

import android.app.Activity;
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
        animaciones.add(AnimationUtils.loadAnimation(this,
                R.anim.translate_anticipate_repeat_reverse));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnAnimar:
            imgIcono.startAnimation(animaciones.get(numAnim++
                    % animaciones.size()));
            break;

        default:
            break;
        }

    }

}