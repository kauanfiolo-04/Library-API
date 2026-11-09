package kauanfiolo_04.library_api.controller;

import kauanfiolo_04.library_api.controller.dto.AuthorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @PostMapping
    public ResponseEntity save(@RequestBody AuthorDTO author) {
        return new ResponseEntity("Author saved with success! " + author, HttpStatus.CREATED);
    }
}
