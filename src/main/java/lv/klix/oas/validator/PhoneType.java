package lv.klix.oas.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum PhoneType {

    LATVIA("^\\+371\\d{8}$"),
    INTERNATIONAL("^\\+[0-9]{11,15}$");

    private final String regex;

    public static List<PhoneType> getAllValues() {
        return List.of(PhoneType.values());
    }
}
