package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Person;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class PersonDTO {

    Long id;
    String fullName;
    String DOB;
    String nationality;
    String fullResAddress;
    String PercentageOfOwnership;
    Boolean PEP;
    Boolean islinkedToPEPpPosition;
    String countryOfResidence;
    String cityOfResidence;

    public static Person toEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setId(personDTO.getId());
        person.setFullName(personDTO.getFullName());
        person.setDOB(personDTO.getDOB());
        person.setNationality(personDTO.getNationality());
        person.setFullResAddress(personDTO.getFullResAddress());
        person.setPercentageOfOwnership(personDTO.getPercentageOfOwnership());
        person.setPEP(personDTO.getPEP());
        person.setIslinkedToPEPpPosition(personDTO.getIslinkedToPEPpPosition());
        person.setCountryOfResidence(personDTO.getCountryOfResidence());
        person.setCityOfResidence(personDTO.getCityOfResidence());
        return person;
    }
}