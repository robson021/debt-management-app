package robert.db.svc.api;

import robert.db.entities.Note;

import java.util.List;

public interface NoteService {

    void saveNewNote(Note note, long userId);

    void deleteNote(long userId, long noteId);

    List<Note> getAllUsersNotes(long userId);

    List<Note> getAllNotes();
}
