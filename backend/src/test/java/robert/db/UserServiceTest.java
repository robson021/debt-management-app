package robert.db;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.exeptions.NoteNotFoundException;
import robert.tools.SpringTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.UserInfoDTO;

public class UserServiceTest extends SpringTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @Before
    public void setup() {
        user = userService.saveNewUser(TestUtils.generateNewUser());
    }

    @Test
    public void findOtherUsersExceptGiven() {
        List<User> otherUsers = userService.findOtherUsersExceptGiven(user.getId());

        Assertions.assertThat(otherUsers)
                .doesNotContain(user);
    }

    @Test
    public void findUserByEmail() {
        User userByEmail = userService.findUserByEmail(user.getEmail());
        Assertions.assertThat(userByEmail)
                .isEqualTo(user);
    }

    @Test
    @DirtiesContext
    public void findAllUsers() {
        userService.findAllUsers();
    }

    @Test
    public void saveNewDtoUser() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setAccountNo("12342422");
        userInfoDTO.setEmail("dto.test@aa.com");
        userInfoDTO.setName("aaaaaa");
        userInfoDTO.setSurname("bbbb");
        userInfoDTO.setPassword("Passs.123");

        User user = userService.saveNewUser(userInfoDTO);

        Assertions.assertThat(user.getId())
                .isGreaterThan(0);
    }

    @Test
    public void changePassword() {
        String newPassword = "newPassword";
        userService.changePassword(user.getId(), newPassword);
        user = userService.findUserById(user.getId());

        boolean arePasswordsEqual = passwordEncoder.matches(newPassword, user.getPassword());
        Assert.assertTrue(arePasswordsEqual);
    }

    @Test
    public void changeEmail() {
        final String newEmail = "new@email.com";
        userService.changeEmail(user.getId(), newEmail);

        String email = userService.findUserById(user.getId())
                .getEmail();

        Assertions.assertThat(newEmail)
                .isEqualTo(email);
    }

    @Test
    public void saveNewNote() {
        Note note = createNote();
        userService.saveNewNote(note, user.getId());

        List<Note> allNotes = userService.getAllNotes();
        Assertions.assertThat(allNotes)
                .hasSize(1);

        note = allNotes.get(0);
        Assertions.assertThat(note.getText())
                .isEqualTo("sample text");
    }

    @Test(expected = NoteNotFoundException.class)
    public void getAllUserNotesAndDeleteOne() {
        final int NOTES_COUNT = 5;
        for (int i = 0; i < NOTES_COUNT; i++) {
            userService.saveNewNote(createNote(), user.getId());
        }

        List<Note> allUsersNotes = userService.getAllUsersNotes(user.getId());
        Assertions.assertThat(allUsersNotes.size())
                .isEqualTo(NOTES_COUNT);

        int noteId = new Random().nextInt(NOTES_COUNT + 1);
        userService.deleteNote(user.getId(), noteId);

        allUsersNotes = userService.getAllUsersNotes(user.getId());
        Assertions.assertThat(allUsersNotes.size())
                .isEqualTo(NOTES_COUNT - 1);

        userService.deleteNote(user.getId(), -1L);
    }

    @Test
    public void getAllNotes() {
        final int NOTES_COUNT = 2;
        IntStream.range(0, NOTES_COUNT)
                .forEach(i -> userService.saveNewNote(createNote(), user.getId()));

        Assertions.assertThat(userService.getAllNotes()
                .size())
                .isGreaterThanOrEqualTo(NOTES_COUNT);
    }

    private Note createNote() {
        Note note = new Note();
        note.setText("sample text");

        return note;
    }

}
