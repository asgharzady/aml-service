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

    public Person toEntity() {
        Person person = new Person();
        person.setId(this.id);
        person.setFullName(this.fullName);
        person.setDOB(this.DOB);
        person.setNationality(this.nationality);
        person.setFullResAddress(this.fullResAddress);
        person.setPercentageOfOwnership(this.PercentageOfOwnership);
        person.setPEP(this.PEP);
        person.setIslinkedToPEPpPosition(this.islinkedToPEPpPosition);
        person.setCountryOfResidence(this.countryOfResidence);
        person.setCityOfResidence(this.cityOfResidence);
        return person;
    }
}