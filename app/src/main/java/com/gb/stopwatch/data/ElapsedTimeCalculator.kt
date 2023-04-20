package com.gb.stopwatch.data

import com.gb.stopwatch.domain.TimestampProvider
import com.gb.stopwatch.view.StopwatchState
import com.gb.stopwatch.utils.ZERO_MARKER


class ElapsedTimeCalculator(
	private val timestampProvider: TimestampProvider,
) {

	fun calculate(state: StopwatchState.Running): Long =
		state.elapsedTime + if (timestampProvider.getMilliseconds() > state.startTime) {
			timestampProvider.getMilliseconds() - state.startTime
		} else {
			ZERO_MARKER
		}
}