package com.philimonov.web;

import com.philimonov.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class CreateTransactionServlet extends AbstractServlet {

    private final TransactionService transactionService;

    public CreateTransactionServlet() {
        this.transactionService = getContext().getBean(TransactionService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            String amount = req.getParameter("amount");
            String fromId = req.getParameter("from");
            String toId = req.getParameter("to");
            String[] categories = req.getParameterValues("category");
            if (checkAmount(amount) && checkId(fromId) && checkId(toId) && checkArrayId(categories)) {
                List<Integer> categoriesList = new ArrayList<>();
                for (String category : categories) {
                    categoriesList.add(Integer.parseInt(category));
                }
                transactionService.insert(Long.parseLong(amount), Integer.parseInt(fromId),
                        Integer.parseInt(toId), categoriesList, personId.get());
                writer.write("Операция выполнена успешно. Транзакция создана.");
            } else {
                writer.write("Ошибка. Не указаны параметры запроса или указаны неверно.");
            }
        } else {
            writer.write("Операция недоступна. Вы не авторизованы.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}