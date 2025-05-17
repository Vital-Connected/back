package com.fatec.back.domain.Patient;

import java.time.LocalDate;

public record PatientDTO (LocalDate birthday, String patientCondition, Long userId){
    
}
