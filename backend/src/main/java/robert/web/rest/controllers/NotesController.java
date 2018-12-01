package robert.web.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.entities.Note;
import robert.db.svc.api.NoteService;
import robert.web.security.userdetails.provider.UserDetailsProvider;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {

    private final NoteService noteService;

    private final UserDetailsProvider userDetailsProvider;

    @GetMapping
    public List<Note> getUsersNotes() {
        return noteService.getAllUsersNotes(userDetailsProvider.getUserId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addNote(@RequestBody String note) {
        noteService.saveNewNote(new Note(note), userDetailsProvider.getUserId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeNote(@PathVariable long id) {
        noteService.deleteNote(userDetailsProvider.getUserId(), id);
    }
}
