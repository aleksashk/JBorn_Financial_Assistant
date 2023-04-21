package com.philimonov.view;

import com.philimonov.service.CategoryDTO;
import com.philimonov.service.CategoryService;
import com.philimonov.service.PersonDTO;

import java.util.List;
import java.util.Optional;

public class CategorySection {
    private final PersonDTO personDto;
    private final CategoryService categoryService;

    public CategorySection(PersonDTO personDto) {
        this.personDto = personDto;
        this.categoryService = new CategoryService();
    }

    public void showMenu() {
        System.out.println("Управление типами транзакций.");
        String[] sectionMenu = {"Показать список типов транзакций", "Создать новый тип транзакций",
                "Редактировать тип транзакций", "Удалить тип транзакций", "Выйти"};
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
}
