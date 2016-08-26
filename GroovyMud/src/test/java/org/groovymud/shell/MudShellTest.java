package org.groovymud.shell;

import junit.framework.TestCase;
import net.wimpi.telnetd.net.Connection;

import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;
import org.groovymud.shell.telnetd.MudShell;

public class MudShellTest extends TestCase {

	boolean connectionSet = false;
	boolean terminalIOSet = false;

	public void testRun() {
		fail("not implemented yet.");
		/* MockControl connCtrl = MockClassControl.createControl(Connection.class);

		 final Connection mockConnection = (Connection) connCtrl.getMock();

		 MudShell shell = new MudShell() {

		     @Override
		     public void setConnection(Connection connection) {
		         // TODO Auto-generated method stub
		         assertEquals(mockConnection, connection);
		         connectionSet = true;
		     }

		     @Override
		     protected ExtendedTerminalIO createTerminalIO(Connection connection) {
		         // TODO Auto-generated method stub
		         return super.createTerminalIO(connection);
		     }

		     @Override
		     public void setTerminalIO(ExtendedTerminalIO io) {
		         // TODO Auto-generated method stub
		         super.setTerminalIO(io);
		     }
		 };
		 mockConnection.addConnectionListener(shell);
		 connCtrl.setDefaultVoidCallable();

		 shell.run(mockConnection);*/
	}

}
