package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.repository.helper.UserHelper;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({JdbcFriendRepository.class, UserHelper.class, UserRowMapper.class, JdbcUserRepository.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JdbcFriendRepositoryTest {
    private final JdbcFriendRepository sut;
    private final UserHelper userHelper;
    private User user;
    private User friend;

    @BeforeEach
    void setUp() {
        user = userHelper.createRandomUser();
        friend = userHelper.createRandomUser();

    }

    @Test
    void shouldAddFriend() {
        sut.saveFriend(user, friend);
        List<User> actual = sut.getFriends(user.getId());

        assertThat(actual)
                .asList()
                .hasSize(1);
        assertThat(actual.get(0))
                .isEqualTo(friend);
    }

    @Test
    void shouldDeleteFriend() {
        sut.saveFriend(user, friend);

        sut.deleteFriend(user, friend);
        List<User> actual = sut.getFriends(user.getId());

        assertThat(actual)
                .asList()
                .isEmpty();
    }

    @Test
    void shouldGetCommonFriend() {
        final User commonFriend = userHelper.createRandomUser();
        sut.saveFriend(user, commonFriend);
        sut.saveFriend(friend, commonFriend);

        List<User> actual = sut.getCommonFriends(user, friend);

        assertThat(actual)
                .asList()
                .hasSize(1);
        assertThat(actual.get(0))
                .isEqualTo(commonFriend);
    }
}
