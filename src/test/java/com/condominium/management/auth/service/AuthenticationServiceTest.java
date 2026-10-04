
package com.condominium.management.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.condominium.management.auth.dto.LoginRequestDTO;
import com.condominium.management.auth.dto.LoginResponseDTO;
import com.condominium.management.auth.dto.RegisterRequestDTO;
import com.condominium.management.security.JwtService;
import com.condominium.management.user.entity.User;
import com.condominium.management.user.repository.UserRepository;
import com.condominium.management.role.entity.Role;
import com.condominium.management.role.repository.RoleRepository;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager manager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldLoginSuccessfully() {

        // Arrange
        LoginRequestDTO dto =
                new LoginRequestDTO(
                        "admin@email.com",
                        "123456"
                );

        User user = org.mockito.Mockito.mock(User.class);

        Authentication authentication =
                org.mockito.Mockito.mock(Authentication.class);

        String expectedToken = "jwt-token-example";

        when(manager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(user);

        when(jwtService.generateToken(user))
                .thenReturn(expectedToken);

        // Act
        LoginResponseDTO response =
                authenticationService.login(dto);

        // Assert
        assertThat(response)
                .isNotNull();

        assertThat(response.token())
                .isEqualTo(expectedToken);

        verify(manager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );

        verify(jwtService).generateToken(user);
    }
    
    @Test
    void shouldNotRegisterUserWhenEmailAlreadyExists() {

        // Arrange
        RegisterRequestDTO dto =
                new RegisterRequestDTO(
                        "João Silva",
                        "admin@email.com",
                        "123456"
                );

        User existingUser = org.mockito.Mockito.mock(User.class);

        when(userRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.of(existingUser));

        // Act & Assert
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> authenticationService.register(dto)
        )
                .isInstanceOf(RuntimeException.class)
                .hasMessage("E-mail já cadastrado");

        // Verify
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void shouldEncodePasswordBeforeRegisteringUser() {

        // Arrange
        RegisterRequestDTO dto =
                new RegisterRequestDTO(
                        "João Silva",
                        "joao@email.com",
                        "123456"
                );

        Role role = org.mockito.Mockito.mock(Role.class);

        String encodedPassword = "encoded-password";

        when(userRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.empty());

        when(roleRepository.findByName("ADMIN"))
                .thenReturn(java.util.Optional.of(role));

        when(passwordEncoder.encode(dto.password()))
                .thenReturn(encodedPassword);

        // Act
        authenticationService.register(dto);

        // Verify
        verify(passwordEncoder).encode(dto.password());

        verify(userRepository).save(
                org.mockito.ArgumentMatchers.argThat(
                        user ->
                                user.getPassword().equals(encodedPassword)
                )
        );
    }
    
    @Test
    void shouldNotRegisterUserWhenAdminRoleDoesNotExist() {

        // Arrange
        RegisterRequestDTO dto =
                new RegisterRequestDTO(
                        "João Silva",
                        "joao@email.com",
                        "123456"
                );

        when(userRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.empty());

        when(roleRepository.findByName("ADMIN"))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> authenticationService.register(dto)
        )
                .isInstanceOf(java.util.NoSuchElementException.class);

        // Verify
        verify(userRepository, never()).save(any(User.class));

        verify(passwordEncoder, never())
                .encode(any(String.class));
    }
    
    @Test
    void shouldRegisterUserSuccessfully() {

        // Arrange
        RegisterRequestDTO dto =
                new RegisterRequestDTO(
                        "João Silva",
                        "joao@email.com",
                        "123456"
                );

        Role role = org.mockito.Mockito.mock(Role.class);

        String encodedPassword = "encoded-password";

        when(userRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.empty());

        when(roleRepository.findByName("ADMIN"))
                .thenReturn(java.util.Optional.of(role));

        when(passwordEncoder.encode(dto.password()))
                .thenReturn(encodedPassword);

        // Act
        authenticationService.register(dto);

        // Verify
        verify(userRepository).save(
                org.mockito.ArgumentMatchers.argThat(
                        user ->
                                user.getName().equals(dto.name())
                                && user.getEmail().equals(dto.email())
                                && user.getPassword().equals(encodedPassword)
                                && user.getActive()
                                && user.getRole().equals(role)
                )
        );
    }
}
