package org.groovymud.shell;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.groovymud.shell.telnetd.ExtendedTerminalIO;

public class MockExtendedTerminalIO extends ExtendedTerminalIO {

	boolean autoFlushing;
	boolean lineWrapping;
	boolean signalling;

	ByteArrayInputStream readCharacters;

	ByteArrayOutputStream os;

	public MockExtendedTerminalIO() {
		super(null);
		os = new ByteArrayOutputStream();
	}

	public void bell() {

	}

	public void close() {

	}

	public boolean defineScrollRegion(int arg0, int arg1) {
		return true;
	}

	protected String deleteCharacters(String str, int number) {
		return str;
	}

	public void eraseLine() {

	}

	public void eraseScreen() {
		os.reset();
	}

	public void eraseToBeginOfLine() {

	}

	public void eraseToBeginOfScreen() {
		os.reset();
	}

	public void eraseToEndOfLine() {

	}

	public void eraseToEndOfScreen() {

	}

	public void flush() {
		os.reset();
	}

	public void forceBold(boolean arg0) {

	}

	public int getColumns() {
		return 0;
	}

	public int getRows() {
		return 0;
	}

	public void homeCursor() {

	}

	public boolean isAutoflushing() {
		return true;
	}

	public boolean isLineWrapping() {
		return true;
	}

	public boolean isSignalling() {
		return signalling;
	}

	public void moveCursor(int arg0, int arg1) {

	}

	public void moveDown(int arg0) {

	}

	public void moveLeft(int arg0) {

	}

	public void moveRight(int arg0) {

	}

	public void moveUp(int arg0) {

	}

	public int read() throws IOException {
		int c = readCharacters.read();

		return c == -1 ? ENTER : c;
	}

	public void resetAttributes() {

	}

	public void resetTerminal() {

	}

	public void restoreCursor() {

	}

	public void setAutoflushing(boolean arg0) {

	}

	public void setBackgroundColor(int arg0) {

	}

	public void setBlink(boolean arg0) {

	}

	public void setBold(boolean arg0) {

	}

	public void setCursor(int arg0, int arg1) {

	}

	public void setDefaultTerminal() {

	}

	public void setForegroundColor(int arg0) {

	}

	public void setItalic(boolean arg0) {

	}

	public void setLinewrapping(boolean arg0) {

	}

	public void setSignalling(boolean arg0) {

	}

	public void setTerminal(String arg0) {

	}

	public void setUnderlined(boolean arg0) {

	}

	public void storeCursor() {

	}

	public void write(byte arg0) {
		os.write(arg0);
	}

	public void write(char arg0) {
		os.write(arg0);
	}

	public void write(String arg0) {
		try {
			os.write(arg0.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isAutoFlushing() {
		return autoFlushing;
	}

	public void setAutoFlushing(boolean autoFlushing) {
		this.autoFlushing = autoFlushing;
	}

	public void setLineWrapping(boolean lineWrapping) {
		this.lineWrapping = lineWrapping;
	}

	public void setReadCharacters(String readCharacters) {
		this.readCharacters = new ByteArrayInputStream(readCharacters.getBytes());
	}

	public String getOutputStreamAsString() {
		return os.toString();
	}

}
