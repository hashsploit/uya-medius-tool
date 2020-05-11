package net.hashsploit.ps2.uya.medius;

public class DecryptionResult {
	
	private String id;
	private byte[] sha1;
	private byte[] data;
	private short length;
	
	public DecryptionResult(final String id, final byte[] sha1, final short length, final byte[] data) {
		this.id = id;
		this.sha1 = sha1;
		this.length = length;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public byte[] getSHA1() {
		return sha1;
	}

	public byte[] getData() {
		return data;
	}

	public short getLength() {
		return length;
	}
	
}
