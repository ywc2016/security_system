package securitySystem;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import edu.buaa.sem.utils.AES;

public class TestEncrypt {

	@Test
	public void testAES() throws Exception {
		AES aes = new AES();
		String plaintext = "123456";
		String key = "1234567812345678";
		String iv = "1234567812345678";
		System.out.println("encrypted: " + aes.encrypt(plaintext, key, iv));
		System.out.println("decrypted: " + aes.decrypt(aes.encrypt(plaintext, key, iv), key, iv).trim());
	}

	@Test
	public void test2() {
		try {
			String string = FileUtils.readFileToString(new File("F:/desktop/1.txt"));
			System.out.println(string);

			AES aes = new AES();
			String key = "1234567812345678";
			String iv = "1234567812345678";
			String ciphertext = aes.encrypt(string, key, iv);
			String pl = aes.decrypt(ciphertext, key, iv);
			System.out.println(ciphertext);
			System.out.println(pl);
			FileUtils.writeStringToFile(new File("F:/desktop/1_enc.txt"), ciphertext);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testReadFile() {

		try {
			File file = new File("F:/desktop/世纪保险经纪一期总体设计报告20151104.docx");
			byte[] bytes = FileUtils.readFileToByteArray(file);
			String string = FileUtils.readFileToString(file);
			File file2 = new File("F:/desktop/JGraph手册new.docx");
			FileUtils.writeByteArrayToFile(file2, bytes);
			File file3 = new File("F:/desktop/JGraph手册str.docx");
			FileUtils.writeStringToFile(file3, string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test5() {
		byte[] bs = "123".getBytes();
		AES aes = new AES();
		byte[] key = aes.getKey();
		byte[] cipher = aes.encrypt(bs, key, key);
	}
}
