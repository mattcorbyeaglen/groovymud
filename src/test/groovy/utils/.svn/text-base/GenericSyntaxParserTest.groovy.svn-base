/**
 * 
 */
package utils

import utils.GenericSyntaxParser
import groovy.util.GroovyTestCase
/**
 * @author corbym
 *
 */
public class GenericSyntaxParserTest extends GroovyTestCase{

	
	public void testParseFrom(){
	    def argstr = "monkey from sack"
	    
	    GenericSyntaxParser parser = new GenericSyntaxParser()
	    
	    parser.parse(argstr,"from")
	    
	    assertTrue(parser.subjectObject.objectName == "monkey")
		assertTrue(parser.prepositionObject.objectName == "sack")
	}
	
	public void testParseFromPrepWithIndex(){
	    def argstr = "monkey from sack 2"
	    
	    GenericSyntaxParser parser = new GenericSyntaxParser()
	    
	    parser.parse(argstr,"from")
	    
	    assertTrue(parser.subjectObject.objectName == "monkey")
		assertTrue(parser.prepositionObject.objectName == "sack")
		assertTrue(parser.prepositionObject.index == 2)
	}
	
	public void testParseFromWithIndex(){
	    def argstr = "monkey 2 from sack"
	    
	    GenericSyntaxParser parser = new GenericSyntaxParser()
	    
	    parser.parse(argstr, "from")
	    
	    assertTrue(parser.subjectObject.objectName == "monkey")
	    assertTrue(parser.subjectObject.index == 2)
		assertTrue(parser.prepositionObject.objectName == "sack")		
	}
	
	public void testParseFromWithBothIndices(){
	    def argstr = "monkey 2 from sack 32"
	    
	    GenericSyntaxParser parser = new GenericSyntaxParser()
	    
	    parser.parse(argstr,"from")
	    
	    assertTrue(parser.subjectObject.objectName == "monkey")
	    assertTrue(parser.subjectObject.index == 2)
		assertTrue(parser.prepositionObject.objectName == "sack")	
		assertTrue(parser.prepositionObject.index == 32)
	}
}
