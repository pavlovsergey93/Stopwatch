package com.gb.stopwatch.data.datasource

import com.gb.stopwatch.view.StopwatchState
import com.gb.stopwatch.utils.TimestampMillisecondsFormatter
import com.gb.stopwatch.utils.ZERO_MARKER
import com.gb.stopwatch.data.repository.StopwatchStateCalculator

class StopwatchStateHolder(
	private val stopwatchStateCalculator: StopwatchStateCalculator,
) {
	private val timestampMillisecondsFormatter = TimestampMillisecondsFormatter()

	var currentState: StopwatchState = StopwatchState.Paused(ZERO_MARKER)
		private set

	fun start() {
		currentState = stopwatchStateCalculator.calculateRunningState(currentState)
	}

	fun pause() {
		currentState = stopwatchStateCalculator.calculatePausedState(currentState)
	}

	fun stop() {
		currentState = StopwatchState.Paused(ZERO_MARKER)
	}

	fun getStringTimeRepresentation(): String {
		val elapsedTime = when (val currentState = currentState) {
			is StopwatchState.Paused -> currentState.elapsedTime
			is StopwatchState.Running -> stopwatchStateCalculator.elapsedTimeCalculator.calculate(currentState)
		}
		return timestampMillisecondsFormatter.format(elapsedTime)
	}
}