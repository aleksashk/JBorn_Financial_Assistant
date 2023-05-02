package com.philimonov.web;

import com.philimonov.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class DeleteAccountServlet  extends AbstractServlet {

    private final AccountService accountService;

    public DeleteAccountServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            String id = req.getParameter("id");
            if (checkId(id)) {
                boolean result = accountService.delete(Integer.parseInt(id), personId.get());
                if (result) {
                    writer.write("Операция выполнена успешно. Счет удален.");
                } else {
                    writer.write("Возникла ошибка при удалении счета. Счет не был удален.");
                }
            } else {
                writer.write("Ошибка. Не указан id счета или указан неверно.");
            }
        } else {
            writer.write("Операция недоступна. Вы не авторизованы.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}