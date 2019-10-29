package authserver.controller

import com.gamezeug.authserver.AuthServerApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AuthServerApplication::class])
@AutoConfigureMockMvc
class JwkSetRestControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getJwksJson() {
        mockMvc.perform(get("/.well-known/jwks.json"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("keys").isArray)
                .andExpect(jsonPath("keys[0]").isMap)
                .andExpect(jsonPath("keys[0].kid").value("gamezeug-key-id"))
    }

}
