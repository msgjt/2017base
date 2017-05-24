package edu.msg.flightManagementEjb.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PasswordEncrypter {
	private static Logger logger = LoggerFactory.getLogger(PasswordEncrypter.class);

	public static String getGeneratedHashedPassword(final String password, final String salt) {
		String hashedPassword = new String("");
		byte[] initialBytes;
		try {
			initialBytes = (password + salt).getBytes(Constants.ENCODING);
			final MessageDigest algorithm = MessageDigest
					.getInstance(Constants.HASHING_ALGORITHM);
			algorithm.reset();
			algorithm.update(initialBytes);
			final byte[] hashedBytes = algorithm.digest();
			hashedPassword = new String(hashedBytes);
		} catch (UnsupportedEncodingException e) {
			PasswordEncrypter.logger.error("Password encryption failed:unsuported encoding", e);
		} catch (NoSuchAlgorithmException e) {
			PasswordEncrypter.logger.error("Password encryption failed:hashing algorithm not supported", e);
		}
		return hashedPassword;
	}

}