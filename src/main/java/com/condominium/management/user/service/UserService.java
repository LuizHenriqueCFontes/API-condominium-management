package com.condominium.management.user.service;

import com.condominium.management.role.entity.Role;
import com.condominium.management.role.repository.RoleRepository;
import com.condominium.management.user.dto.UserRequestDTO;
import com.condominium.management.user.dto.UserResponseDTO;
import com.condominium.management.user.entity.User;
import com.condominium.management.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException(
                    "E-mail já cadastrado."
            );
        }

        Role role = roleRepository.findById(dto.roleId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Role não encontrada."
                        )
                );

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(
                        passwordEncoder.encode(
                                dto.password()
                        )
                )
                .active(dto.active())
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();

        return toResponse(
                repository.save(user)
        );
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado."
                        )
                );

        return toResponse(user);
    }

    @Transactional
    public UserResponseDTO update(
            Long id,
            UserRequestDTO dto
    ) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado."
                        )
                );

        Role role = roleRepository.findById(dto.roleId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Role não encontrada."
                        )
                );

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setActive(dto.active());
        user.setRole(role);

        if (dto.password() != null &&
                !dto.password().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            dto.password()
                    )
            );
        }

        return toResponse(
                repository.save(user)
        );
    }

    @Transactional
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException(
                    "Usuário não encontrado."
            );
        }

        repository.deleteById(id);
    }

    private UserResponseDTO toResponse(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getActive(),
                user.getRole().getId(),
                user.getRole().getName()
        );
    }
}