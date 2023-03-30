package med.voll.api.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum SpecialtyData {

    ORTHOPEDICS("orthopedics"),
    CARDIOLOGY("cardiology"),
    GYNECOLOGY("gynecology"),
    DERMATOLOGY("dermatology");

    public final String name;

    SpecialtyData(String name) {
        this.name = name;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SpecialtyData fromString(String name) {
        return Arrays.stream(SpecialtyData.values())
                .filter(e -> e.name.equals(name))
                .findFirst()
                .orElse(null);
    }
}
