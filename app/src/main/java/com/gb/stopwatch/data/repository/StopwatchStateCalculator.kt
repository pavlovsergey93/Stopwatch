package com.gb.stopwatch.data.repository

import com.gb.stopwatch.data.ElapsedTimeCalculator
import com.gb.stopwatch.view.StopwatchState
import com.gb.stopwatch.domain.TimestampProvider

class StopwatchStateCalculator(
	private val timestampProvider: TimestampProvider,
	val elapsedTimeCalculator: ElapsedTimeCalculator,
) {

	fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
		when (oldState) {
			is StopwatchState.Running -> oldState

			is StopwatchState.Paused -> {
				StopwatchState.Running(
					startTime = timestampProvider.getMilliseconds(),
					elapsedTime = oldState.elapsedTime
				)
			}
		}

	fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
		when (oldState) {
			is StopwatchState.Running -> {
				val elapsedTime = elapsedTimeCalculator.calculate(oldState)
				StopwatchState.Paused(elapsedTime = elapsedTime)
			}
			is StopwatchState.Paused -> oldState
		}
}