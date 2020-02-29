package com.example.droolsexmaple.Service;

import com.example.droolsexmaple.model.Pet;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class PetsService {

    @Autowired private KieSession session;

    public Pet getPetPicture(String type) {

        Pet pet = new Pet();
        pet.setType(type);

        session.insert(pet);
        session.fireAllRules();

        return pet;
    }
}
