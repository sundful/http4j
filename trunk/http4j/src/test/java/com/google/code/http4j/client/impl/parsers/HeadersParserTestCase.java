package com.google.code.http4j.client.impl.parsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.client.HttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersParserTestCase {

	@Test
	public void testParse() throws IOException {
		//assertion("Content-Type:text/html", new String[] { "Content-Type", "text/html" });
		assertion("Content-Type:text/html\r\nContent-Location:www.google.com", 
				new String[] {"Content-Type", "text/html"}, 
				new String[] {"Content-Location", "www.google.com"});
	}

	private void assertion(String source, String[]... values)
			throws IOException {
		InputStream input = new ByteArrayInputStream(source.getBytes());
		Map<String, HttpHeader> headers = new HeadersParser().parse(input);
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), values.length);
		for (String[] header: values) {
			Assert.assertTrue(headers.containsKey(header[0]));
			Assert.assertEquals(headers.get(header[0]).getValue(), header[1]);
		}
	}
}
