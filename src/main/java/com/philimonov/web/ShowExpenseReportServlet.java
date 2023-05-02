package com.philimonov.web;

import com.philimonov.service.CategoryService;
import com.philimonov.service.ReportCategoryDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class ShowExpenseReportServlet extends AbstractServlet {

    private final CategoryService categoryService;

    public ShowExpenseReportServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            Date from = getDate(req.getParameter("from"));
            Date to = getDate(req.getParameter("to"));
            if (from != null && to != null) {
                List<ReportCategoryDTO> reportsList = categoryService.getExpenseReportByCategory(from, to, personId.get());
                if (reportsList.isEmpty()) {
                    writer.write("Вы не проводили транзакции за указанный период времени.");
                } else {
                    for (ReportCategoryDTO report : reportsList) {
                        writer.write("<p>" + report.getName() + " " + report.getAmount() + "</p");
                    }
                }
            } else {
                writer.write("Ошибка. Неверный формат даты.");
            }
        } else {
            writer.write("Операция недоступна. Вы не авторизованы.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
