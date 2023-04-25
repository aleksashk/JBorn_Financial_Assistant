package com.philimonov.view;

import com.philimonov.service.CategoryDTO;
import com.philimonov.service.CategoryService;
import com.philimonov.service.PersonDTO;
import com.philimonov.service.ReportCategoryDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.philimonov.service.ServiceFactory.getCategoryService;

public class CategorySection {
    private final PersonDTO personDto;
    private final CategoryService categoryService;

    public CategorySection(PersonDTO personDto) {
        this.personDto = personDto;
        this.categoryService = getCategoryService();
    }

    public void showMenu() {
        System.out.println("Управление типами транзакций.");
        String[] sectionMenu = {"Показать список типов транзакций", "Создать новый тип транзакций",
                "Редактировать тип транзакций", "Удалить тип транзакций", "Показать доход по типам транзакций за период времени", "Показать расход по типам транзакций за период времени", "Выйти"};
        while (true) {
            String choice = Tools.getSelectedMenuItem(sectionMenu);
            if ("Выйти".equals(choice)) {
                break;
            } else if ("Показать список типов транзакций".equals(choice)) {
                showAllCategories();
            } else if ("Создать новый тип транзакций".equals(choice)) {
                createCategory();
            } else if ("Редактировать тип транзакций".equals(choice)) {
                editCategory();
            } else if ("Удалить тип транзакций".equals(choice)) {
                deleteCategory();
            }else if ("Показать доход по типам транзакций за период времени".equals(choice)) {showIncomeReportByCategory();
            }else if ("Показать расход по типам транзакций за период времени".equals(choice)) {
                showExpenseReportByCategory();
            }
        }
    }

    public void showAllCategories() {
        List<CategoryDTO> categoryList = categoryService.findAllByPersonId(personDto.getId());
        if (categoryList.isEmpty()) {
            System.out.println("У Вас пока нет типов транзакций.");
        } else {
            System.out.printf("%-15s %s%n", "Id типа", "Имя типа");
            for (CategoryDTO category : categoryList) {
                System.out.printf("%-15s %s%n", category.getId(), category.getName());
            }
        }
    }

    public void createCategory() {
        System.out.print("Введите название нового типа транзакций: ");
        String name = Tools.getNewLine();
        Optional<CategoryDTO> category = Optional.ofNullable(categoryService.insert(name, personDto.getId()));
        if (category.isPresent()) {
            System.out.println("Операция выполнена успешно. Новый тип транзакций создан.");
        } else {
            System.out.println("Возникла ошибка при создании нового типа транзакций. Новый тип транзакций не был создан.");
        }
    }

    public void editCategory() {
        System.out.print("Введите Id типа транзакции: ");
        int id = Tools.getIntValue();
        System.out.print("Введите новое название для этого типа транзакций: ");
        String name = Tools.getNewLine();
        Optional<CategoryDTO> category = Optional.ofNullable(categoryService.update(name, id, personDto.getId()));
        if (category.isPresent()) {
            System.out.println("Операция выполнена успешно. Указанный тип транзакций изменен.");
        } else {
            System.out.println("Возникла ошибка при редактировании этого типа транзакций. Тип транзакций не был изменен.");
        }
    }

    public void deleteCategory() {
        System.out.print("Введите Id типа транзакции: ");
        int id = Tools.getIntValue();
        boolean result = categoryService.delete(id, personDto.getId());
        if (result) {
            System.out.println("Операция выполнена успешно. Указанный тип транзакций удален.");
        } else {
            System.out.println("Возникла ошибка при удалении типа транзакций. Тип транзакций не был удален.");
        }
    }
    public void showIncomeReportByCategory() {
        System.out.print("Введите дату начала периода времени (в формате dd-MM-yyyy): ");
        Date from = Tools.getDateFromInput();
        System.out.print("Введите дату окончания периода времени (в формате dd-MM-yyyy): ");
        Date to = Tools.getDateFromInput();
        List<ReportCategoryDTO> reportsList = categoryService.getIncomeReportByCategory(from, to, personDto.getId());
        if (reportsList.isEmpty()) {
            System.out.println("Вы не проводили транзакции за указанный период времени.");
        } else {
            System.out.printf("%-30s %s%n", "Тип транзакции", "Доход по типу транзакций (в копейках)");
            for (ReportCategoryDTO report : reportsList) {
                System.out.printf("%-30s %s%n", report.getName() , report.getAmount());
            }
        }
    }

    public void showExpenseReportByCategory() {
        System.out.print("Введите дату начала периода времени (в формате dd-MM-yyyy): ");
        Date from = Tools.getDateFromInput();
        System.out.print("Введите дату окончания периода времени (в формате dd-MM-yyyy): ");
        Date to = Tools.getDateFromInput();
        List<ReportCategoryDTO> reportsList = categoryService.getExpenseReportByCategory(from, to, personDto.getId());
        if (reportsList.isEmpty()) {
            System.out.println("Вы не проводили транзакции за указанный период времени.");
        } else {
            System.out.printf("%-30s %s%n", "Тип транзакции", "Расход по типу транзакций (в копейках)");
            for (ReportCategoryDTO report : reportsList) {
                System.out.printf("%-30s %s%n", report.getName() , report.getAmount());
            }
        }
    }
}
