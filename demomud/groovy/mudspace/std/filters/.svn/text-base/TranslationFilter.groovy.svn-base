package std.filters;


import net.wimpi.telnetd.io.terminal.ColorHelper;

import org.apache.commons.lang.StringEscapeUtils;


import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


import org.groovymud.shell.io.filters.FilterType;
import org.groovymud.shell.io.filters.TerminalIOFilter;
import org.json.simple.parser.JSONParser;

class TranslationFilter<ExtendedTerminalIO> implements TerminalIOFilter {
	Map ioMap = [:]
	FilterType filterType = FilterType.OUTPUT_FILTER_TYPE
	static final String googleKey = "ABQIAAAA1Jje5tanpzDMg0eSGs9NlxQLZL5OPJ_5suJg-CtEvw-EMcrK-RRNgWs2YxI-5SGljZ4QRTbKR90QBQ"
	static final String website = "http://code.google.com/p/groovymud"
	String language = "en"
	
	class SpecialCharOperations{
		List spec = []
		
		def replaceSpecialChars(String io){
			String text = new String(io)
			int i = 0
			def matcher = io =~ /${ColorHelper.INTERNAL_MARKER}./
			matcher.eachWithIndex { it, idx ->
				spec << it
				text = text.replaceFirst(/$it/, "<$idx/>")
				i++
			}
			matcher = io =~ /\r\n/
			matcher.eachWithIndex { it, idx ->
				spec << it << it
				text = text.replaceFirst(/$it/, "<${idx + i}/>")
			}
			return text
		}
		def reinsertChars(String io){		
			spec.eachWithIndex(){ it, idx ->				
				io = io.replaceAll(/<$idx \/> ?/, "$it")				
			}
			return io
		}
	}
	
	public String doFilter(ExtendedTerminalIO terminalOutput, String io){
		String ret = io // keep it in case...
		String key = language + "_" + io;
		if(ioMap.containsKey(key)){
			return ioMap.get(key);
		}
		SpecialCharOperations spec = new SpecialCharOperations()
		io = spec.replaceSpecialChars(io)
		
		io= StringEscapeUtils.escapeHtml(URLEncoder.encode(io, "UTF-8"))
		
		def url = new URL("http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&q=${io}&key=$googleKey&langpair=en%7C${language}")
		
		def trans = url.openConnection()
		trans.setRequestProperty "Accept-Charset", "UTF-8"
		def txt = trans.inputStream.text
		JSONParser parser = new JSONParser()
		def obj = parser.parse(txt)
		println "response: ${obj}"
		if(obj.responseData != null){
			txt = StringEscapeUtils.unescapeHtml(obj.responseData.translatedText)
			io = spec.reinsertChars(txt)
			io = io.replaceAll(/(  *)/, " ")
		}else{
			io = ret
		}
		print io
		ioMap.put(key, io)
		return io
	}
	
}
