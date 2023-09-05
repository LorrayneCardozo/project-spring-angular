package project.backend.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LogoutController {

    @GetMapping("/logged-out")
    public ResponseEntity<String> loggedOut() {
        return ResponseEntity.ok("Logged out successfully.");
    }
}
