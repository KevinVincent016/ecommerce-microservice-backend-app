package com.selimhorri.app.unit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.selimhorri.app.dto.UserDto;
import com.selimhorri.app.helper.UserMappingHelper;
import com.selimhorri.app.repository.UserRepository;
import com.selimhorri.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnUserList() {
        var credential = com.selimhorri.app.dto.CredentialDto.builder()
            .credentialId(1)
            .username("testuser")
            .password("pass")
            .roleBasedAuthority(null)
            .isEnabled(true)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .build();

        var userDto = UserDto.builder()
            .userId(1)
            .firstName("Test")
            .credentialDto(credential)
            .build();

        var user = UserMappingHelper.map(userDto);
        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getFirstName());
    }

    @Test
    void findById_shouldReturnUser() {
        var credential = com.selimhorri.app.dto.CredentialDto.builder()
            .credentialId(1)
            .username("testuser")
            .password("pass")
            .roleBasedAuthority(null)
            .isEnabled(true)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .build();

        var userDto = UserDto.builder()
            .userId(1)
            .firstName("Test")
            .credentialDto(credential)
            .build();

        var user = UserMappingHelper.map(userDto);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        var result = userService.findById(1);

        assertEquals("Test", result.getFirstName());
    }
}