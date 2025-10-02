import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({RegistrationTest.class,
        LoginUserTest.class,
        GetUserData.class,
        ChangeUserData.class,
        CreateNews.class,
        SearchNews.class,
        SearchNewsId.class,
        ChangeNews.class,
        DeleteNews.class,
        CreateComment.class,
        ChangeComment.class,
        DeleteComment.class
})
class AllTests {
}