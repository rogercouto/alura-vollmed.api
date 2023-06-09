package med.voll.api.domain.doctor;

public record DoctorListData(
    Long id,
    String name,
    String email,
    String phone,
    String specialty
) {
    public DoctorListData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getPhone(), doctor.getSpecialty().toString());
    }
}
