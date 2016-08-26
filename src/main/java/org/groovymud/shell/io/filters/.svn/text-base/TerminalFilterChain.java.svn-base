package org.groovymud.shell.io.filters;

import java.util.Iterator;
import java.util.List;

public class TerminalFilterChain {

	private List<TerminalIOFilter> filters;

	public void register(TerminalIOFilter filter) {
		getFilters().add(filter);
	}

	public boolean unregister(TerminalIOFilter filter) {
		return getFilters().remove(filter);
	}

	public String doFilter(String str, int type) {
		Iterator<TerminalIOFilter> iterator = getFilters().iterator();
		while (iterator.hasNext()) {
			TerminalIOFilter filter = iterator.next();
			if (filter.getFilterType() == type) {
				str = filter.doFilter(str);
			}
		}
		return str;
	}

	public void setFilters(List<TerminalIOFilter> filters) {
		this.filters = filters;
	}

	public List<TerminalIOFilter> getFilters() {
		return filters;
	}
}
