package com.philimonov.web;

import com.philimonov.service.CategoryDTO;
import com.philimonov.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static com.philimonov.SpringContext.getContext;

public class ShowAllCategoriesServlet extends AbstractServlet {

    private final CategoryService categoryService;

    public ShowAllCategoriesServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Optional<Integer> personId = getPersonId(req);
        if (personId.isPresent()) {
            List<CategoryDTO> categoryList = categoryService.findAllByPersonId(personId.get());
            if (categoryList.isEmpty()) {
                writer.write("У Вас пока нет типов транзакций.");
            } else {
                for (CategoryDTO category : categoryList) {
                    writer.write("<p>" + category.getId() + " " + category.getName() + "</p>");
                }
            }
        } else {
            writer.write("Операция недоступна. Вы не авторизованы.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}

