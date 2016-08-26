package commands.player;

import std.filters.TranslationFilter;
def filter = null
new LinkedList(source.filters).each{
	if(it instanceof TranslationFilter){
		filter = it
		source.filters - it
		source.terminalOutput.filterChain.unregister it
	}
}
if(argstr == null || argstr == "" || argstr == "off"){
	source.terminalOutput.writeln "Language is now English."
	return;
}
if(args.size() == 1){
	if(!filter){
		filter = new TranslationFilter();
	}
	filter.language = args[0]
	source.filters << filter
	source.terminalOutput.filterChain.register filter
	source.terminalOutput.writeln "Language is now set to ${args[0]}. You will still have to write commands and reference objects in English."
}