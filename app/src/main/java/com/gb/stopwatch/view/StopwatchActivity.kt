package com.gb.stopwatch.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gb.stopwatch.*
import com.gb.stopwatch.data.ElapsedTimeCalculator
import com.gb.stopwatch.domain.TimestampProvider
import com.gb.stopwatch.viewmodel.StopwatchListOrchestrator
import com.gb.stopwatch.data.datasource.StopwatchStateHolder
import com.gb.stopwatch.data.repository.StopwatchStateCalculator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class StopwatchActivity : AppCompatActivity() {

    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
			println("VVV получение милисекунд : object TimestampProvider")
            return System.currentTimeMillis()
        }
    }
    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
			)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_stopwtch)

		val textView = findViewById<TextView>(R.id.text_time)
		CoroutineScope(
			Dispatchers.Main
					+ SupervisorJob()
		).launch {
			stopwatchListOrchestrator.ticker.collect {
				textView.text = it
			}
		}

		findViewById<Button>(R.id.button_start).setOnClickListener {
			stopwatchListOrchestrator.start()
		}
		findViewById<Button>(R.id.button_pause).setOnClickListener {
			stopwatchListOrchestrator.pause()
		}
		findViewById<Button>(R.id.button_stop).setOnClickListener {
			stopwatchListOrchestrator.stop()
		}
	}
}















