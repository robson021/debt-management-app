package robert.db.svc.api;

import java.util.List;

import robert.db.entities.Note;
import robert.db.entities.User;
import robert.web.rest.dto.UserInfoDTO;

public interface UserService {

    User saveNewUser(User user);

    User saveNewUser(UserInfoDTO userDTO);

    User findUserByEmail(String email);

    User findUserById(long id);

    List<User> findOtherUsersExceptGiven(long userId);

    List<User> findAllUsers();

    void changePassword(long userId, String newPassword);

    void changeEmail(long userId, String newEmail);

    void saveNewNote(Note note, long userId);

    void deleteNote(long userId, long noteId);

    List<Note> getAllUsersNotes(long userId);

    List<Note> getAllNotes();

}
