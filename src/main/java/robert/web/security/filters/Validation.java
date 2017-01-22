package robert.web.security.filters;

public interface Validation {

    String[] PUBLIC_URIS = { "/", "/login/**", "/register/**" };

    String[] PUBLIC_FILES = { ".html", ".js", ".css", ".jpg", ".png", ".jpeg" };

}
