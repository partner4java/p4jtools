package com.dazongwuliu.p4jtools.str;

import org.junit.Test;

import com.partner4java.p4jtools.str.AESHelper;

public class AESHelperTest {

	@Test
	public void testGenerateKey(){
		AESHelper.generateKey("D://userpass6.aes");
	}
}
