package com.example.droolsexmaple.Service;

import com.example.droolsexmaple.model.Pet;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PetsService {

    @Autowired
    private KieSession session;

    public ResponseEntity getPets() {

        Pet pet = new Pet();
        pet.setName("Ash");
        pet.setType("Dog");

        session.insert(pet);
        session.fireAllRules();

        return new ResponseEntity(pet, HttpStatus.OK);
    }

}
