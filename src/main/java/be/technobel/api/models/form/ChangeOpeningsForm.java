package be.technobel.api.models.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeOpeningsForm {

    private LocalTime opensAt;
    private LocalTime closesAt;

}
