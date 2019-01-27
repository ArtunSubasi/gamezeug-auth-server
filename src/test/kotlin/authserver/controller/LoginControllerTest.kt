package authserver.controller

import com.gamezeug.authserver.controller.LoginController
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [LoginController::class])
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun login() {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk)
    }

}
