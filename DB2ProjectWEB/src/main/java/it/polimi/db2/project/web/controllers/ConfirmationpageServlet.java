package it.polimi.db2.project.web.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;

@WebServlet(name = "ConfirmationpageServlet", value = "/confirmationpage")
public class ConfirmationpageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	ServicePackageEntity servicePackage;
    boolean creatingPackage = true;
    String rejectedOrderID;

	public void init() {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		UserEntity user = (UserEntity) session.getAttribute("user");

		rejectedOrderID = req.getParameter("rejectedOrder");

		if (rejectedOrderID != null) {
			//servicePackage = userService.findOrderByID(Long.parseLong(rejectedOrderID)).get().getServicePackage();
			creatingPackage = false;
		} else {
			servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");
			creatingPackage = true;
		}

		req.setAttribute("servicePackage", servicePackage);

		String path;resp.setContentType("text/html");
		ServletContext servletContext = getServletContext();
		WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
		path="/WEB-INF/confirmationpage.html";
		templateEngine.process(path,ctx,resp.getWriter());
	}

}
