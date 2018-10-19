package robert.db.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.cache.annotations.notes.UserNotesCache;
import robert.cache.annotations.notes.UserNotesCacheEvict;
import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.NoteService;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private static final Logger log = LoggerFactory.getLogger(NoteService.class);

    private final EntityManager em;

    public NoteServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @UserNotesCacheEvict
    public void saveNewNote(Note note, long userId) {
        User user = em.getReference(User.class, userId);
        note.setCreationDate(new Date());
        user.getNotes().add(note);
        note.setUser(user);
        log.info("Adding note for user: {}; content: {}", user.getEmail(), note.getText());
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
