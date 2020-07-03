package com.example.droolsexample.service;

import com.example.droolsexample.model.Pet;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@SuppressWarnings("unchecked")
public class PetsService {

    @Autowired private KieContainer kieContainer;

    public Pet getPetPicture(String type) throws Exception {

        Pet pet = new Pet();
        pet.setType(type);

        KieSession kieSession = kieContainer.newKieSession();

        kieSession.insert(pet);
        kieSession.fireAllRules();

        kieSession.dispose();

        if (StringUtils.isEmpty(pet.getUrl())) {
            throw new Exception("Not able to set url");
        }

        return pet;
    }
}
