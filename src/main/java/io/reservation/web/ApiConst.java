package io.reservation.web;

/**
 * Class that holds primarily request path URIs as constants.
 *
 * @author Georgi Velev
 */
public class ApiConst {

    public ApiConst() {
        // pass
    }

    public static final String API_BASE = "/api";
    public static final String ABOUT = API_BASE + "/about";
    public static final String AUTHENTICATION = API_BASE + "/authentication";
    public static final String RESERVATIONS = API_BASE + "/reservations";
    public static final String RESERVATION_BY_ID = RESERVATIONS + "/{reservationId}";
    public static final String H2_CONSOLE = "/h2-console/**";
    public static final String[] OPEN_API_RESOURCES = {
            "/api-docs/**", "/api-docs.yaml",
            "/swagger-ui/**", "/swagger-ui.html"
    };

}
