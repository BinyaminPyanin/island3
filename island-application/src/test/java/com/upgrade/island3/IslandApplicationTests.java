package com.upgrade.island3;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(classes = {IslandApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IslandApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void whenIndexEPReachedThenStatusIsOk() {
        log.info("Running whenIndexEPReachedThenStatusIsOk");
        mockMvc.perform(get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
