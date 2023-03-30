package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.infra.security.TokenService;
import med.voll.api.model.User;
import med.voll.api.model.dto.AuthResponseData;
import med.voll.api.model.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity authenticate(@RequestBody @Valid UserData userData) {
        var authToken = new UsernamePasswordAuthenticationToken(userData.username(), userData.password());
        var auth = authManager.authenticate(authToken);
        var user = (User)auth.getPrincipal();
        var generatedToken = tokenService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseData(generatedToken));
    }

}
