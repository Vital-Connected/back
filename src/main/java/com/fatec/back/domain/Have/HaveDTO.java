package com.fatec.back.domain.Have;

import java.time.LocalDate;

public record HaveDTO (LocalDate startDate, LocalDate endDate, Long patient, Long caregiver, Long userId){
    
}
