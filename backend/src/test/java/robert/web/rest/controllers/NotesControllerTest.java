package robert.web.rest.controllers;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import robert.db.entities.Note;
import robert.db.svc.api.NoteService;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.security.userdetails.provider.UserDetailsProvider;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotesControllerTest extends SpringWebMvcTest {

    private static final List<Note> NOTES = Arrays.asList(new Note("Note 1"), new Note("Note 2"));

    @MockBean
    private NoteService noteService;

    @MockBean
    private UserDetailsProvider userDetailsProvider;

    @Before
    public void setup() {
        given(noteService.getAllUsersNotes(anyLong())).willReturn(NOTES);
        given(userDetailsProvider.getUserId()).willReturn(1L);
    }

    @Test
    public void getUsersNotes() throws Exception {
        String json = mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        NOTES.forEach(note -> Assertions.assertThat(json).contains(note.getText()));
    }

    @Test
    public void addNote() throws Exception {
        mockMvc.perform(post("/notes").content(TestUtils.asJsonString(new Note("example note")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(noteService).saveNewNote(any(Note.class), anyLong());
    }

    @Test
    public void removeNote() throws Exception {
        mockMvc.perform(delete("/notes/1/"))
                .andExpect(status().isOk());

        verify(noteService).deleteNote(anyLong(), anyLong());
    }
}