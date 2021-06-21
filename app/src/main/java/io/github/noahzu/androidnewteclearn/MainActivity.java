package io.github.noahzu.androidnewteclearn;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MessageAutoSender().start();
    }

    static class MessageAutoSender extends Thread {
        EventDispatcherProxy dispatcher = new EventDispatcherProxy();

        @Override
        public void run() {
            super.run();
            for (int i = 0;i<1000;i++) {
                MessagePacket packet = new MessagePacket(i,"这是第$i 个消息");
                dispatcher.dispatch(packet);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

