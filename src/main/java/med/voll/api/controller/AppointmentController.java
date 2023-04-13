package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentDetailData;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.DataValidationException;
import med.voll.api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("appointment")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentDetailData> schedule(@RequestBody @Valid AppointmentScheduleData data, UriComponentsBuilder uriBuilder) throws DataValidationException {
        var appointment = service.schedule(data);
        var uri = uriBuilder.path("/appointment/{id}").buildAndExpand(appointment.getId()).toUri();
        var returnBody = new AppointmentDetailData(appointment);
        return ResponseEntity.created(uri).body(returnBody);
    }

}
