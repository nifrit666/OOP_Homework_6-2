package notebook.core.mapper.impl;

import notebook.core.mapper.Mapper;
import notebook.repository.model.Note;
import java.time.LocalDate;

public class NoteMapper implements Mapper<Note, String> {
    @Override
    public String toInput(Note note) {
        return String.format("%s;%s;%s;%s;%s", note.getId(),note.getUserID(),
                note.getDate(),note.getHead(),note.getBody());
    }

    @Override
    public Note toOutput(String s) {
        String[] lines = s.replace("\n","").split(";");
        long id, userID;
        if (isDigit(lines[0])&&isDigit(lines[1])) {
            id = Long.parseLong(lines[0]);
            userID = Long.parseLong(lines[1]);
            return new Note(id, userID, LocalDate.parse(lines[2]), lines[3],lines[4]);
        }
        throw new NumberFormatException("It must be a large number");
    }

    private boolean isDigit(String s) throws NumberFormatException {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
