/**
 *  Copyright (C) 2017 java training
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.msg.jbugs.util;

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
			final MessageDigest algorithm = MessageDigest.getInstance(Constants.HASHING_ALGORITHM);
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