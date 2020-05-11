package net.hashsploit.ps2.uya.medius.utils;

import org.bouncycastle.crypto.digests.SHA1Digest;

/**
 * Custom RC4 implementation for UYA's Medius implementation, based off
 * https://github.com/Dnawrkshp/uya-medius-encryption/blob/master/UYA.Medius.Shared/RC4.cs
 */
public class RC4 {

	private static final int STATE_LENGTH = 256;
	private byte[] engineState;
	private byte[] workingKey;
	private int x;
	private int y;

	public RC4(byte[] key) {
		// Initialize key
		// UYA wants a 512 bit key
		setKey(key);
	}

	/**
	 * Reset the key being used
	 */
	public void reset() {
		setKey(workingKey);
	}

	/**
	 * Set the RC4 key
	 * 
	 * @param key
	 */
	private void setKey(byte[] key) {
		setKey(key, null);
	}

	/**
	 * Set the RC4 key with hash
	 * 
	 * @param key
	 * @param hash
	 */
	private void setKey(byte[] key, byte[] hash) {
		workingKey = key;

		x = 0;
		y = 0;

		int keyIndex = 0;
		int li = 0;
		int cipherIndex = 0;
		int idIndex = 0;

		// Initialize engine state
		if (engineState == null) {
			engineState = new byte[STATE_LENGTH];
		}

		// reset the state of the engine
		// Normally this initializes values 0,1..254,255 but UYA does this in reverse.
		for (int i = 0; i < STATE_LENGTH; i++) {
			engineState[i] = (byte) ((STATE_LENGTH - 1) - i);
		}

		if (hash != null && hash.length == 4) {
			// Apply hash
			do {
				int v1 = hash[idIndex];
				idIndex = (idIndex + 1) & 3;

				byte temp = engineState[cipherIndex];
				v1 += li;
				li = (temp + v1) & 0xFF;

				engineState[cipherIndex] = engineState[li];
				engineState[li] = temp;

				cipherIndex = (cipherIndex + 5) & 0xFF;

			} while (cipherIndex != 0);

			// Reset
			keyIndex = 0;
			li = 0;
			cipherIndex = 0;
			idIndex = 0;
		}

		// Apply key
		do {
			int keyByte = key[keyIndex];
			keyByte += li;
			keyIndex += 1;
			keyIndex &= 0x3F;

			int cipherByte = engineState[cipherIndex];
			byte cipherValue = (byte) (cipherByte & 0xFF);

			cipherByte += keyByte;
			li = cipherByte & 0xFF;

			byte t0 = engineState[li];
			engineState[cipherIndex] = t0;
			engineState[li] = cipherValue;

			cipherIndex += 3;
			cipherIndex &= 0xFF;
		} while (cipherIndex != 0);
	}

	private void hash(byte[] input, int inOff, int length, byte[] output, int outOff) {
		byte[] result = new byte[20];

		// Compute sha1 hash
		SHA1Digest digest = new SHA1Digest();
		digest.update(input, inOff, length);
		digest.doFinal(result, 0);

		// Extra operation that UYA wants
		result[3] = (byte) ((result[3] & 0x1F) | 0x60);

		System.arraycopy(result, 0, output, outOff, 4);
	}

	public byte[] hash(byte[] input) {
		byte[] result = new byte[4];
		hash(input, 0, input.length, result, 0);
		return result;
	}

	private void decrypt(byte[] input, int inOff, int length, byte[] output, int outOff) {
		for (int i = 0; i < length; ++i) {
			y = (y + 5) & 0xFF;

			int v0 = engineState[y];
			byte a2 = (byte) (v0 & 0xFF);
			v0 += x;
			x = (byte) (v0 & 0xFF);

			v0 = engineState[x];
			engineState[y] = (byte) (v0 & 0xFF);
			engineState[x] = a2;

			byte a0 = input[i];

			v0 += a2;
			v0 &= 0xFF;
			int v1 = engineState[v0];

			a0 ^= (byte) v1;
			output[i] = a0;

			v1 = engineState[a0] + x;
			x = v1 & 0xFF;
		}
	}

	/**
	 * Decrypt RC4 data
	 * 
	 * @param hash
	 * @param data
	 * @return
	 */
	public byte[] decrypt(byte[] hash, byte[] data) {
		// Set seed
		setKey(workingKey, hash);

		byte[] result = new byte[data.length];
		decrypt(data, 0, data.length, result, 0);
		return result;
	}

	private void encrypt(byte[] input, int inOff, int length, byte[] output, int outOff) {
		for (int i = 0; i < length; ++i) {
			x = (x + 5) & 0xff;
			y = (y + engineState[x]) & 0xff;

			// Swap
			byte temp = engineState[x];
			engineState[x] = engineState[y];
			engineState[y] = temp;

			// Xor
			output[i + outOff] = (byte) (input[i + inOff] ^ engineState[(engineState[x] + engineState[y]) & 0xff]);

			y = (engineState[input[i + inOff]] + y) & 0xff;
		}
	}

	/**
	 * Encrypt RC4 data
	 * 
	 * @param data
	 * @return
	 */
	public byte[] encrypt(byte[] data) {
		// Set seed
		setKey(workingKey, hash(data));

		byte[] result = new byte[data.length];
		encrypt(data, 0, data.length, result, 0);
		return result;
	}

}
