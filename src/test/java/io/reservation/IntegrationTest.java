package io.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reservation.exception.InvalidReservationRequestException;
import io.reservation.exception.ReservationNotFoundException;
import io.reservation.web.model.ReservationInfo;
import io.reservation.web.model.ReservationRequest;
import io.reservation.web.model.UpdateReservationRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.reservation.web.ApiConst.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.WWW_AUTHENTICATE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    private static ObjectMapper objectMapper;

    private static UserDetails admin;

    private static UserDetails user;

    private static UserDetails lockedUser;


    @BeforeAll
    public static void init() {
        objectMapper = new ObjectMapper().findAndRegisterModules();

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        admin = User.builder()
                .username("admin").password("admin").roles("ADMIN")
                .passwordEncoder(encoder::encode)
                .build();

        user = User.builder()
                .username("user").password("user").roles("USER")
                .passwordEncoder(encoder::encode)
                .build();

        lockedUser = User.builder()
                .username("locked-user").password("user").roles("USER").accountLocked(true)
                .passwordEncoder(encoder::encode)
                .build();
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(ctx)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testQueryReservationsUnauthenticated() throws Exception {
        mockMvc.perform(get(RESERVATIONS).with(anonymous()))
                .andExpect(unauthenticated())
                .andExpect(status().isUnauthorized())
                .andExpect(header().exists(WWW_AUTHENTICATE));
    }

    @Test
    public void testQueryReservationsUnauthorized() throws Exception {
        mockMvc.perform(get(RESERVATIONS).with(user(lockedUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testQueryReservationsAsAdmin() throws Exception {
        mockMvc.perform(get(RESERVATIONS).with(user(admin)))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", containsString("For Ivan's colleagues")));
    }

    @Test
    public void testQueryReservationsAsUser() throws Exception {
        mockMvc.perform(get(RESERVATION_BY_ID, 3).with(user(user)))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", containsString("For Robert's colleagues")));
    }

    @Test
    public void testDeleteReservationAsAdmin() throws Exception {
        int id = 5;
        mockMvc.perform(delete(RESERVATION_BY_ID, id).with(user(admin)))
                .andExpect(authenticated())
                .andExpect(status().isNoContent());

        mockMvc.perform(get(RESERVATION_BY_ID, id).with(user(user)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ReservationNotFoundException))
                .andExpect(result -> {
                    var ex = (ReservationNotFoundException) result.getResolvedException();
                    assertEquals("id", ex.getProperty(), "Invalid property should be 'id'");
                    assertEquals(String.valueOf(id), ex.getValue(), "Invalid property value mismatch");
                });
    }

    @Test
    public void testUpdateReservation() throws Exception {
        // arrange
        int id = 2;
        byte[] bytes = mockMvc.perform(get(RESERVATION_BY_ID, id).with(user(user)))
                .andReturn().getResponse().getContentAsByteArray();

        var beforeUpdate = objectMapper.readValue(bytes, ReservationInfo.class);
        var updateReq = new UpdateReservationRequest("UPDATED", LocalDate.now().plusMonths(1), null, null);
        byte[] requestBody = objectMapper.writeValueAsBytes(updateReq);

        // act and assert
        mockMvc.perform(put(RESERVATION_BY_ID, id).with(user(user))
                        .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(updateReq.name())))
                .andExpect(jsonPath("$.date", equalTo(updateReq.date().toString())))
                .andExpect(jsonPath("$.time", equalTo(beforeUpdate.time().toString())))
                .andExpect(jsonPath("$.people", equalTo(beforeUpdate.people())));
    }

    @Test
    public void testUpdateReservationEmptyRequest() throws Exception {
        // arrange
        int id = 2;
        byte[] bytes = mockMvc.perform(get(RESERVATION_BY_ID, id).with(user(user)))
                .andReturn().getResponse().getContentAsByteArray();

        var beforeUpdate = objectMapper.readValue(bytes, ReservationInfo.class);
        var updateReq = new UpdateReservationRequest(null, null, null, null);
        byte[] requestBody = objectMapper.writeValueAsBytes(updateReq);

        // act and assert
        mockMvc.perform(put(RESERVATION_BY_ID, id).with(user(user))
                        .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(beforeUpdate.name())))
                .andExpect(jsonPath("$.date", equalTo(beforeUpdate.date().toString())))
                .andExpect(jsonPath("$.time", equalTo(beforeUpdate.time().toString())))
                .andExpect(jsonPath("$.people", equalTo(beforeUpdate.people())));
    }


    @Test
    public void testCreateReservationsAsUser() throws Exception {
        var req = new ReservationRequest(
                "TEST", LocalDate.now().plusDays(1), LocalTime.now(), 5);

        byte[] contentAsByteArray = mockMvc.perform(post(RESERVATIONS)
                        .with(user(user))
                        .content(objectMapper.writeValueAsBytes(req)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", containsString(req.name())))
                .andReturn().getResponse().getContentAsByteArray();

        ReservationInfo created = objectMapper.readValue(contentAsByteArray, ReservationInfo.class);
        assertEquals(req.name(), created.name(), "name does not match");
        assertEquals(req.date(), created.date(), "date does not match");
        assertEquals(req.time(), created.time(), "time does not match");
        assertEquals(req.people(), created.people(), "people does not match");
    }

    @Test
    public void testCreateReservationsInvalidDate() throws Exception {
        var req = new ReservationRequest(
                "TEST", LocalDate.now().minusDays(1), LocalTime.now(), 5);

        mockMvc.perform(post(RESERVATIONS)
                        .with(user(user))
                        .content(objectMapper.writeValueAsBytes(req)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidReservationRequestException))
                .andExpect(result -> {
                    var ex = (InvalidReservationRequestException) result.getResolvedException();
                    assertEquals("date", ex.getProperty(), "Invalid property should be 'date'");
                    assertEquals(req.date().toString(), ex.getValue(), "Invalid property value mismatch");
                });
    }

    @Test
    public void testCreateReservationsBlankName() throws Exception {
        var req = new ReservationRequest("", LocalDate.now(), LocalTime.now(), 5);

        mockMvc.perform(post(RESERVATIONS)
                        .with(user(user))
                        .content(objectMapper.writeValueAsBytes(req)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidReservationRequestException))
                .andExpect(result -> {
                    var ex = (InvalidReservationRequestException) result.getResolvedException();
                    assertEquals("name", ex.getProperty(), "Invalid property should be 'name'");
                    assertEquals(req.name(), ex.getValue(), "Invalid property value mismatch");
                });
    }

    @Test
    public void testCreateReservationsInvalidNumberOfPeople() throws Exception {
        var req = new ReservationRequest(
                "TEST", LocalDate.now().plusDays(1), LocalTime.now(), -5);

        mockMvc.perform(post(RESERVATIONS)
                        .with(user(user))
                        .content(objectMapper.writeValueAsBytes(req)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidReservationRequestException))
                .andExpect(result -> {
                    var ex = (InvalidReservationRequestException) result.getResolvedException();
                    assertEquals("people", ex.getProperty(), "Invalid property should be 'people'");
                    assertEquals(req.people().toString(), ex.getValue(), "Invalid property value mismatch");
                });
    }

    @Test
    public void testAboutEndpoint() throws Exception {
        mockMvc.perform(get(ABOUT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo("RESTaurant reservation API server.")))
                .andExpect(jsonPath("$.version", equalTo("v0.0.1")))
                .andExpect(jsonPath("$.author", equalTo("Georgi Velev")))
                .andExpect(jsonPath("$.apiDocsUrl", equalTo("http://localhost:0/api-docs")))
                .andExpect(jsonPath("$.swaggerUiUrl", equalTo("http://localhost:0/swagger-ui.html")));
    }

}
