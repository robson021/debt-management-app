package robert.db.svc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.annotations.cache.notes.UserNotesCache;
import robert.annotations.cache.notes.UserNotesCacheEvict;
import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.NoteService;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final EntityManager em;

    @Override
    @UserNotesCacheEvict
    public void saveNewNote(Note note, long userId) {
        User user = em.getReference(User.class, userId);
        note.setCreationDate(new Date());
        note.setUser(user);
        em.persist(note);
        log.info("Adding note '{}' for user {}", note.getText(), user.getEmail());
    }

    @Override
    @UserNotesCacheEvict
    public void deleteNote(long userId, long noteId) {
        Note note = em.createQuery("from Note n where n.id = :nid and n.user.id = :uid", Note.class)
                .setParameter("nid", noteId)
                .setParameter("uid", userId)
                .getSingleResult();

        log.info("Deleting note '{}' of user '{}'", note.getText(), note.getUser().getEmail());
        em.remove(note);
    }

    @Override
    @UserNotesCache
    @Transactional(readOnly = true)
    public List<Note> getAllUsersNotes(long userId) {
        return em.createQuery("from Note n where n.user.id = :id order by creationDate desc", Note.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Note> getAllNotes() {
        return em.createQuery("from Note", Note.class)
                .getResultList();
    }
}
