package com.philimonov.web;

import com.philimonov.service.AccountDTO;
import com.philimonov.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class ShowAllAccountsServlet extends AbstractServlet {

    private final AccountService accountService;

    public ShowAllAccountsServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            List<AccountDTO> accountList = accountService.findAllByPersonId(personId.get());
            if (accountList.isEmpty()) {
                writer.write("У Вас пока нет счетов.");
            } else {
                for (AccountDTO account : accountList) {
                    writer.write("<p>" + account.getId() + " " + account.getName() + " " + account.getAmount() + "</p>");
                }
            }
        } else {
            writer.write("Операция недоступна. Вы не авторизованы.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
