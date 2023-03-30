package med.voll.api.model.dto;

import med.voll.api.model.Doctor;

public record DoctorDetailData(
    String name,
    String email,
    String phone,
    String specialty,
    AddressData address
) {

    public DoctorDetailData(Doctor doctor) {
        this(
            doctor.getName(),
            doctor.getEmail(),
            doctor.getPhone(),
            doctor.getSpecialty().toString(),
            new AddressData(doctor.getAddress())
        );
    }

}
