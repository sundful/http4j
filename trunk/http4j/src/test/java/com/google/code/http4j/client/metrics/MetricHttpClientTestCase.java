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

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class MetricHttpClientTestCase {
	
	/*
	 * While test suite executes, the other cases might also access thread local metrics.
	 * Thus some metrics in that thread might have initialized values.
	 * So we need to test it in separated thread.
	 */
	@Test
	public void testMetricsInSeparatedThread() throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<Boolean> result = service.submit(new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception {
				try {
					testMetrics();
					return true;
				} catch(Exception e) {
					return false;
				}
			}});
		Assert.assertTrue(result.get());
	}
	
	
	public void testMetrics() throws IOException {
		ThreadLocalMetrics metrics = ThreadLocalMetrics.getInstance();
		Timer dns = metrics.getDNSTimer();
		Timer connection = metrics.getConnectionTimer();
		Assert.assertFalse(dns.getTimeCost() > 0);
		Assert.assertFalse(connection.getTimeCost() > 0);
		byte[] response = new MetricHttpClient().executeHead("http://www.google.com");
		Assert.assertNotNull(response);
		Assert.assertTrue(dns.getTimeCost() > 0);
		Assert.assertTrue(connection.getTimeCost() > 0);
	}
}
