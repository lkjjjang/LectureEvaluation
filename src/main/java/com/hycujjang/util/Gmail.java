package com.hycujjang.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator {

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("lkjjjang1985@gmail.com", "dlrlwnrlatjdal**");
	}
}
