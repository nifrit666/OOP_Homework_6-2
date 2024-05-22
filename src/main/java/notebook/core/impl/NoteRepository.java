package notebook.core.impl;

import notebook.core.GBRepository;
import notebook.core.mapper.impl.NoteMapper;
import notebook.repository.DBConnector;
import notebook.repository.model.Note;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteRepository implements GBRepository<Note, Long> {

    private final NoteMapper mapper;
    private final DBConnector dbName;

    public NoteRepository(DBConnector dbName) {
        this.mapper = new NoteMapper();
        this.dbName = dbName;
    }

    @Override
    public Note create(Note note) {
        List<Note> notes = findAll();
        long max = 0L;
        for (Note n : notes) {
            long id = n.getId();
            if (max < id){
                max = id;
            }
        }
        long next = max + 1;
        note.setId(next);
        notes.add(note);
        saveAll(notes);
        return note;
    }

    @Override
    public List<Note> findAll() {
        List<String> lines = dbName.readAll();
        List<Note> notes = new ArrayList<>();
        for (String line : lines) {
            notes.add(mapper.toOutput(line));
        }
        return notes;
    }

    @Override
    public Note findById(Long id) {
        List <Note> notes = findAll();
        for (Note note : notes)
            if (note.getId().equals(id)) {
                return note;
            }
        return null;
    }

    @Override
    public Optional<Note> update(Long id, Note note) {
        List <Note> notes = findAll();
        Note editNote = notes.stream()
                .filter(u -> u.getId()
                        .equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException("Note not found"));
        editNote.setId(id);
        editNote.setUserID(note.getUserID());
        editNote.setDate(LocalDate.now());
        editNote.setHead(note.getHead());
        editNote.setBody(note.getBody());
        saveAll(notes);
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        try{
            List <Note> notes = findAll();
            for (int i = notes.size()-1; i >= 0; i--){
                if (notes.get(i).getId().equals(id)){
                    notes.remove(i);
                    saveAll(notes);
                    return true;
                }
            }
        }catch (Exception e){
            System.out.printf("Заметка с идентификатором %s не найдена\n", id);
        }
        return false;
    }

    private void saveAll(List<Note> notes) {
        List<String> lines = new ArrayList<>();
        for (Note n: notes) {
            lines.add(mapper.toInput(n));
        }
        if (!lines.isEmpty()){
            dbName.saveAll(lines);
        }
    }
}
