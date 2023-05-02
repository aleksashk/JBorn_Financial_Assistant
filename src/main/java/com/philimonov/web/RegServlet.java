package com.philimonov.web;

import com.philimonov.service.AuthService;
import com.philimonov.service.PersonDTO;
import com.philimonov.view.Tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class RegServlet extends AbstractServlet {
    private final AuthService authService;

    public RegServlet() {
        this.authService = getContext().getBean(AuthService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (Tools.checkEmail(email) && Tools.checkPassword(password)) {
            Optional<PersonDTO> person = Optional.ofNullable(authService.registration(email, password));
            if (person.isPresent()) {
                writer.write("Регистрация выполнена успешно.");
            } else {
                writer.write("При регистрации возникли проблемы. Регистрация не была выполнена.");
            }
        } else {
            writer.write("Регистрация не выполнена. Неверный логин или пароль.");
        }
    }
}
