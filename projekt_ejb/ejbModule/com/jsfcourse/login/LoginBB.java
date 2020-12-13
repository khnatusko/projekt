package com.jsfcourse.login;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import projekt.dao.UserDAO;
import projektcourse.entities.User;

@Named
@RequestScoped
public class LoginBB {
	private static final String PAGE_MAIN = "";
	private static final String PAGE_LOGIN = "";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String login;
	private String pass;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Inject
	UserDAO userDAO;

	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		
		User user = userDAO.getUserFromDatabase(login, pass);

		
		if (user == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Niepoprawny login lub has�o", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		
		
		RemoteClient<User> client = new RemoteClient<User>(); 
		client.setDetails(user);
		
		List<String> roles = userDAO.getUserRolesFromDatabase(user);  
		
		if (roles != null) { 
			for (String role: roles) {
				client.getRoles().add(role);
			}
		}
	
		
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);

		
		return PAGE_MAIN;
	}
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		session.invalidate();
		return PAGE_LOGIN;
	}
	
}
