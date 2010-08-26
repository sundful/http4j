/**
 * Copyright (C) 2010 Zhang, Guilin <guilin.zhang@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.http4j.client.metrics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class AggregatedNanoSecondTimer extends NanoSecondTimer implements AggregatedTimer {
	
	@Override
	public void aggregate(Timer timer) {
		minStart(timer.getStart());
		maxStop(timer.getStop());
	}

	protected void minStart(long t) {
		while(true) {
			long s = getStart();
			long min = Math.min(s, t);
			if(((AtomicLong) start).compareAndSet(s, min)) {
				break;
			}
		}
	}
	
	protected void maxStop(long t) {
		while(true) {
			long s = getStop();
			long max = Math.max(s, t);
			if(((AtomicLong) stop).compareAndSet(s, max)) {
				break;
			}
		}
	}
	
	@Override
	public void reset() {
		start = new AtomicLong(0);
		stop = new AtomicLong(0);
	}

	@Override
	protected long getCurrentTime() {
		long time = super.getCurrentTime();
		return new AtomicLong(time).get();
	}
}