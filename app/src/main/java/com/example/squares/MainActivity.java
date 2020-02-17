package com.example.squares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button run;
    private TextView question, YourAnswers, RealAnswers;
    private RadioButton xs,sqr;
    private EditText t,a,enter;
    private int mode = 1, time, amount, progress;
    private String newQuestion, newAnswer,newRealAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progressBar = findViewById(R.id.progressBar);
        run = findViewById(R.id.run);
        question = findViewById(R.id.question);
        YourAnswers = findViewById(R.id.youranswers);
        RealAnswers = findViewById(R.id.realanswers);
        xs = findViewById(R.id.xs);
        sqr = findViewById(R.id.sqr);
        t = findViewById(R.id.time);
        a = findViewById(R.id.amount);
        enter = findViewById(R.id.answer);

        xs.setChecked(true);
        t.setText("5");
        a.setText("5");
        amount = 5;
        time = 5;

        xs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 1;
                xs.setChecked(true);
                sqr.setChecked(false);
            }
        });
        sqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 2;
                xs.setChecked(false);
                sqr.setChecked(true);
            }
        });
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = Integer.parseInt(t.getText().toString());
                amount = Integer.parseInt(a.getText().toString());
                new MyThread();
                RealAnswers.setText("Правильные ответы:");
                YourAnswers.setText("Ваши ответы:");
            }
        });
    }

    public class MyThread extends Thread {

        // Конструктор
        MyThread() {
            // Создаём новый поток
            super("Второй поток");
            start(); // Запускаем поток
        }

        public void run() {
            try {
                progress = 100;
                for(int e = 0; e<100; e++){
                    progress--;
                    SETprogress(progress);
                    Thread.sleep(10);
                }

                for (int i = 1; i <= amount; i++) {
                    progress = 100;
                    int n = (int) (100000+(Math.random()*999999));

                    if(mode == 1) Mode1();
                    if(mode == 2) Mode2();

                    for(int e = 0; e<100; e++){
                        progress--;
                        SETprogress(progress);
                        Thread.sleep(time*10);
                    }
                    setParams();
                    Thread.sleep(10);
                }


            } catch (InterruptedException e) {
                //...
            }
        }
        public void Mode1(){
            int A,B,C,x1,x2,k;
            x1 = (int)(-15+(Math.random()*30));
            x2 = (int)(-15+(Math.random()*30));
            k = (int)(-3+(Math.random()*3));
            C = x1*x2;
            B = -1*(x1+x2);
            A = 1;
            if(k != 0){
                A*=k;
                B*=k;
                C*=k;
            }
            String Bs, Cs;
            if(B>=0) {
                Bs = "+"+Integer.toString(B);
            } else {
                Bs = Integer.toString(B);
            }
            if(C>=0) {
                Cs = "+"+Integer.toString(C);
            }else{
                Cs = Integer.toString(C);
            }
            newQuestion = (Integer.toString(A)+"x"+"\u00B2"+Bs+"x"+Cs+"=0");
            setQuestion();
            newRealAnswer = "{"+x1+";"+x2+"}";
        }
        public void Mode2(){
            int bool, x;
            x = (int)(11+(Math.random()*25));
            bool = (int)(0+(Math.random()*1));
            if(bool == 0) {
                newRealAnswer = "{"+Integer.toString(x*x)+"}";
                newQuestion = Integer.toString(x);
                setQuestion();
            }else{
                newRealAnswer = "{"+Integer.toString(x)+"}";
                newQuestion = Integer.toString(x*x);
                setQuestion();
            }
        }
    }

    public void setParams(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                newAnswer = "{"+enter.getText().toString()+"}";
                YourAnswers.setText(YourAnswers.getText()+" "+newAnswer);
                RealAnswers.setText(RealAnswers.getText()+" "+newRealAnswer);

            }
        });
    }
    public void SETprogress(int progress){
        progressBar.setProgress(progress);
    }
    public void setQuestion(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                question.setText(newQuestion);
            }
        });
    }

}
