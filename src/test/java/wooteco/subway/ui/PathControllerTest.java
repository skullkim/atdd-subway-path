package wooteco.subway.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import wooteco.subway.service.PathService;

@DisplayName("PathController 는 ")
@WebMvcTest(PathController.class)
class PathControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PathService pathService;

    @DisplayName("지하철 경로를 조회할 때")
    @Nested
    class ShowPathTest {

        @DisplayName("입력한 데이터가 모두 유효하면 지하철 경로를 조회한다.")
        @Test
        void showPath() throws Exception {
            mockMvc.perform(get("/paths?source=1&target=3&age=15")).andExpect(status().isOk());
        }

        @DisplayName("입력한 데이터 중 하나라도 유효하지 않으면 지하철 경로를 조회하지 못한다.")
        @ParameterizedTest(name = "{index} {displayName} source={0} target={1} age={2}")
        @CsvSource(value = {"0, 1, 1", "1, 0, 1,", "1, 1, 0"})
        void failedToShowPath(final Long source, final Long target, final int age) throws Exception {
            final String requestUrl = "/paths?source=" + source + "&target=" + target + "&age=" + age;
            mockMvc.perform(get(requestUrl)).andExpect(status().isBadRequest());
        }
    }
}
