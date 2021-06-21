package io.github.noahzu.androidnewteclearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        MessageAutoSender().start()
    }

    class MessageAutoSender : Thread() {
        var dispatcher = EventDispatcherProxy()
        override fun run() {
            super.run()
            for (i in 0..999) {
                val packet = MessagePacket(i, "这是第\$i 个消息")
                dispatcher.dispatch(packet)
                try {
                    sleep(5000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}