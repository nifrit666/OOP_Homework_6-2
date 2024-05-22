package notebook.core.controller;

import notebook.core.GBRepository;
import notebook.repository.model.Note;

import java.util.List;

public class NoteController {
    private final GBRepository<Note, Long> repository;

    public NoteController(GBRepository<Note, Long> repository) {
        this.repository = repository;
    }
    public void createNote(Note note) {
        repository.create(note);
    }
    public Note readNote(Long noteID) {
        return repository.findById(noteID);
    }
    public List<Note> readAllNotes() {
        return repository.findAll();
    }
    public void updateNote(Long noteID, Note note) {
        note.setId(noteID);
        repository.update(noteID, note);
    }
    public void deleteNote(Long noteID) {
        repository.delete(noteID);
    }
}
