package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientCreateData;
import med.voll.api.domain.patient.PatientDetailData;
import med.voll.api.domain.patient.PatientListData;
import med.voll.api.domain.patient.PatientUpdateData;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patient")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @GetMapping
    public ResponseEntity<Page<PatientListData>> list(Pageable pageable) {
        var page = repository.findAllByActiveTrue(pageable).map(PatientListData::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailData> detail(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        if (!patient.isActive()) {
            throw new EntityNotFoundException(String.format("Patient with id %d not exists or is inactive", id));
        }
        var responseData = new PatientDetailData(patient);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    public ResponseEntity<PatientDetailData> register(@RequestBody @Valid PatientCreateData data, UriComponentsBuilder uriBuilder) {
        var patient = repository.save(new Patient(data));
        var uri = uriBuilder.path("/patient/{id}").buildAndExpand(patient.getId()).toUri();
        var responseData = new PatientDetailData(patient);
        return ResponseEntity.created(uri).body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDetailData> update(@PathVariable Long id, @RequestBody PatientUpdateData data) {
        var patient = repository.getReferenceById(id);
        if (!patient.isActive()) {
            throw new EntityNotFoundException(String.format("Patient with id %d not exists or is inactive", id));
        }
        patient.updateFields(data);
        patient = repository.save(patient);
        return ResponseEntity.ok(new PatientDetailData(patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        patient.deactivate();
        repository.delete(patient);
        return ResponseEntity.noContent().build();
    }

}
