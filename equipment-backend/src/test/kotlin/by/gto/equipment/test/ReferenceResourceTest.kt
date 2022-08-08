package by.gto.equipment.test

import by.gto.equipment.test.helpers.LogHelpers
import by.gto.equipment.test.lifecycle.MariaDBTestResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse
import org.eclipse.microprofile.config.ConfigProvider
import org.junit.jupiter.api.Test
import java.util.Date
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import  io.restassured.RestAssured.*
import io.restassured.matcher.RestAssuredMatchers.*
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions
import kotlin.collections.Map

@QuarkusTest
@QuarkusTestResource(MariaDBTestResource::class)
class ReferenceResourceTest {

    @Test
    fun logSomeInfo() {
        val sb = StringBuilder(Date().toString()).append("\n\n")
        val config = ConfigProvider.getConfig()
        for (propertyName in config.propertyNames) {
            sb.append(propertyName).append(" => ").append(config.getConfigValue(propertyName).rawValue).append("\n")
        }
        LogHelpers.writeCharSequenceToFile(sb.append("\n\n"), "build/quarkus-test-properties.log")
    }

    @Test
    @Throws(Throwable::class)
    fun testStatesEndpoint() {
        val response = RestAssured.given().`when`().get("/r/eqStates")
        val vResponse: ValidatableResponse = response.then()
        vResponse.statusCode(Response.Status.OK.statusCode)
                .contentType(MediaType.APPLICATION_JSON)
//        val body: Map<String, Any?> = vResponse.extract().path("$")
//        vResponse.body("date", `is`(""))
    }
}
