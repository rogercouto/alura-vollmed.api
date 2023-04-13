package med.voll.api.controller;

import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.appointment.AppointmentDetailData;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.common.Address;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorDetailData;
import med.voll.api.domain.doctor.Specialty;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientDetailData;
import med.voll.api.service.AppointmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AppointmentScheduleData> requestJson;

    @Autowired
    private JacksonTester<AppointmentDetailData> responseJson;

    @MockBean
    private AppointmentService service;

    private static final Doctor DOCTOR_1 = new Doctor(
        null,
        "Dr House",
        "drhouse@gmail.com",
        "5532612345678",
        "123456",
        Specialty.CARDIOLOGY,
        new Address("Rua 1",
                "Bairro 1",
                "12345-678",
                "Cidade 1",
                "RS",
                "Complemento 1",
                "1"),
        true
    );

    private static final Patient PATIENT_1 = new Patient(
            null,
            "John Doe",
            "jhondoe@gmail.com",
            "5532612345678",
            "12345678901",
            new Address("Rua 2",
                    "Bairro 2",
                    "12345-678",
                    "Cidade 2",
                    "RS",
                    "Complemento 2",
                    "2"),
            true
    );

    @Test
    @DisplayName("Should get a 400 when trying to schedule an appointment without a body")
    @WithMockUser
    void schedule_scenario1() throws Exception {
        mockMvc.perform(post("/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get a 201 when trying to schedule an appointment with a valid body")
    @WithMockUser
    void schedule_scenario2() throws Exception {
        var mockData = new AppointmentScheduleData(
            1L,
            1L,
            LocalDateTime.now().plusDays(1),
            null
        );

        when(service.schedule(mockData)).thenReturn(
            new Appointment(null, DOCTOR_1, PATIENT_1, LocalDateTime.now().plusDays(1))
        );

        mockMvc.perform(post("/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson.write(mockData).getJson()))
                .andExpect(result -> responseJson.parseObject(result.getResponse().getContentAsString()));
    }

}