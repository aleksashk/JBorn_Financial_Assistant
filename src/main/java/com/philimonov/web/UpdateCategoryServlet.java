package com.philimonov.web;

import com.philimonov.service.CategoryDTO;
import com.philimonov.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class UpdateCategoryServlet extends AbstractServlet {

    private final CategoryService categoryService;

    public UpdateCategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            if (checkId(id) && checkString(name)) {
                Optional<CategoryDTO> category = Optional.ofNullable(categoryService.update(name, Integer.parseInt(id), personId.get()));
                if (category.isPresent()) {
                    writer.write("Операция выполнена успешно. Указанный тип транзакций изменен.");
                } else {
                    writer.write("Возникла ошибка при редактировании этого типа транзакций. Тип транзакций не был изменен.");
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
