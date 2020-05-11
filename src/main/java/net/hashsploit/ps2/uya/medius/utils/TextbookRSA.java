package net.hashsploit.ps2.uya.medius.utils;

import java.math.BigInteger;

/**
 * "Textbook RSA" implementation, based off
 * https://github.com/Dnawrkshp/uya-medius-encryption/blob/yeet/UYA.Medius.Shared/RSA.cs
 * No padding
 */
public class TextbookRSA {
	
	private BigInteger mod;
	private BigInteger exp1;
	private BigInteger exp2;

	public TextbookRSA(BigInteger mod, BigInteger exp1, BigInteger exp2) {
		this.mod = mod;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	public BigInteger encrypt(BigInteger m) {
		return m.modPow(exp1, mod);
	}

	public BigInteger decrypt(BigInteger c) {
		return c.modPow(exp2, mod);
	}

}