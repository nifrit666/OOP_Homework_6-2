package notebook.repository.comparators;

import notebook.repository.model.Note;

import java.util.Comparator;

public class NoteComparator implements Comparator<Note> {
    @Override
    public int compare(Note n1, Note n2) {
        return n1.getDate().compareTo(n2.getDate());
    }
}
