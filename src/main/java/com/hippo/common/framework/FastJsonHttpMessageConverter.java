package com.hippo.common.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hippo.common.util.general.Constants;
import com.hippo.common.util.general.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *  类功能说明 使spring mvc与fastjson结合  
 * <p>
 * Title: FastJsonHttpMessageConverter.java
 * </p>
 *  
 */

public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

	private static final Logger log = LoggerFactory.getLogger(FastJsonHttpMessageConverter.class);

	public final static Charset UTF8 = Charset.forName("UTF-8");

	private Charset charset = UTF8;

	private SerializerFeature[] serializerFeature;

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = inputMessage.getBody();
			
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (charset == UTF8) {
			return JSON.parseObject(sb.toString(), clazz);
		} else {
			return JSON.parseObject(sb.toString().getBytes(), 0, sb.toString().getBytes().length, charset.newDecoder(), clazz);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		Map<String, Object> map = JSON.parseObject(JSON.toJSONString(obj), HashMap.class);
		if (map.get(Constants.RESULT_CODE) != null)
			map.put(Constants.RESULT_CODE,
					Utils.getProjectCode() + Utils.getResultCodeFormat(Integer.parseInt(map.get(Constants.RESULT_CODE).toString())));

		OutputStream out = outputMessage.getBody();
		byte[] bytes;

		if (charset == UTF8) {
			if (serializerFeature != null) {
				bytes = JSON.toJSONBytes(map, serializerFeature);
			} else {
				bytes = JSON.toJSONBytes(map);
			}

		} else {
			String text;
			if (serializerFeature != null) {
				text = JSON.toJSONString(map, serializerFeature);
			} else {
				text = JSON.toJSONString(map);
			}
			bytes = text.getBytes(charset);
		}

		out.write(bytes);

		log.info("send message {} ", JSON.toJSONString(map));
	}

}
