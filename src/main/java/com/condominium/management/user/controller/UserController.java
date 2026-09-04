package com.condominium.management.user.controller;

import com.condominium.management.user.dto.UserRequestDTO;
import com.condominium.management.user.dto.UserResponseDTO;
import com.condominium.management.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(
            @RequestBody @Valid UserRequestDTO dto
    ) {
        return service.create(dto);
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(
            @PathVariable Long id
    ) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDTO dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        service.delete(id);
    }
}