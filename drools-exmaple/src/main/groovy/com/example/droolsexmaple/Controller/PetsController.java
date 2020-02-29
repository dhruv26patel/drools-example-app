package com.example.droolsexmaple.Controller;

import com.example.droolsexmaple.Service.PetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class PetsController {

    @Autowired
    private PetsService petsService;

    @PostMapping(value="/getPets", name="GetPets")
    public ResponseEntity getPets(@RequestParam(name = "parent") String parent) {

        return petsService.getPets();
    }
}
