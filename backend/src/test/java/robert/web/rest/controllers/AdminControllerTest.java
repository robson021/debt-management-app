package robert.web.rest.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import robert.db.entities.Note;
import robert.db.entities.User;
import robert.db.svc.api.UserService;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.svc.api.UserDetailsProvider;

public class AdminControllerTest extends SpringWebMvcTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserDetailsProvider userDetailsProvider;

    @Test
    public void getAllUsers() throws Exception {
        List<User> users = Arrays.asList(TestUtils.generateNewUserWithId(), TestUtils.generateNewUserWithId(), TestUtils.generateNewUserWithId());

        given(userService.findAllUsers()).willReturn(users);

        String response = mockMvc.perform(get("/admin/all-users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserInfoDTO[] userInfoDTOs = TestUtils.jsonToObject(response, UserInfoDTO[].class);

        Assertions.assertThat(users.size())
                .isEqualTo(userInfoDTOs.length);

        for (int i = 0; i < users.size(); i++) {
            User userA = users.get(i);
            UserInfoDTO userB = userInfoDTOs[i];

            Assertions.assertThat(userA.getId())
                    .isEqualTo(userB.getId());

            Assertions.assertThat(userA.getEmail())
                    .isEqualTo(userB.getEmail());
        }
    }

    @Test
    public void getAllNotes() throws Exception {
        final String TEXT = "abc xyz";
        List<Note> notes = Arrays.asList(new Note(TEXT), new Note(TEXT));

        given(userService.getAllNotes()).willReturn(notes);

        String response = mockMvc.perform(get("/admin/all-notes"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Note[] notesArray = TestUtils.jsonToObject(response, Note[].class);

        Assertions.assertThat(notes.size())
                .isEqualTo(notesArray.length);

        Arrays.stream(notesArray)
                .forEach(note -> Assertions.assertThat(note.getText())
                        .isEqualTo(TEXT));
    }

    @Test
    public void sendLogs() throws Exception {
        when(userDetailsProvider.getUserEmail()).thenReturn("admin@test.pl");

        mockMvc.perform(post("/admin/send-server-logs"))
                .andExpect(status().isOk());
    }

}