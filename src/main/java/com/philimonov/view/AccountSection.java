package com.philimonov.view;

import com.philimonov.service.AccountDTO;
import com.philimonov.service.AccountService;
import com.philimonov.service.PersonDTO;

import java.util.List;
import java.util.Optional;

import static com.philimonov.service.ServiceFactory.getAccountService;

public class AccountSection {
    private final PersonDTO personDto;
    private final com.philimonov.service.AccountService accountService;

    public AccountSection(PersonDTO personDto) {
        this.personDto = personDto;
        this.accountService = getAccountService();
    }

    public void showMenu() {
        System.out.println("Выберите действие со счётом: ");

        String[] itemsMenu = {"Показать счета", "Создать", "Удалить", "Выйти"};
        while (true) {
            String choice = Tools.getSelectedMenuItem(itemsMenu);
            if ("Выйти".equals(choice)) {
                break;
            } else if ("Показать счета".equals(choice)) {
                showAccountsList();
            } else if ("Создать".equals(choice)) {
                create();
            } else if ("Удалить".equals(choice)) {
                delete();
            }
        }
    }

    public void showAccountsList() {
        List<AccountDTO> accountDTOList = accountService.findAllByPersonId(personDto.getId());
        if (accountDTOList.isEmpty()) {
            System.out.println("У вас нет счетов");
        } else {
            System.out.printf("%-15s %-30s %s%n", "Id счета", "Имя счета", "Сумма на счете (в копейках)");
            for (AccountDTO item : accountDTOList) {
                System.out.printf("%-15s %-30s %s%n", item.getId(), item.getName(), item.getAmount());
            }
        }
    }

    public void create() {
        System.out.println("Введите название счета: ");
        String accountName = Tools.getNewLine();
        System.out.println("Введите сумму на счете: ");
        long amount = Tools.getLongValue();
        Optional<AccountDTO> account = Optional.ofNullable(accountService.insert(accountName, amount, personDto.getId()));
        if (account.isPresent()) {
            System.out.println("Операция по созданию счёта выполнена успешно.");
        } else {
            System.out.println("Операция по созданию счета завершилась неудачно. Счет не был создан.");
        }
    }

    public void delete() {
        System.out.println("Введите Id счёта для удаления: ");
        int id = Tools.getIntValue();
        boolean result = accountService.delete(id, personDto.getId());
        if(result){
            System.out.println("Операция по удалению счета завершилась успешно.");
        }else {
            System.out.println("Операция по удалению счета завершилась неудачно, счет не был удалён.");
        }
    }
}
