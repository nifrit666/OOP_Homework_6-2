package notebook.repository.model;

import java.time.LocalDate;

public class Note implements Comparable<Note>{
    private Long id;
    private Long userID;
    private LocalDate date;
    private String head;
    private String body;


    public Note(Long userID, String head, String body) {
        this.userID = userID;
        this.head = head;
        this.body = body;
        this.date = LocalDate.now();
    }

    public Note(Long id, Long userID, String head, String body) {
        this(userID, head, body);
        this.id = id;
    }

    public Note(Long id, Long userID, LocalDate date, String head, String body) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.head = head;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getHead() {
        return head;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toString() {
        return String.format("Идентификатор: %s, Дата: %s, Тема: %s, Заметка: %s\n", id, date, head, body);
    }

    @Override
    public int compareTo(Note n) {
        return date.compareTo(n.getDate());
    }
}