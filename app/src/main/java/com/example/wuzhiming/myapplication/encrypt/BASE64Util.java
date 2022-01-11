package com.example.wuzhiming.myapplication.encrypt;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * 无论是编码还是解码都会有一个参数Flags，Android提供了以下几种

 DEFAULT 这个参数是默认，使用默认的方法来加密

 NO_PADDING 这个参数是略去加密字符串最后的”=”

 NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）

 CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF

 URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
 */
public class BASE64Util {

	/*Base64编码本质上是一种将二进制数据转成文本数据的方案。
	 * 对于非二进制数据，是先将其转换成二进制形式，然后每连续6比特（2的6次方=64）计算其十进制值，根据该值在A--Z,a--z,0--9,+,/ 这64个字符中找到对应的字符，最终得到一个文本字符串。基本规则如下几点：

	标准Base64只有64个字符（英文大小写、数字和+、/）以及用作后缀等号；
	Base64是把3个字节变成4个可打印字符，所以Base64编码后的字符串一定能被4整除（不算用作后缀的等号）；
	等号一定用作后缀，且数目一定是0个、1个或2个。这是因为如果原文长度不能被3整除，Base64要在后面添加\0凑齐3n位。为了正确还原，添加了几个\0就加上几个等号。显然添加等号的数目只能是0、1或2；
	严格来说Base64不能算是一种加密，只能说是编码转换*/


	/**
	 * 对文件进行Base64解码
	 * @param desFile,将文件解码到何处
	 * @param encodedString
	 * @return
	 */
	public static void decodeFile(File desFile,String encodedString)  {
		FileOutputStream  fos = null;
		try {
			byte[] decodeBytes = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
			fos = new FileOutputStream(desFile);
			fos.write(decodeBytes);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对文件进行Base64编码
	 * @param file
	 * @return
	 */
	public static String encodeFfile(File file) {
		String encodedString = "";
		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			encodedString = Base64.encodeToString(buffer, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodedString;
	}

	/**
	 * BASE64解密
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeToBytes(String key)  {
		return Base64.decode(key, Base64.NO_WRAP);
	}
	public static String decodeToString(String key)  {
		String decodedString =new String(Base64.decode(key,Base64.DEFAULT));
		return decodedString;
	}

	/**
	 * BASE64加密
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encodeByte(byte[] key) {
		byte[] res = Base64.encode(key, Base64.NO_WRAP);
		String resStr =new String(res);
		return resStr;
	}
	public static String encodeString(String key) {
		String encodedString = Base64.encodeToString(key.getBytes(), Base64.DEFAULT);
		return encodedString;
	}



}