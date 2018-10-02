package robert.db;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.NoteService;
import robert.db.svc.api.UserService;
import robert.tools.SpringTest;
import robert.tools.TestUtils;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class NoteServiceTest extends SpringTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setup() {
        user = userService.saveNewUser(TestUtils.generateNewUser());
    }

    @Test
    public void saveNewNote() {
        Note note = createNote();
        noteService.saveNewNote(note, user.getId());

        List<Note> allNotes = noteService.getAllNotes();
        Assertions.assertThat(allNotes)
                .hasSize(1);

        note = allNotes.get(0);
        Assertions.assertThat(note.getText())
                .isEqualTo("sample text");
    }

    @Test(expected = NoResultException.class)
    public void getAllUserNotesAndDeleteOne() {
        final int NOTES_COUNT = 5;
        for (int i = 0; i < NOTES_COUNT; i++) {
            noteService.saveNewNote(createNote(), user.getId());
        }

        List<Note> allUsersNotes = noteService.getAllUsersNotes(user.getId());
        Assertions.assertThat(allUsersNotes.size())
                .isEqualTo(NOTES_COUNT);

        int noteId = new Random().nextInt(NOTES_COUNT) + 1;
        noteService.deleteNote(user.getId(), noteId);

        allUsersNotes = noteService.getAllUsersNotes(user.getId());
        Assertions.assertThat(allUsersNotes.size())
                .isEqualTo(NOTES_COUNT - 1);

        noteService.deleteNote(user.getId(), -1L);
    }

    @Test
    public void getAllNotes() {
        final int NOTES_COUNT = 2;
        IntStream.range(0, NOTES_COUNT)
                .forEach(i -> noteService.saveNewNote(createNote(), user.getId()));

        Assertions.assertThat(noteService.getAllNotes()
                .size())
                .isGreaterThanOrEqualTo(NOTES_COUNT);
    }

    private Note createNote() {
        Note note = new Note();
        note.setText("sample text");

        return note;
    }

}
