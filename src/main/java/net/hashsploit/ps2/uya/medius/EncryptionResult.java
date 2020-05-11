package net.hashsploit.ps2.uya.medius;

public class EncryptionResult {
	
	private byte[] sha1;
	private byte[] data;
	
	public EncryptionResult(final byte[] messageBytes, final byte[] data) {
		this.sha1 = messageBytes;
		this.data = data;
	}

	public byte[] getSHA1() {
		return sha1;
	}

	public byte[] getData() {
		return data;
	}
	
}
