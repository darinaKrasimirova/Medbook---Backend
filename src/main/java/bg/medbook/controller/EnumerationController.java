package bg.medbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bg.medbook.model.entity.City;
import bg.medbook.model.entity.MedicalField;
import bg.medbook.model.repository.CityRepository;
import bg.medbook.model.repository.MedicalFieldRepository;

@RestController
@RequestMapping(value = "/enumeration")
public class EnumerationController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MedicalFieldRepository medicalFieldRepository;

    @GetMapping(value = "/cities")
    @ResponseBody
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @GetMapping(value = "/medicalFields")
    @ResponseBody
    public List<MedicalField> getMedicalFields() {
        return medicalFieldRepository.findAll();
    }


}
