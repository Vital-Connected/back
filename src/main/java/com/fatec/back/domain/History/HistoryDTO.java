package com.fatec.back.domain.History;

import java.time.LocalDateTime;

public record HistoryDTO (Boolean taked, LocalDateTime takedAt, Long relationMP,Long userId){
    
}
