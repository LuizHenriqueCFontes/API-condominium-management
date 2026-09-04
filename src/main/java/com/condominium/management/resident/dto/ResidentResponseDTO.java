package com.condominium.management.resident.dto;

import java.time.LocalDate;

public record ResidentResponseDTO(

        Long id,

        Long userId,

        String name,

        String email,

        String cpf,

        String phone,

        LocalDate birthDate,

        Long unitId,

        String unitNumber

) {
}
