package com.philimonov.view;

import com.philimonov.service.PersonDTO;
import com.philimonov.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

import static com.philimonov.service.ServiceFactory.getTransactionService;

public class TransactionSection {
    private final PersonDTO person;
    private final TransactionService transactionService;

    public TransactionSection(PersonDTO person) {
        this.person = person;
        this.transactionService = getTransactionService();
    }

    public void showMenu() {
        System.out.println("Управление транзакциями.");
        String[] sectionMenu = {"Создать транзакцию", "Выйти"};
        while (true) {
            String choice = Tools.getSelectedMenuItem(sectionMenu);
            if ("Выйти".equals(choice)) {
                break;
            } else if ("Создать транзакцию".equals(choice)) {
                createTransaction();
            }
        }
    }

    private void createTransaction() {
        System.out.print("Введите сумму транзакции (в копейках): ");
        long amount = Tools.getLongFromInput();
        System.out.print("Введите Id счета-источника (0 - если Id не требуется): ");
        int fromAccountId = Tools.getIntValue();
        System.out.print("Введите Id счета назначения (0 - если Id не требуется): ");
        int toAccountId = Tools.getIntValue();
        List<Integer> categoriesList;
        while (true) {
            System.out.print("Введите Id типа транзакции (если типов несколько - введите их Id через пробел): ");
            String categoriesLine = Tools.getNewLine();
            String[] categories = categoriesLine.split("\\s+");
            categoriesList = new ArrayList<>();
            try {
                for (String category : categories) {
                    categoriesList.add(Integer.parseInt(category));
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка. Не могу прочитать данные. Повторите ввод.");
            }
        }
        transactionService.insert(amount, fromAccountId, toAccountId, categoriesList, person.getId());
        System.out.println("Операция выполнена успешно. Транзакция создана.");
    }
}
