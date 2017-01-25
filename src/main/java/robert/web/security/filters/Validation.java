package robert.web.security.filters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Validation {

    List<String> PUBLIC_URIS = Collections.unmodifiableList(Arrays.asList("/", "/login/**", "/register/**"));

    List<String> PUBLIC_FILES = Collections.unmodifiableList(Arrays.asList(".html", ".js", ".css", ".jpg", ".png", ".jpeg"));

}
