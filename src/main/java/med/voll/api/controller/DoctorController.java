package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.model.Doctor;
import med.voll.api.model.dto.DoctorDetailData;
import med.voll.api.model.dto.DoctorCreateData;
import med.voll.api.model.dto.DoctorUpdateData;
import med.voll.api.model.dto.DoctorListData;
import med.voll.api.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @GetMapping
    public ResponseEntity<Page<DoctorListData>> list(@PageableDefault(size = 10, page = 0, sort = {"name"}) Pageable pageable) {
        var page = repository.findAllByActiveTrue(pageable).map(DoctorListData::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailData> get(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        if (!doctor.isActive()) {
            throw new EntityNotFoundException(String.format("Doctor with id %d not exists or is inactive", id));
        }
        return ResponseEntity.ok(new DoctorDetailData(doctor));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorDetailData> register(@RequestBody @Valid DoctorCreateData doctorCreateData, UriComponentsBuilder uriBuilder) {
        var doctor = repository.save(new Doctor(doctorCreateData));
        var uri = uriBuilder.path("/doctor/{id}").buildAndExpand(doctor.getId()).toUri();
        var responseData = new DoctorDetailData(doctor);
        return ResponseEntity.created(uri).body(responseData);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DoctorDetailData> update(@PathVariable Long id, @RequestBody DoctorUpdateData doctorUpdateData) {
        var doctor = repository.getReferenceById(id);
        if (!doctor.isActive()) {
            throw new EntityNotFoundException(String.format("Doctor with id %d not exists or is inactive", id));
        }
        doctor.updateFields(doctorUpdateData);
        doctor = repository.save(doctor);
        return ResponseEntity.ok(new DoctorDetailData(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Doctor doctor = repository.getReferenceById(id);
        doctor.deactivate();
        repository.save(doctor);
        return ResponseEntity.noContent().build();
    }


}
