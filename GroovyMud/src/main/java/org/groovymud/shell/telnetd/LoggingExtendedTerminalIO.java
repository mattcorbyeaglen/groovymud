package org.groovymud.shell.telnetd;

import java.io.IOException;
import java.util.List;

import net.wimpi.telnetd.io.BasicTerminalIO;
import net.wimpi.telnetd.net.Connection;

import org.apache.log4j.Logger;
import org.groovymud.object.alive.Alive;

public class LoggingExtendedTerminalIO extends ExtendedTerminalIO {

	Alive object = null;

	private static final Logger logger = Logger.getLogger(LoggingExtendedTerminalIO.class);

	public LoggingExtendedTerminalIO(Connection connection) {
		super(null);
	}

	public Alive getObject() {
		return object;
	}

	public void setObject(Alive object) {
		this.object = object;
	}

	@Override
	public void bell() throws IOException {

	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public boolean defineScrollRegion(int arg0, int arg1) throws IOException {
		return false;
	}

	@Override
	protected String deleteCharacters(String str, int number) throws IOException {
		return str.substring(0, str.length() - number);
	}

	@Override
	public void eraseLine() throws IOException {

	}

	@Override
	public void eraseScreen() throws IOException {

	}

	@Override
	public void eraseToBeginOfLine() throws IOException {

	}

	@Override
	public void eraseToBeginOfScreen() throws IOException {

	}

	@Override
	public void eraseToEndOfLine() throws IOException {

	}

	@Override
	public void eraseToEndOfScreen() throws IOException {

	}

	@Override
	public void flush() throws IOException {

	}

	@Override
	public void forceBold(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public BasicTerminalIO getBasicTerminalIO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumns() {
		// TODO Auto-generated method stub
		return super.getColumns();
	}

	@Override
	public List getPreviousCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void homeCursor() throws IOException {
		super.homeCursor();
	}

	@Override
	public boolean isAutoflushing() {
		return false;
	}

	@Override
	public boolean isLineWrapping() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSignalling() {
		return false;
	}

	@Override
	public void moveCursor(int arg0, int arg1) throws IOException {

	}

	@Override
	public void moveDown(int arg0) throws IOException {

	}

	@Override
	public void moveLeft(int arg0) throws IOException {

	}

	@Override
	public void moveRight(int arg0) throws IOException {

	}

	@Override
	public void moveUp(int arg0) throws IOException {

	}

	@Override
	public int read() throws IOException {
		return 0;
	}

	@Override
	public String readln(boolean quiet) throws IOException {
		return null;
	}

	@Override
	public void resetAttributes() throws IOException {
	}

	@Override
	public void resetTerminal() throws IOException {

	}

	@Override
	public void restoreCursor() throws IOException {
	}

	@Override
	public void setAutoflushing(boolean arg0) {

	}

	@Override
	public void setBackgroundColor(int arg0) throws IOException {
	}

	@Override
	public void setBasicTerminalIO(BasicTerminalIO basicTerminalIO) {
	}

	@Override
	public void setBlink(boolean arg0) throws IOException {
	}

	@Override
	public void setBold(boolean arg0) throws IOException {

	}

	@Override
	public void setCursor(int arg0, int arg1) throws IOException {
	}

	@Override
	public void setDefaultTerminal() throws IOException {
	}

	@Override
	public void setForegroundColor(int arg0) throws IOException {
	}

	@Override
	public void setItalic(boolean arg0) throws IOException {
	}

	@Override
	public void setLinewrapping(boolean arg0) throws IOException {

	}

	@Override
	public void setPreviousCommands(List previousCommands) {

	}

	@Override
	public void setSignalling(boolean arg0) {
	}

	@Override
	public void setTerminal(String arg0) throws IOException {

	}

	@Override
	public void setUnderlined(boolean arg0) throws IOException {

	}

	@Override
	public void storeCursor() throws IOException {
	}

	@Override
	public void write(byte arg0) throws IOException {
		logger.info(objectIdentity() + ":" + arg0);
	}

	@Override
	public void write(char arg0) throws IOException {
		logger.info(objectIdentity() + ":" + arg0);
	}

	private String objectIdentity() {
		return object.getName() + ":" + object.toString();
	}

	@Override
	public void write(String arg0) throws IOException {
		logger.info(objectIdentity() + ":" + arg0);
	}

	@Override
	public void writeln(String string) throws IOException {
		logger.info(objectIdentity() + ":" + string);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
