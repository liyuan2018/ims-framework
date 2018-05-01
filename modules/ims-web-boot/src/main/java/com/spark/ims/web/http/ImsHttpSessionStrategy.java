package com.spark.ims.web.http;

import org.springframework.session.Session;
import org.springframework.session.web.http.HttpSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liyuan on 2018/4/26.
 */
public class ImsHttpSessionStrategy implements HttpSessionStrategy {

	private HttpSessionStrategy browser;
	private HttpSessionStrategy api;
	private HttpSessionStrategy token;

	public ImsHttpSessionStrategy(HttpSessionStrategy browser, HttpSessionStrategy api, HttpSessionStrategy token) {
		this.browser = browser;
		this.api = api;
		this.token = token;
	}

	
	public String getRequestedSessionId(HttpServletRequest request) {
		return getStrategy(request).getRequestedSessionId(request);
	}

	
	public void onNewSession(Session session, HttpServletRequest request,
			HttpServletResponse response) {
		getStrategy(request).onNewSession(session, request, response);
	}

	public void onInvalidateSession(HttpServletRequest request,
			HttpServletResponse response) {
		getStrategy(request).onInvalidateSession(request, response);
	}

	private HttpSessionStrategy getStrategy(HttpServletRequest request) {
		return api.getRequestedSessionId(request) != null ? api : (token
				.getRequestedSessionId(request) != null ? token : browser);

	}
}
