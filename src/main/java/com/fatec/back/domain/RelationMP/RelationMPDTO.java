package com.fatec.back.domain.RelationMP;

import java.time.LocalDate;

import com.fatec.back.domain.Medication.Medication;
import com.fatec.back.domain.Patient.Patient;
import com.fatec.back.domain.RelationMP.RelationMP.FrequencyUnit;

public record RelationMPDTO (Integer dosage,Integer frequencyValue,FrequencyUnit frequencyUnit,Medication medication,Patient patient,LocalDate startDate,LocalDate endDate){
    
}
