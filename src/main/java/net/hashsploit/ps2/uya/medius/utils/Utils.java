package net.hashsploit.ps2.uya.medius.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.List;

import net.hashsploit.ps2.uya.medius.UYAMediusTool;

public class Utils {

	// Prevent instantiation
	private Utils() {
	}

	public static final byte[] strToByteArray(final String str) {
		int len = str.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
		}
		return data;
	}

	public static final String baToString(byte[] buffer) {
		String str = "";
		for (int i = 0; i < buffer.length; ++i) {
			str += String.format("%02X", buffer[i]);
		}
		return str;
	}

	public static void fancyPrintBA(byte[] buffer) {
		String str = "";
		for (int i = 0; i < buffer.length; ++i) {
			char c = (char) buffer[i];
			System.out.print(String.format("%02X ", buffer[i]));
			str += Character.isISOControl(c) ? '.' : c;
		}

		System.out.println();
		System.out.println(str);
	}

	public static int bytesToUnsigned(byte b) {
		return b & 0xFF;
	}

	public static String[] readFileAsLines(String resourceName) {
		try {
			File file = new File(UYAMediusTool.class.getResource("/" + resourceName).getFile());
			List<String> list = Files.readAllLines(file.toPath());
			return list.toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static short toInt16(byte[] bytes, int index) {
		return (short) ((bytes[index + 1] & 0xFF) | ((bytes[index] & 0xFF) << 0));
	}

	public static byte[] GetBytesU16(long value) {
		ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.nativeOrder());
		buffer.putLong(value);
		return buffer.array();
	}
}
