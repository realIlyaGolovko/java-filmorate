package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.repository.helper.UserHelper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.yandex.practicum.filmorate.testdata.UserDefaultGenerator.generateDefaultUser;

@JdbcTest
@Import({JdbcUserRepository.class, UserRowMapper.class, UserHelper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JdbcUserRepositoryTest {
    private final JdbcUserRepository sut;
    private final UserHelper userHelper;

    @Test
    void shouldSaveNewUser() {
        final User expected = generateDefaultUser();

        final User actual = sut.saveUser(expected);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldUpdatedSavedUser() {
        final long savedUserId = userHelper.createRandomUser().getId();
        final User expected = generateDefaultUser();
        expected.setId(savedUserId);

        final User actual = sut.updateUser(expected);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnUserById() {
        final User expected = userHelper.createRandomUser();

        final Optional<User> actual = sut.getUser(expected.getId());

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnAllUsers() {
        List<User> expected = List.of(userHelper.createRandomUser(), userHelper.createRandomUser());

        List<User> actual = sut.getUsers();

        assertThat(actual)
                .asList()
                .hasSize(expected.size())
                .containsExactlyElementsOf(expected);
    }
}
