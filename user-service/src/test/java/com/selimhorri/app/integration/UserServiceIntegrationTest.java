package com.selimhorri.app.integration;

import com.selimhorri.app.dto.UserDto;
import com.selimhorri.app.repository.UserRepository;
import com.selimhorri.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindUser() {
        var credential = com.selimhorri.app.dto.CredentialDto.builder()
            .username("integrationuser")
            .password("pass")
            .roleBasedAuthority(null)
            .isEnabled(true)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .build();

        var userDto = com.selimhorri.app.dto.UserDto.builder()
            .firstName("Integration")
            .credentialDto(credential)
            .build();

        var saved = userService.save(userDto);

        assertNotNull(saved.getUserId());
        assertEquals("Integration", saved.getFirstName());

        var found = userService.findById(saved.getUserId());
        assertEquals("Integration", found.getFirstName());
    }

    @Test
    void updateUser_shouldModifyUser() {
        var credential = com.selimhorri.app.dto.CredentialDto.builder()
            .username("integrationuser2")
            .password("pass")
            .roleBasedAuthority(null)
            .isEnabled(true)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .build();

        var userDto = com.selimhorri.app.dto.UserDto.builder()
            .firstName("Original")
            .credentialDto(credential)
            .build();

        var saved = userService.save(userDto);

        saved.setFirstName("Updated");
        var updated = userService.update(saved);

        assertEquals("Updated", updated.getFirstName());
    }

    @Test
    void deleteUser_shouldRemoveUser() {
        var credential = com.selimhorri.app.dto.CredentialDto.builder()
            .username("integrationuser3")
            .password("pass")
            .roleBasedAuthority(null)
            .isEnabled(true)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .build();

        var userDto = com.selimhorri.app.dto.UserDto.builder()
            .firstName("ToDelete")
            .credentialDto(credential)
            .build();

        var saved = userService.save(userDto);
        Integer id = saved.getUserId();

        userService.deleteById(id);

        assertThrows(Exception.class, () -> userService.findById(id));
    }
}