package edu.buaa.sem.utils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {

	public String encrypt(String plaintext, String key, String iv) throws Exception {
		try {
			// plaintext = "123456";
			// key = "1234567812345678";
			// iv = "1234567812345678";
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = plaintext.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintextByte = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintextByte, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintextByte);
			return Base64.encodeBase64String(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String decrypt(String ciphertext, String key, String iv) throws Exception {
		try {
			// String data = encrypted;
			// String key = "1234567812345678";
			// String iv = "1234567812345678";
			byte[] encrypted1 = Base64.decodeBase64(ciphertext);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 随机生成秘钥
	 */
	public byte[] getKey() {
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128);// 要生成多少位，只需要修改这里即可128, 192或256
			SecretKey sk = kg.generateKey();
			byte[] b = sk.getEncoded();
			return b;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public byte[] encrypt(byte[] plaintext, byte[] key, byte[] iv) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			int plaintextLength = plaintext.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintextByte = new byte[plaintextLength];
			System.arraycopy(plaintext, 0, plaintextByte, 0, plaintext.length);
			SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintextByte);
			return Base64.encodeBase64(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] decrypt(byte[] ciphertext, byte[] key, byte[] iv) {
		try {
			byte[] encrypted = Base64.decodeBase64(ciphertext);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted);
			return original;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}