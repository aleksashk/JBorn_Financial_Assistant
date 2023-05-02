package com.philimonov.web;

import com.philimonov.service.AccountDTO;
import com.philimonov.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class CreateAccountServlet extends AbstractServlet {
    private final AccountService accountService;

    public CreateAccountServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            String name = req.getParameter("name");
            String amount = req.getParameter("amount");
            if (checkString(name) && checkAmount(amount)) {
                Optional<AccountDTO> account = Optional.ofNullable(accountService.insert(name, Long.parseLong(amount), personId.get()));
                if (account.isPresent()) {
                    writer.write("Операция выполнена успешно. Счет создан.");
                } else {
                    writer.write("Возникла ошибка при создании счета. Счет не был создан.");
                }
            } else {
                writer.write("Ошибка. Не указаны параметры запроса или указаны неверно.");
            }
        } else {
            writer.write("Операция недоступна. Вы не авторизованы.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
