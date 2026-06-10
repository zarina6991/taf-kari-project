package kari.api.recovery;

import io.restassured.response.Response;
import kari.api.base.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecoveryApiNegativeTest extends BaseApiTest {

    private RecoveryApiSteps recoveryApiSteps;

    @BeforeEach
    public void setUpRecoveryApi() {
        recoveryApiSteps = new RecoveryApiSteps(requestSpec);
    }

    @DisplayName("Восстановление без авторизации должно возвращать 401")
    @Test
    public void recoveryWithoutAuthenticationShouldReturn401() {
        logger.info("Проверяем восстановление без авторизации");

        Response response = recoveryApiSteps.recoveryWithoutAuthentication();

        recoveryApiSteps.assertMissingAuthenticationResponse(response);
    }
}
