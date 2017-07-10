package edu.msg.jbugs.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.msg.jbugs.util.MessageConstants;

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
@ManagedBean
@RequestScoped
public class LogOutBean implements Serializable {

	private static final long serialVersionUID = -6980802816537432188L;

	private HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public String processLogOut() {
		HttpSession session = getSession();
		session.invalidate();
		return MessageConstants.LOGGEDOUT;
	}

}
