package net.hashsploit.ps2.uya.medius;

import net.hashsploit.ps2.uya.medius.utils.RC4;
import net.hashsploit.ps2.uya.medius.utils.Utils;

public class UYAMediusTool {

	public static final String NAME = "UYA Medius Tool";
	public static final String VERSION = "0.1.0";

	public UYAMediusTool() {
		
	}

	public EncryptionResult encryptMessage(String key, String message) {
		if (key != null) {
			return subEncryptMessage(key, message);
		}

		String[] keys = Utils.readFileAsLines("keys.txt");
		for (int k = 0; k < keys.length; ++k) {
			System.out.println(String.format("ENCRYPTING WITH KEY:{%s}", keys[k]));
			System.out.println();
			subEncryptMessage(keys[k], message);
			System.out.println();
			System.out.println();
		}

		return null;
	}

	private EncryptionResult subEncryptMessage(String key, String message) {
		byte[] keyBytes = Utils.strToByteArray(key);
		byte[] messageBytes = Utils.strToByteArray(message);
		byte[] cipher = new byte[messageBytes.length];

		RC4 packer = new RC4(keyBytes);

		cipher = packer.encrypt(messageBytes);

		System.out.println("SHA1:{Utils.BAToString(packer.Hash(messageBytes))} CIPHER:{Utils.BAToString(cipher)}");
		System.out.println();

		return new EncryptionResult(messageBytes, cipher);
	}

	public DecryptionResult decryptMessage(String key, String packet) {
		if (key != null) {
			return subDecryptPacket(key, packet);
		}

		String[] keys = Utils.readFileAsLines("keys.txt");

		for (int k = 0; k < keys.length; ++k) {
			System.out.println(String.format("DECRYPTING WITH KEY:{%s}", keys[k]));
			System.out.println();
			subDecryptPacket(keys[k], packet);
			System.out.println();
			System.out.println();
		}

		return null;
	}

	private DecryptionResult subDecryptPacket(String key, String packet) {
		byte[] keyBytes = Utils.strToByteArray(key);
		byte[] packetBytes = Utils.strToByteArray(packet);

		RC4 packer = new RC4(keyBytes);

		for (int i = 0; i < packetBytes.length;) {
			byte id = packetBytes[i + 0];
			short len = Utils.toInt16(packetBytes, i + 1);
			byte[] hash = new byte[4];
			byte[] buf = new byte[len];
			System.arraycopy(packetBytes, i + 7, buf, 0, len);
			System.arraycopy(packetBytes, i + 3, hash, 0, 4);
			byte[] result = packer.decrypt(hash, buf);

			System.out.println("ID:{id.ToString(\"X2\")} LEN:{len} SHA1:{Utils.BAToString(hash)} DATA:");
			System.out.println(String.format("ID:%s LEN:%d SHA1:%s DATA:", Byte.toString(id), len, Utils.baToString(hash)));
			Utils.fancyPrintBA(result);
			System.out.println();

			i += len + 7;
		}

		return null;
	}

}
