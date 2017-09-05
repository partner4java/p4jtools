package com.partner4java.p4jtools.quickpay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class CAP12CertTool {
	private static SignedPack signedPack;

	public CAP12CertTool(InputStream fileInputStream, String keyPass) throws SecurityException {
		signedPack = getP12(fileInputStream, keyPass);
	}

	public CAP12CertTool(String path, String keyPass) throws SecurityException, FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(new File(path));

		signedPack = getP12(fileInputStream, keyPass);
	}

	private SignedPack getP12(InputStream fileInputStream, String keyPass) throws SecurityException {
		SignedPack sp = new SignedPack();
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			char[] nPassword = (char[]) null;
			if ((keyPass == null) || (keyPass.trim().equals("")))
				nPassword = (char[]) null;
			else {
				nPassword = keyPass.toCharArray();
			}
			ks.load(fileInputStream, nPassword);
			Enumeration enum2 = ks.aliases();
			String keyAlias = null;
			if (enum2.hasMoreElements()) {
				keyAlias = (String) enum2.nextElement();
			}

			PrivateKey priKey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubKey = cert.getPublicKey();
			sp.setCert((X509Certificate) cert);
			sp.setPubKey(pubKey);
			sp.setPriKey(priKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SecurityException(e.getMessage());
		} finally {
			if (fileInputStream != null)
				try {
					fileInputStream.close();
				} catch (IOException localIOException) {
				}
		}
		return sp;
	}

	public X509Certificate getCert() {
		return signedPack.getCert();
	}

	public PublicKey getPublicKey() {
		return signedPack.getPubKey();
	}

	public PrivateKey getPrivateKey() {
		return signedPack.getPriKey();
	}

	public byte[] getSignData(byte[] indata) throws SecurityException {
		byte[] res = (byte[]) null;
		try {
			Signature signet = Signature.getInstance("SHA1WITHRSA");
			signet.initSign(getPrivateKey());
			signet.update(indata);
			res = signet.sign();
		} catch (InvalidKeyException e) {
			throw new SecurityException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e.getMessage());
		} catch (SignatureException e) {
			throw new SecurityException(e.getMessage());
		}
		return res;
	}
}
