package com.spark.ims.web.http;
/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import com.spark.ims.auth.constants.ITokenConstants;
import org.springframework.session.Session;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.session.web.http.MultiHttpSessionStrategy;
import com.spark.ims.web.session.ImsSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * A {@link HttpSessionStrategy} that uses a cookie to obtain the session from.
 * Specifically, this implementation will allow specifying a cookie name using
 * {@link org.springframework.session.web.http.CookieHttpSessionStrategy#setCookieName(String)}. The default is
 * "SESSION".
 *
 * When a session is created, the HTTP response will have a cookie with the
 * specified cookie name and the value of the session id. The cookie will be
 * marked as a session cookie, use the context path for the path of the cookie,
 * marked as HTTPOnly, and if
 * {@link javax.servlet.http.HttpServletRequest#isSecure()} returns true, the
 * cookie will be marked as secure. For example:
 *
 * <pre>
 * HTTP/1.1 200 OK
 * Set-Cookie: SESSION=f81d4fae-7dec-11d0-a765-00a0c91e6bf6; Path=/context-root; Secure; HttpOnly
 * </pre>
 *
 * The client should now include the session in each request by specifying the
 * same cookie in their request. For example:
 *
 * <pre>
 * GET /messages/ HTTP/1.1
 * Host: example.com
 * Cookie: SESSION=f81d4fae-7dec-11d0-a765-00a0c91e6bf6
 * </pre>
 *
 * When the session is invalidated, the server will send an HTTP response that
 * expires the cookie. For example:
 *
 * <pre>
 * HTTP/1.1 200 OK
 * Set-Cookie: SESSION=f81d4fae-7dec-11d0-a765-00a0c91e6bf6; Expires=Thur, 1 Jan 1970 00:00:00 GMT; Secure; HttpOnly
 * </pre>
 *
 * <h2>Supporting Multiple Simultaneous Sessions</h2>
 *
 * <p>
 * By default multiple sessions are also supported. Once a session is
 * established with the browser, another session can be initiated by specifying
 * a unique value for the {@link #setSessionAliasParamName(String)}. For
 * example, a request to:
 * </p>
 *
 * <pre>
 * GET /messages/?_s=1416195761178 HTTP/1.1
 * Host: example.com
 * Cookie: SESSION=f81d4fae-7dec-11d0-a765-00a0c91e6bf6
 * </pre>
 *
 * Will result in the following response:
 *
 * <pre>
 *  HTTP/1.1 200 OK
 * Set-Cookie: SESSION="0 f81d4fae-7dec-11d0-a765-00a0c91e6bf6 1416195761178 8a929cde-2218-4557-8d4e-82a79a37876d"; Expires=Thur, 1 Jan 1970 00:00:00 GMT; Secure; HttpOnly
 * </pre>
 *
 * <p>
 * To use the original session a request without the HTTP parameter u can be
 * made. To use the new session, a request with the HTTP parameter
 * _s=1416195761178 can be used. By default URLs will be rewritten to include the
 * currently selected session.
 * </p>
 *
 * <h2>Selecting Sessions</h2>
 *
 * <p>
 * Sessions can be managed by using the HttpSessionManager and
 * SessionRepository. If you are not using Spring in the rest of your
 * application you can obtain a reference from the HttpServletRequest
 * attributes. An example is provided below:
 * </p>
 *
 * {@code
 *      HttpSessionManager sessionManager =
 *              (HttpSessionManager) req.getAttribute(HttpSessionManager.class.getName());
 *      SessionRepository<Session> repo =
 *              (SessionRepository<Session>) req.getAttribute(SessionRepository.class.getName());
 *
 *      String currentSessionAlias = sessionManager.getCurrentSessionAlias(req);
 *      Map<String, String> sessionIds = sessionManager.getSessionIds(req);
 *      String newSessionAlias = String.valueOf(System.currentTimeMillis());
 *
 *      String contextPath = req.getContextPath();
 *      List<Account> accounts = new ArrayList<>();
 *      Account currentAccount = null;
 *      for(Map.Entry<String, String> entry : sessionIds.entrySet()) {
 *          String alias = entry.getKey();
 *          String sessionId = entry.getValue();
 *
 *          Session session = repo.getSession(sessionId);
 *          if(session == null) {
 *              continue;
 *          }
 *
 *          String username = session.getAttribute("username");
 *          if(username == null) {
 *              newSessionAlias = alias;
 *              continue;
 *          }
 *
 *          String logoutUrl = sessionManager.encodeURL("./logout", alias);
 *          String switchAccountUrl = sessionManager.encodeURL("./", alias);
 *          Account account = new Account(username, logoutUrl, switchAccountUrl);
 *          if(currentSessionAlias.equals(alias)) {
 *              currentAccount = account;
 *          } else {
 *              accounts.add(account);
 *          }
 *      }
 *
 *      req.setAttribute("currentAccount", currentAccount);
 *      req.setAttribute("addAccountUrl", sessionManager.encodeURL(contextPath, newSessionAlias));
 *      req.setAttribute("accounts", accounts);
 * }
 *
 *
 * Created by liyuan on 2018/4/26.
 */
// 从spring的CookieHttpSessionStrategy复制， 因CookieHttpSessionStrategy无法继承，新版本的也无法满足多端对过期时间的动态性要求和切换，故这么做
public final class ImsCookieHttpSessionStrategy implements MultiHttpSessionStrategy, HttpSessionManager {
	private static final String SESSION_IDS_WRITTEN_ATTR = CookieHttpSessionStrategy.class.getName().concat(".SESSIONS_WRITTEN_ATTR");

	static final String DEFAULT_ALIAS = "0";

	static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_s";

	private Pattern ALIAS_PATTERN = Pattern.compile("^[\\w-]{1,50}$");

	private String cookieName = "SESSION";

	private String sessionParam = DEFAULT_SESSION_ALIAS_PARAM_NAME;

	private boolean isServlet3Plus = isServlet3();
	private CookieHttpSessionStrategy cookieHttpSessionStrategy = new CookieHttpSessionStrategy();
	private int maxInactiveIntervalInSeconds;

	public ImsCookieHttpSessionStrategy(int maxInactiveIntervalInSeconds) {
		this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
	}

	public String getRequestedSessionId(HttpServletRequest request) {
		return cookieHttpSessionStrategy.getRequestedSessionId(request);
	}

	public String getCurrentSessionAlias(HttpServletRequest request) {
		return cookieHttpSessionStrategy.getCurrentSessionAlias(request);
	}

	public String getNewSessionAlias(HttpServletRequest request) {
		return cookieHttpSessionStrategy.getNewSessionAlias(request);
	}

	public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
		Set<String> sessionIdsWritten = getSessionIdsWritten(request);
		if(sessionIdsWritten.contains(session.getId())) {
			return;
		}
		sessionIdsWritten.add(session.getId());

		Map<String,String> sessionIds = getSessionIds(request);
		String sessionAlias = getCurrentSessionAlias(request);
		sessionIds.put(sessionAlias, session.getId());
		Cookie sessionCookie = createSessionCookie(request, sessionIds);
		response.addCookie(sessionCookie);
	}

	@SuppressWarnings("unchecked")
	private Set<String> getSessionIdsWritten(HttpServletRequest request) {
		Set<String> sessionsWritten = (Set<String>) request.getAttribute(SESSION_IDS_WRITTEN_ATTR);
		if(sessionsWritten == null) {
			sessionsWritten = new HashSet<>();
			request.setAttribute(SESSION_IDS_WRITTEN_ATTR, sessionsWritten);
		}
		return sessionsWritten;
	}

	private Cookie createSessionCookie(HttpServletRequest request,
									   Map<String, String> sessionIds) {
		Cookie sessionCookie = new Cookie(cookieName,"");
		if(isServlet3Plus) {
			sessionCookie.setHttpOnly(true);
		}
		sessionCookie.setSecure(request.isSecure());
		sessionCookie.setPath(cookiePath(request));
		// TODO set domain?

		if(sessionIds.isEmpty()) {
			sessionCookie.setMaxAge(0);
			return sessionCookie;
		}

		int maxAge = this.maxInactiveIntervalInSeconds;
		if (ImsSessionManager.getInstance().getAppId().equalsIgnoreCase(ITokenConstants.MOBILE_DEFAULT_APP_ID)) {
			maxAge = ITokenConstants.DEFAULT_TOKEN_EXPIRE_MOBILE;
		}
		sessionCookie.setMaxAge(maxAge);

		if(sessionIds.size() == 1) {
			String cookieValue = sessionIds.values().iterator().next();
			sessionCookie.setValue(cookieValue);
			return sessionCookie;
		}
		StringBuilder builder = new StringBuilder();
		for(Map.Entry<String,String> entry : sessionIds.entrySet()) {
			String alias = entry.getKey();
			String id = entry.getValue();

			builder.append(alias);
			builder.append(" ");
			builder.append(id);
			builder.append(" ");
		}
		builder.deleteCharAt(builder.length()-1);

		sessionCookie.setValue(builder.toString());
		return sessionCookie;
	}

	public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> sessionIds = getSessionIds(request);
		String requestedAlias = getCurrentSessionAlias(request);
		sessionIds.remove(requestedAlias);

		Cookie sessionCookie = createSessionCookie(request, sessionIds);
		response.addCookie(sessionCookie);
	}

	/**
	 * Sets the name of the HTTP parameter that is used to specify the session
	 * alias. If the value is null, then only a single session is supported per
	 * browser.
	 *
	 * @param sessionAliasParamName
	 *            the name of the HTTP parameter used to specify the session
	 *            alias. If null, then ony a single session is supported per
	 *            browser.
	 */
	public void setSessionAliasParamName(String sessionAliasParamName) {
		this.sessionParam = sessionAliasParamName;
		cookieHttpSessionStrategy.setSessionAliasParamName(sessionAliasParamName);
	}

	/**
	 * Sets the name of the cookie to be used
	 * @param cookieName the name of the cookie to be used
	 */
	public void setCookieName(String cookieName) {
		if(cookieName == null) {
			throw new IllegalArgumentException("cookieName cannot be null");
		}
		this.cookieName = cookieName;
		cookieHttpSessionStrategy.setCookieName(cookieName);
	}

	private static String cookiePath(HttpServletRequest request) {
		return request.getContextPath() + "/";
	}

	public Map<String,String> getSessionIds(HttpServletRequest request) {
		return cookieHttpSessionStrategy.getSessionIds(request);
	}

	public HttpServletRequest wrapRequest(HttpServletRequest request, HttpServletResponse response) {
		return cookieHttpSessionStrategy.wrapRequest(request, response);
	}

	public HttpServletResponse wrapResponse(HttpServletRequest request, HttpServletResponse response) {
		return cookieHttpSessionStrategy.wrapResponse(request, response);
	}

	public String encodeURL(String url, String sessionAlias) {
		return cookieHttpSessionStrategy.encodeURL(url, sessionAlias);
	}

	/**
	 * Returns true if the Servlet 3 APIs are detected.
	 * @return
	 */
	private boolean isServlet3() {
		try {
			ServletRequest.class.getMethod("startAsync");
			return true;
		} catch(NoSuchMethodException e) {}
		return false;
	}
}