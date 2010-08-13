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

package com.google.code.http4j.client.impl.utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class IOUtils {

	private IOUtils() {
	}

	public static void close(Socket socket) {
		if (null != socket && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Extract buffer data from 0 to the position after flip.
	 * 
	 * @param buffer
	 * @return bytes
	 */
	public static byte[] extract(ByteBuffer buffer) {
		buffer.flip();
		byte[] data = new byte[buffer.limit()];
		System.arraycopy(buffer.array(), 0, data, 0, data.length);
		return data;
	}
}
