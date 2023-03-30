package med.voll.api.model;

import med.voll.api.model.dto.SpecialtyData;

public enum Specialty {

    ORTHOPEDICS,
    CARDIOLOGY,
    GYNECOLOGY,
    DERMATOLOGY;

    public static Specialty fromRequest(SpecialtyData request) {
        switch (request) {
            case ORTHOPEDICS -> {
                return ORTHOPEDICS;
            }
            case CARDIOLOGY -> {
                return CARDIOLOGY;
            }
            case GYNECOLOGY -> {
                return GYNECOLOGY;
            }
            case DERMATOLOGY -> {
                return DERMATOLOGY;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase().concat(name().substring(1).toLowerCase());
    }

}
