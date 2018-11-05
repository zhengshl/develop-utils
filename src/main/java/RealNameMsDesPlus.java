import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RealNameMsDesPlus {
	private static Log logger = LogFactory.getLog(RealNameMsDesPlus.class);
	private static String strDefaultKey = "RNQDK";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 */
	private String byteArrToHexStr (byte[] arrB) {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuilder sb = new StringBuilder(iLen * 2);
		for (byte anArrB : arrB) {
			int intTmp = anArrB;
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 */
	private byte[] hexStrToByteArr (String strIn){
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 默认构造方法，使用默认密钥
	 * 
	 */
	public RealNameMsDesPlus() {
		this(strDefaultKey);
	}

	/**
	 * 指定密钥构造方法
	 * @param strKey 指定的密钥
	 */
	public RealNameMsDesPlus(String strKey) {
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			Key key = getKey(strKey.getBytes());

			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);

			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			logger.error("RealNameMsDesPlus RealNameMsDesPlus", e);
		}
		
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 */
	private byte[] encrypt (byte[] arrB)  {
		try {
			return encryptCipher.doFinal(arrB);
		} catch (Exception e) {
			logger.error("RealNameMsDesPlus encrypt", e);
			return null;
		}
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 */
	public String encrypt (String strIn)  {
		try {
			return byteArrToHexStr(encrypt(strIn.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.error("RealNameMsDesPlus encrypt", e);
			return null;
		}
	}

	/**
	 * 解密字节数组
	 * @param arrB 需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	private byte[] decrypt (byte[] arrB) throws  IllegalBlockSizeException, BadPaddingException {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn 需解密的字符串
	 * @return 解密后的字符串,以utf-8格式编码
	 */
	public String decrypt (String strIn) {
		try {
			return new String(decrypt(hexStrToByteArr(strIn)), "utf-8");
		} catch (Exception e) {
			logger.error("RealNameMsDesPlus decrypt", e);
			return null;
		}
	}

	/**
	 * @param strIn 需解密的字符串
	 * @param encode 解密后的字符编码
	 */
	public String decrypt (String strIn, String encode)  {
		try {
			return new String(decrypt(hexStrToByteArr(strIn)), encode);
		} catch (Exception e) {
			logger.error("RealNameMsDesPlus decrypt", e);
			return null;
		}
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * @param arrBTmp 构成该字符串的字节数组
	 * @return 生成的密钥
	 */
	private Key getKey (byte[] arrBTmp) {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
	}
}
