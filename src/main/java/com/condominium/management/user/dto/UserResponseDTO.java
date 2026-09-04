package com.condominium.management.user.dto;

public record UserResponseDTO(

        Long id,

        String name,

        String email,

        Boolean active,

        Long roleId,

        String roleName

) {
}