package com.skovdev.springlearn.repository.jpa;

import com.skovdev.springlearn.config.OpenTableDBConfig;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.UserRepository;
import com.skovdev.springlearn.repository.jpa.factory.UserFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//        classes = OpenTableDBConfig.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Because we have well-configured in-memory OpenTable database, don't want to override it
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
//@SpringBootTest
@Import({OpenTableDBConfig.class})
@ComponentScan(basePackages = "com.skovdev.springlearn.repository.jpa")
@Slf4j
class ITUserRepositoryJpaImpl {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepositoryJpaImpl;

    @Autowired
    UserSpringDataRepository userSpringDataRepository;

    @Test
    public void createUser_allFieldsTwoRoles_createdSuccessfully() throws InterruptedException {
        User user = UserFactory.allFieldsAdminAndUserRoles();
        userRepositoryJpaImpl.createUser(user);
        em.flush();
        User resultUser = userSpringDataRepository.findById(user.getUserId()).get();
        assertThat(user!=resultUser);
        User resultUser2 = userRepositoryJpaImpl.getUser(user.getLogin()).get();
        assertThat(user!=resultUser2);
    }

    public void createUser_allFieldsOneRoles_createdSuccessfully() {

    }

//    @Test
    //созд пошльз с повторяющимся логином
    public void createUser_allFieldsExistedLogin_throwsObjectAlreadyExistsException() {
//        assertThrows
    }



    //Создание пользвоателя с несуществующей ролью
//    @Test
    public void createUser_NonExistingRole_throws() {
    }

    //Update пользователя не затрагивает его роли (Добавление пользователю несуществующей роли)
    //Update пользователя без Id
}

// @BeforeEach  @BeforeAll @Nested @TestInstance(Lifecycle.PER_CLASS) 