package com.example.droolsexmaple.Service;

import com.example.droolsexmaple.model.Pet;
import org.apache.poi.util.StringUtil;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@SuppressWarnings("unchecked")
public class PetsService {

    @Autowired private KieSession session;

    public Pet getPetPicture(String type) throws Exception {

        Pet pet = new Pet();
        pet.setType(type);

        session.insert(pet);
        session.fireAllRules();

        if(StringUtils.isEmpty(pet.getUrl())) {
            throw new Exception("Not able to set url");
        }

        return pet;
    }
}
