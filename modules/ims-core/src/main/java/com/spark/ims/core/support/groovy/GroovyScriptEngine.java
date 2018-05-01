package com.spark.ims.core.support.groovy;

import groovy.lang.GroovyShell;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Component("groovyScriptEngine")
public class GroovyScriptEngine {

	private void setParameters(GroovyShell shell, Map<String, Object> vars) {
		if (vars == null)
			return;
		Set<Entry<String, Object>> set = vars.entrySet();
		for (Iterator<Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Entry<String, Object> entry = (Entry<String, Object>) it.next();
			shell.setVariable((String) entry.getKey(), entry.getValue());
		}
	}

	public boolean executeBoolean(String script, Map<String, Object> vars) {
		Boolean rtn = (Boolean) executeObject(script, vars);
		return rtn.booleanValue();
	}

	public Object executeObject(String script, Map<String, Object> vars) {
		GroovyShell shell = new GroovyShell();
		setParameters(shell, vars);
		script = script.replace("&apos;", "'").replace("&quot;", "\"")
				.replace("&gt;", ">").replace("&lt;", "<")
				.replace("&nuot;", "\n").replace("&amp;", "&");
		return shell.evaluate(script);
	}

}