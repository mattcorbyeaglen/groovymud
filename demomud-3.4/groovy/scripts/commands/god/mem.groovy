/** Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package commands.god

import java.lang.management.*
def stream = source.getTerminalOutput()
def all = false

if("-a" in args){
	all = true
}
if(args?.size() == 0 || argstr == null){
	stream.writeln """Usage: mem [-a] [-os] [-r] [-cls] [-m] [-t] [-gc] [-o [object name]]
where:
\t-a\tshow everything
\t-os\tshow OS and architecture info
\t-r\tshow runtime info
\t-cls\t class loader information
\t-m\t memory information
\t-t\t show thread information
\t-gc\t show garbage collection data
\t-o [object]\t show objects in registry, with optional name parameter
""";
	return
}

if("-os" in args || all){
	def os = ManagementFactory.operatingSystemMXBean
	stream.writeln """OPERATING SYSTEM:
    \tarchitecture = $os.arch
    \tname = $os.name
    \tversion = $os.version
    \tprocessors = $os.availableProcessors
    """
}
if("-r" in args || all){
	def rt = ManagementFactory.runtimeMXBean
	stream.writeln """RUNTIME:
\tname = $rt.name
\tspec name = $rt.specName
\tvendor = $rt.specVendor
\tspec version = $rt.specVersion
\tmanagement spec version = $rt.managementSpecVersion
"""
}
if("-cls" in args || all){
	def cl = ManagementFactory.classLoadingMXBean
	stream.writeln """CLASS LOADING SYSTEM:
\tisVerbose = ${cl.isVerbose()}
\tloadedClassCount = $cl.loadedClassCount
\ttotalLoadedClassCount = $cl.totalLoadedClassCount
\tunloadedClassCount = $cl.unloadedClassCount
"""
	
	
	def comp = ManagementFactory.compilationMXBean
	stream.writeln """COMPILATION:
\ttotalCompilationTime = $comp.totalCompilationTime
"""
}
if("-m" in args || all){
	def mem = ManagementFactory.memoryMXBean
	def heapUsage = mem.heapMemoryUsage
	def nonHeapUsage = mem.nonHeapMemoryUsage
	stream.writeln """MEMORY:
HEAP STORAGE:
\tcommitted = $heapUsage.committed
\tinit = $heapUsage.init
\tmax = $heapUsage.max
\tused = $heapUsage.used
NON-HEAP STORAGE:
\tcommitted = $nonHeapUsage.committed
\tinit = $nonHeapUsage.init
\tmax = $nonHeapUsage.max
\tused = $nonHeapUsage.used
"""
	
	ManagementFactory.memoryPoolMXBeans.each{ mp ->
		stream.writeln "\tname: " + mp.name
		String[] mmnames = mp.memoryManagerNames
		mmnames.each{ mmname -> stream.writeln "\t\tManager Name: $mmname" }
		stream.writeln "\t\tmtype = $mp.type"
		stream.writeln "\t\tUsage threshold supported = " + mp.isUsageThresholdSupported()
	}
	stream.writeln()
}
if("-t" in args || all){
	def td = ManagementFactory.threadMXBean
	stream.writeln "THREADS:"
	td.allThreadIds.each { tid -> stream.writeln "\tThread name = ${td.getThreadInfo(tid).threadName}" }
	stream.writeln()
}
if("-gc" in args || all){
	stream.writeln "GARBAGE COLLECTION:"
	ManagementFactory.garbageCollectorMXBeans.each { gc ->
		stream.writeln "\tname = $gc.name"
		stream.writeln "\t\tcollection count = $gc.collectionCount"
		stream.writeln "\t\tcollection time = $gc.collectionTime"
		String[] mpoolNames = gc.memoryPoolNames
		mpoolNames.each { mpoolName -> stream.writeln "\t\tmpool name = $mpoolName" }
	}
}
if("-o" in args || all){
	stream.writeln "OBJECT REGISTRY:"
	def matcher = (argstr =~ /\-o\s[a-zA-Z0-9]*/)
	def hasName = matcher.find()
	
	
	if(hasName){
	    matcher = (matcher.group() =~ /\s[a-zA-Z0-9]*/)
	    matcher.find()
		def oName = matcher.group().trim()
	    def hash = registry.getInventoryHandler().getMudObjects(oName)
	    stream.writeln "\t$oName=> " 
	    hash.each{
	        it-> stream.writeln "\t\t $it"
	    }
	}else{
	    def map = registry.getInventoryHandler().getMudObjectsMap()
	    map.each{ it ->
	    stream.writeln "\t$it.key => "
	    it.value.each{
	        val -> stream.writeln "\t\t $val"
	    }
    }
	}
	
	
}