package com.fatec.back.domain.RelationMP;

import java.time.LocalDate;

import com.fatec.back.domain.RelationMP.RelationMP.FrequencyUnit;

public record RelationMPDTO (Integer dosage,Integer frequencyValue,FrequencyUnit frequencyUnit,Long medication,Long patient,LocalDate startDate,LocalDate endDate, Long userId){
    
}
