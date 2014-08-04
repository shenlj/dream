package com.wholetech.commons.dao;

import java.net.InetAddress;

public class UUIDHexGenerator {

	private final String sep = "";
	private static final int IP;
	private static short counter = (short) 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	private static UUIDHexGenerator uuidgen = new UUIDHexGenerator();

	static {
		int ipadd;
		try {
			ipadd = UUIDHexGenerator.toInt(InetAddress.getLocalHost().getAddress());
		} catch (final Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	public static UUIDHexGenerator getInstance() {

		return UUIDHexGenerator.uuidgen;
	}

	public static int toInt(final byte[] bytes) {

		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + bytes[i];
		}
		return result;
	}

	protected String format(final int intval) {

		final String formatted = Integer.toHexString(intval);
		final StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(final short shortval) {

		final String formatted = Integer.toHexString(shortval);
		final StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	protected int getJVM() {

		return UUIDHexGenerator.JVM;
	}

	protected synchronized short getCount() {

		if (UUIDHexGenerator.counter < 0) {
			UUIDHexGenerator.counter = 0;
		}
		return UUIDHexGenerator.counter++;
	}

	protected int getIP() {

		return UUIDHexGenerator.IP;
	}

	protected short getHiTime() {

		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected int getLoTime() {

		return (int) System.currentTimeMillis();
	}

	/**
	 * 生成uuid字符串，长度为32。
	 * 
	 * @return
	 */
	public String generate() {

		return new StringBuffer(36).append(format(getIP()))
				.append(this.sep).append(format(getJVM()))
				.append(this.sep).append(format(getHiTime()))
				.append(this.sep).append(format(getLoTime()))
				.append(this.sep).append(format(getCount())).toString();
	}

	// public static void main(String[] args) {
	// System.out.println(UUIDHexGenerator.getInstance().generate());
	// }
}
