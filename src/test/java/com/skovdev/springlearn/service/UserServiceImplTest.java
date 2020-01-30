package com.skovdev.springlearn.service;

import com.google.common.collect.Sets;
import com.skovdev.springlearn.dto.RoleDto;
import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
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

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        when(bCryptPasswordEncoderMock.encode(any())).then(invocation -> invocation.getArgument(0));
    }

    @Test
    void RegisterNewUser_CorrectUser_ReturnUserWithAllFieldsWithoutPassword() {
        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, bCryptPasswordEncoderMock);
        UserDto newUserDto = new UserDto()
                .setLogin("fu@bar.com")
                .setPassword("qwerty")
                .setName("Shalayn")
                .setBirthdayDate(new Date(12345678));
        User newUserFromStorage = new User()
                .setLogin("fu@bar.com")
                .setPassword("egvnerwoinrevn") //some encrypted value
                .setFirstName("Shalayn")
                .setBirthdayDate(new Date(12345678));
        when(userRepositoryMock.getUser("fu@bar.com")).thenReturn(Optional.of(newUserFromStorage));

        UserDto createdUserDto = userService.registerNewUser(newUserDto);

        assertThat(createdUserDto.getPassword()).isNull();     //password doesn't exposed to client
        assertThat(createdUserDto.getLogin()).isEqualTo("fu@bar.com");
        assertThat(createdUserDto.getName()).isEqualTo("Shalayn");
        assertThat(createdUserDto.getBirthdayDate()).isEqualTo(new Date(12345678));
        assertThat(createdUserDto.getRoles()).isNull(); //roles didn't map back to DTO, because now there is no use cases, when user should see his role. They're used only by system.
    }

    @Test
    void RegisterNewUser_CorrectUser_PasswordEncryptedAndPassedForSaving() {
        BCryptPasswordEncoder realBCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, realBCryptPasswordEncoder);
        UserDto newUserDto = new UserDto()
                .setLogin("fu@bar.com")
                .setPassword("qwerty")
                .setName("Shalayn")
                .setBirthdayDate(new Date(12345678));
        User newUserFromStorage = new User()
                .setLogin("fu@bar.com")
                .setPassword("iornoirvnwerwervv") //encrypted pass
                .setFirstName("Shalayn")
                .setBirthdayDate(new Date(12345678));
        when(userRepositoryMock.getUser("fu@bar.com")).thenReturn(Optional.of(newUserFromStorage));

        userService.registerNewUser(newUserDto);

        ArgumentCaptor<User> requestCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).createUser(requestCaptor.capture());
        assertThat(requestCaptor.getAllValues()).hasSize(1);
        User newUserToPersist = requestCaptor.getValue();

        assertThat(realBCryptPasswordEncoder.matches("qwerty", newUserToPersist.getPassword())).isTrue();
    }


    @Test
    void RegisterNewUser_CorrectUser_IgnoreRoleSetByClient() {
        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, bCryptPasswordEncoderMock);
        UserDto newUserDto = new UserDto()
                .setLogin("fu@bar.com")
                .setPassword("qwerty")
                .setName("Shalayn")
                .setBirthdayDate(new Date(12345678))
                .setRoles(Sets.newHashSet(new RoleDto().setRole(Role.ROLE_ADMIN)));  //client set ADMIN role, but he won't get it
        User newUserFromStorage = new User()
                .setLogin("fu@bar.com")
                .setPassword("iornoirvnwerwervv") //encrypted pass
                .setFirstName("Shalayn")
                .setBirthdayDate(new Date(12345678));
        when(userRepositoryMock.getUser("fu@bar.com")).thenReturn(Optional.of(newUserFromStorage));

        userService.registerNewUser(newUserDto);

        ArgumentCaptor<User> requestCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).createUser(requestCaptor.capture());
        assertThat(requestCaptor.getAllValues()).hasSize(1);
        User newUserToPersist = requestCaptor.getValue();

        assertThat(newUserToPersist.getRoles()).containsExactly(Role.of(Role.ROLE_USER));
    }

    @Test
    void GetUser_PasswordIncluded_ReturnUserWithCompletedPasswordField() {
        User user = new User().setLogin("fu@bar.com").setPassword("qwerty").setFirstName("Shalayn");
        when(userRepositoryMock.getUser("fu@bar.com")).thenReturn(Optional.of(user));

        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, bCryptPasswordEncoderMock);
        Optional<UserDto> newUser = userService.getUser("fu@bar.com", false);

        assertThat(newUser.get().getPassword()).isEqualTo("qwerty");
    }

    @Test
    void GetUser_PasswordExcluded_ReturnUserWithoutPasswordField() {
        User user = new User().setLogin("fu@bar.com").setPassword("qwerty").setFirstName("Shalayn");
        when(userRepositoryMock.getUser("fu@bar.com")).thenReturn(Optional.of(user));

        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, bCryptPasswordEncoderMock);
        Optional<UserDto> newUser = userService.getUser("fu@bar.com", true);
        assertThat(newUser.get().getPassword()).isNull();
    }
}