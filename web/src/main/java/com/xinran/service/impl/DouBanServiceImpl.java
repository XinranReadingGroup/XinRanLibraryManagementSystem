package com.xinran.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.xinran.service.DouBanService;

/**
 * 
 * 
 * 
 * @author zhuangyao.zy
 *
 */
public class DouBanServiceImpl implements DouBanService {
	
	private static final String API_URL_PREFIX = "https://api.douban.com/v2/book";
	private static final String API_GET_BOOK_BY_ISBN = API_URL_PREFIX + "/isbn/";
	
	private static final String CHARSET_DEFAULT = "UTF-8";
	
	private static final Logger LOG = LoggerFactory.getLogger(DouBanServiceImpl.class);
	
	
	private final CloseableHttpClient http = HttpClients.createDefault();

	@Override
	public Map<String, Object> getBookByISBN(String isbn) {
		HttpGet get = new HttpGet(API_GET_BOOK_BY_ISBN + isbn);
		String resp = request(get);
		if(StringUtils.isNotBlank(resp)){
			return JSON.parseObject(resp);
		}
		return null;
	}
	
	
	private String request(HttpRequestBase reqBase){
		CloseableHttpResponse resp = null;
		try {
			resp = http.execute(reqBase);
			HttpEntity entity = resp.getEntity();
			Header header = entity.getContentEncoding();
			String charset = CHARSET_DEFAULT;
			if(header!=null && StringUtils.isNotBlank(header.getValue())){
				charset = header.getValue();
			}
			Reader reader = new InputStreamReader(entity.getContent(), charset);
			char[] buffer = new char[128];
			StringBuilder content = new StringBuilder(128);
			for(int count = reader.read(buffer); count > 0; count = reader.read(buffer)){
				content.append(buffer, 0, count);
			}
			return content.toString();
		} catch (Exception e) {
			LOG.error("Error to execute http request", e);
		}finally{
			if(resp != null){
				try {
					resp.close();
				} catch (IOException e) {
					LOG.error("Error to close http response", e);
				}
			}
		}
		return null;
	}
	
	
}
