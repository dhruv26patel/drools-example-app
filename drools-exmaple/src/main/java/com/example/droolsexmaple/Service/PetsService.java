package com.example.droolsexmaple.Service;

import com.example.droolsexmaple.model.Pet;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class PetsService {

    @Autowired private KieSession session;

    public ResponseEntity getPets(String type) {

        Pet pet = new Pet();
        pet.setType(type);

        session.insert(pet);
        session.fireAllRules();

        return new ResponseEntity(pet.getUrl(), HttpStatus.OK);
    }
}
