package com.philimonov.web;

import com.philimonov.service.CategoryDTO;
import com.philimonov.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class CreateCategoryServlet extends AbstractServlet {
    private final CategoryService categoryService;

    public CreateCategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            String name = req.getParameter("name");
            if (checkString(name)) {
                Optional<CategoryDTO> category = Optional.ofNullable(categoryService.insert(name, personId.get()));
                if (category.isPresent()) {
                    writer.write("Операция выполнена успешно. Новый тип транзакций создан.");
                } else {
                    writer.write("Возникла ошибка при создании нового типа транзакций. Новый тип транзакций не был создан.");
                }
            } else {
                writer.write("Ошибка. Не указано имя типа транзакции или указано неверно.");
            }
        } else {
            writer.write("Операция недоступна. Вы не авторизованы.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
