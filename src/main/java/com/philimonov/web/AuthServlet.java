package com.philimonov.web;

import com.philimonov.service.AuthService;
import com.philimonov.service.PersonDTO;
import com.philimonov.view.Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class AuthServlet extends AbstractServlet {
    private final AuthService authService;

    public AuthServlet() {
        this.authService = getContext().getBean(AuthService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html:charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if(Tools.checkEmail(email) && Tools.checkPassword(password
        )){
            Optional<PersonDTO> person = Optional.ofNullable(authService.auth(email, password));
            if (person.isPresent()) {
                HttpSession session = req.getSession();
                session.setAttribute("personId", person.get().getId());
                writer.write("Авторизация выполнена успешно.");
            } else {
                writer.write("Авторизация не выполнена. Вы не зарегистрированы.");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            writer.write("Авторизация не выполнена. Неверный логин или пароль.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
