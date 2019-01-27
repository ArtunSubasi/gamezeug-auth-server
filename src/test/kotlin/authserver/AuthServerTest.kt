package authserver

import com.gamezeug.authserver.AuthServerApplication
import cucumber.api.CucumberOptions
import cucumber.api.java8.En
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest

@RunWith(Cucumber::class)
@CucumberOptions(plugin = ["pretty", "junit:build/reports/junit/junit.xml", "json:build/reports/cucumber/json.cucumber"])
class AuthServerTest

@SpringBootTest(classes = [AuthServerApplication::class])
class StepDefs: En {

    init {
        Given("I start the spring boot application") {
        }

        Then("The application context should load") {
        }
    }

}
