package com.philimonov.view;

import com.philimonov.service.PersonDTO;

public class PersonalCabinet {
    private final PersonDTO person;

    public PersonalCabinet(PersonDTO person) {
        this.person = person;
    }

    public void showMenu() {
        System.out.println("Личный кабинет.");
        String[] cabinetMenu = { "Счета", "Типы транзакций",  "Выйти" };
        while (true) {
            String choice = Tools.getSelectedMenuItem(cabinetMenu);
            if ("Выйти".equals(choice)) {
                break;
            } else if ("Счета".equals(choice)) {
                new AccountSection(person).showMenu();
            } else if ("Типы транзакций".equals(choice)) {
                new CategorySection(person).showMenu();
            }
        }
    }
}
