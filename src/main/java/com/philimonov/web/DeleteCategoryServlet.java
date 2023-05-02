package com.philimonov.web;

import com.philimonov.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class DeleteCategoryServlet extends AbstractServlet {

    private final CategoryService categoryService;

    public DeleteCategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            String id = req.getParameter("id");
            if (checkId(id)) {
                boolean result = categoryService.delete(Integer.parseInt(id), personId.get());
                if (result) {
                    writer.write("Операция выполнена успешно. Указанный тип транзакций удален.");
                } else {
                    writer.write("Возникла ошибка при удалении типа транзакций. Тип транзакций не был удален.");
                }
            } else {
                writer.write("Ошибка. Не указан id типа транзакции или указан неверно.");
            }
        } else {
            writer.write("Операция недоступна. Вы не авторизованы.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
