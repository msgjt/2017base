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
package edu.msg.jbugs.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabelProvider {
	private static final String BUNDLE_NAME = "messages";
	private static Logger logger = LoggerFactory.getLogger(LabelProvider.class);
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle(LabelProvider.BUNDLE_NAME,
			FacesContext.getCurrentInstance().getViewRoot().getLocale());

	public static String getLabel(final String key) {
		try {
			return new String(LabelProvider.resourceBundle.getString(key).getBytes("UTF-8"));
		} catch (final MissingResourceException e) {
			logger.error("PropertyProvider: Missing resource! " + e);
			return "N/A";// the hard coded label if no default label is found
		} catch (UnsupportedEncodingException e) {
			logger.error("PropertyProvider: Unsupported encoding! " + e);
			return LabelProvider.resourceBundle.getString(key);
		}
	}

	public static void setLocale(final Locale locale) {
		LabelProvider.resourceBundle = ResourceBundle.getBundle(LabelProvider.BUNDLE_NAME, locale);
	}
}
