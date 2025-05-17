package com.fatec.back.domain.Have;

import java.time.LocalDate;

import com.fatec.back.domain.Caregiver.Caregiver;
import com.fatec.back.domain.Patient.Patient;

public record HaveDTO (LocalDate startDate, LocalDate endDate, Patient patient, Caregiver caregiver){
    
}
