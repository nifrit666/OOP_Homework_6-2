package notebook.ui.impl;

import notebook.core.controller.Commands;
import notebook.core.controller.NoteController;
import notebook.repository.model.Note;
import notebook.ui.View;

import java.util.List;
import java.util.TreeSet;

public class NotebookView implements View {
    private final Long userID;
    private final String userFullName;
    private final NoteController noteController;

    public NotebookView(NoteController noteController, Long userID, String userName) {
        this.userID = userID;
        this.noteController = noteController;
        this.userFullName = userName;
    }

    @Override
    public void run() {
        Commands com = Commands.NONE;
        while (com != Commands.EXIT) {
            showMenu("----- АРХИВ ЗАПИСЕЙ v.2024.05.22 -----\n"
                    + "пользователя " + userFullName + "\n"
                    + "ГЛАВНОЕ МЕНЮ:\n");
            String command = prompt("Введите команду: ").toUpperCase();
            try {
                com = Commands.valueOf(command);
            } catch (Exception e) {
                System.out.println("Unsupported operation. Try again...");
                com = Commands.NONE;
                timeOut();
            }
            switch (com) {
                case CREATE -> {
                    System.out.println("----- Создание новой заметки -----");
                    String head = prompt("Тема: ");
                    String body = prompt("Содержание: ");
                    noteController.createNote(new Note(userID, head, body));
                }
                case READ -> {
                    System.out.println("----- Введите данные заметки для поиска -----");
                    String id = prompt("Идентификатор заметки: ");
                    try {
                        Note note = noteController.readNote(Long.parseLong(id));
                        if (note != null && note.getUserID() == userID) {
                            System.out.println((note));
                        } else {
                            System.out.printf("Заметка с идентификатором %s не найдена.\n", id);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid identificator");
                    }
                    timeOut();
                }
                case LIST -> {
                    try {
                        System.out.println("----- Список заметок -----");
                        List<Note> notes = noteController.readAllNotes();
                        TreeSet<Note> sortedNotes = new TreeSet<>();
                        for (Note note : notes) {
                            if (note.getUserID() == userID) {
                                sortedNotes.add(note);
                            }
                        }
                        for (Note note : sortedNotes){
                            System.out.println(note);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    timeOut();
                }
                case UPDATE -> {
                    try {
                        System.out.println("----- Обновление заметки -----");
                        System.out.println("(Нажмите ENTER, если не хотите менять поле)");
                        Long noteID = Long.parseLong(prompt("Введите идентификатор заметки: "));
                        Note note = noteController.readNote(noteID);
                        if (note != null) {
                            String head = prompt("Тема: ");
                            head = (head.equals("") ? note.getHead() : head);
                            String body = prompt("Содержание: ");
                            body = (body.equals("") ? note.getBody() : body);
                            Note updated = new Note(userID, head, body);
                            noteController.updateNote(noteID, updated);
                        } else {
                            System.out.printf("Пользователь с идентификатором %s не найден.\n", noteID);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid identificator");
                        timeOut();
                    }catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case DELETE -> {
                    try {
                        System.out.println("----- Удаление заметки -----");
                        Long noteID = Long.parseLong(prompt("Введите идентификатор заметки: "));
                        noteController.deleteNote(noteID);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid identificator");
                        timeOut();
                    }
                }
            }
        }
    }
}
