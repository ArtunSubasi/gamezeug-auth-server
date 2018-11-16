package authserver

import com.gamezeug.authserver.AuthServerApplication
import cucumber.api.CucumberOptions
import cucumber.api.java8.En
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest

@RunWith(Cucumber::class)
@CucumberOptions(plugin = arrayOf("pretty", "junit:build/cucumber/junit.xml"))
class AuthServerTest

@SpringBootTest(classes = arrayOf(AuthServerApplication::class))
class StepDefs: En {

    init {
        Given("I start the spring boot application") {
        }

        Then("The application context should load") {
        }
    }

}
