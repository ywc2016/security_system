/**
 * ywc
 */

function testAES() {
	var data = "123456";
	var key = '123456781234567';
	var iv = '12345678123456V';
	var ciphertext = encryptAES(data, getKey(key), getIv(iv));
	decryptAES(ciphertext, getKey(key), getIv(iv));
}
/**
 * 
 * @param {}
 *            key 16位的字符串
 * @return {}
 */
function getKey(key) {
	var key = CryptoJS.enc.Latin1.parse(key);
	return key;
}
/**
 * 
 * @param {}
 *            iv 16位的字符串
 * @return {}
 */
function getIv(iv) {
	var iv = CryptoJS.enc.Latin1.parse(iv);
	return iv;
}

function encryptAES(plaintext, key, iv) {
	// 加密
	var encrypted = CryptoJS.AES.encrypt(plaintext, key, {
				iv : iv,
				mode : CryptoJS.mode.CBC,
				padding : CryptoJS.pad.ZeroPadding
			});
	console.log('encrypted: ' + encrypted);
	return encrypted;
}

function decryptAES(ciphertext, key, iv) {
	// 解密
	var decrypted = CryptoJS.AES.decrypt(ciphertext, key, {
				iv : iv,
				padding : CryptoJS.pad.ZeroPadding
			});
	console.log('decrypted: ' + decrypted.toString(CryptoJS.enc.Utf8));
	return decrypted;
}