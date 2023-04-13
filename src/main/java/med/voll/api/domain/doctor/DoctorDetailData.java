package med.voll.api.domain.doctor;

import med.voll.api.domain.common.AddressData;

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
