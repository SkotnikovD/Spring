package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.user.GetUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.FilesRepository;
import com.skovdev.springlearn.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Naming convention in use for all unit tests: UnitOfWork_StateUnderTest_ExpectedBehavior
 */
class UserServiceImplTest {

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoderMock;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    FilesRepository filesRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        when(bCryptPasswordEncoderMock.encode(any())).then(invocation -> invocation.getArgument(0));
    }

    @Test
    void RegisterNewUser_CorrectUser_ReturnUserWithAllFieldsWithoutPassword() {
        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, bCryptPasswordEncoderMock, filesRepository, currentPrincipalInfoService);
        SignUpUserDto newUserDto = new SignUpUserDto()
                .setLogin("fu@bar.com")
                .setPassword("qwerty")
                .setName("Shalayn")
                .setBirthdayDate(LocalDate.of(2000, 10, 10));
        User newUserFromStorage = new User()
                .setLogin("fu@bar.com")
                .setPassword("egvnerwoinrevn") //some encrypted value
                .setFirstName("Shalayn")
                .setBirthdayDate(LocalDate.of(2000, 10, 10))
                .setRoles(Role.ROLE_USER);
        when(userRepositoryMock.getUser("fu@bar.com")).thenReturn(Optional.of(newUserFromStorage));

        GetUserDto createdUserDto = userService.registerNewUser(newUserDto);

        assertThat(createdUserDto.getLogin()).isEqualTo("fu@bar.com");
        assertThat(createdUserDto.getName()).isEqualTo("Shalayn");
        assertThat(createdUserDto.getBirthdayDate()).isEqualTo(LocalDate.of(2000, 10, 10));
        assertThat(createdUserDto.getRoles().size()==1);
        assertThat(createdUserDto.getRoles().contains(Role.ROLE_USER));
    }

    @Test
    void RegisterNewUser_CorrectUser_PasswordEncryptedAndPassedForSaving() {
        BCryptPasswordEncoder realBCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, realBCryptPasswordEncoder, filesRepository, currentPrincipalInfoService);
        SignUpUserDto newUserDto = new SignUpUserDto()
                .setLogin("fu@bar.com")
                .setPassword("qwerty")
                .setName("Shalayn")
                .setBirthdayDate(LocalDate.of(2000, 10, 10));
        User newUserFromStorage = new User()
                .setLogin("fu@bar.com")
                .setPassword("someencryptedpass") //encrypted pass
                .setFirstName("Shalayn")
                .setBirthdayDate(LocalDate.of(2000, 10, 10));
        when(userRepositoryMock.getUser("fu@bar.com")).thenReturn(Optional.of(newUserFromStorage));

        userService.registerNewUser(newUserDto);

        ArgumentCaptor<User> requestCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).createUser(requestCaptor.capture());
        assertThat(requestCaptor.getAllValues()).hasSize(1);
        User newUserToPersist = requestCaptor.getValue();

        assertThat(realBCryptPasswordEncoder.matches("qwerty", newUserToPersist.getPassword())).isTrue();
    }
}