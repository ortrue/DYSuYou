
package cn.com.dayang.suyou.security;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.com.dayang.suyou.util.DesUtils;
import cn.com.dayang.suyou.util.EasyUtils;

/**
 * <p>
 * LicenseManager.java
 * </p>
 * 
 * 1、应用程序可以创建以及验证绑定给用户、系统等实体的license。 2、licenses可以是永久性的或者临时性的（在某个特定时期内有效）
 * 3、共享应用程序可以配置试用期licenses 4、licenses的验证由JAVA Security
 * API提供的数字签名机制来实现。通过生成公钥/私钥对来分别对licenses进行签名和校验。
 * 5、license安装模块需要用特殊机制对其进行保护，以防被反编译轻易破解。可以使用java代码编译混淆器、自定义类装载器来实现。
 * 
 */
public final class LicenseManager {

	private static LicenseManager instance = new LicenseManager();

	private LicenseManager() {
	}
	public static synchronized LicenseManager getInstance() {
		return instance;
	}

	/**
	 * 验证license是否合法。 首先验证Mac地址是否有改变，有的话则非法。（防止用户自由拷贝软件）。 然后根据公钥验证签名是否合法。
	 * 
	 * @param license
	 * @return
	 * @throws Exception
	 */
	public boolean validate(String signature) {
		String signatureArry[]=StringUtils.split(signature,"|");
		String signatureKey=signatureArry[0];
		String publicKey=signatureArry[1];

		try {
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(EasyUtils.decodeHex(publicKey));
			java.security.PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
			Signature sig = Signature.getInstance("DSA");
			sig.initVerify(pubKey);
			DesUtils des = new DesUtils(publicKey);
			String decryptStr=des.decrypt(signatureKey);

			String decryptArry[]=decryptStr.split("\n|\r\n|\r");
			Map<String,Object> paraMap=new HashMap<String,Object>();
			paraMap.put(StringUtils.substringBefore(decryptArry[0],"="),StringUtils.substringAfter(decryptArry[0],"="));
			paraMap.put(StringUtils.substringBefore(decryptArry[1],"="),StringUtils.substringAfter(decryptArry[1],"="));
			paraMap.put(StringUtils.substringBefore(decryptArry[2],"="),StringUtils.substringAfter(decryptArry[2],"="));
			paraMap.put(StringUtils.substringBefore(decryptArry[3],"="),StringUtils.substringAfter(decryptArry[3],"="));
			paraMap.put(StringUtils.substringBefore(decryptArry[4],"="),StringUtils.substringAfter(decryptArry[4],"="));
			String Fingerprint=paraMap.get("product").toString()+paraMap.get("version").toString()+paraMap.get("type").toString()+paraMap.get("expiry").toString();
			sig.update(Fingerprint.getBytes());
			return sig.verify(EasyUtils.decodeHex(paraMap.get("signature").toString()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return false;
		} catch (SignatureException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}