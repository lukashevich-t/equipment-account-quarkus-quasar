// package by.gto.equipment.test;
//
// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
//
// import by.gto.equipment.test.helpers.LogHelpers;
// import by.gto.equipment.test.lifecycle.MariaDBTestResource;
// import io.quarkus.test.common.QuarkusTestResource;
// import io.quarkus.test.junit.QuarkusTest;
// import java.util.Date;
// import java.util.Map;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
// import org.eclipse.microprofile.config.Config;
// import org.eclipse.microprofile.config.ConfigProvider;
// import org.eclipse.microprofile.config.inject.ConfigProperty;
// import org.junit.jupiter.api.Test;
//
// @QuarkusTest
// @QuarkusTestResource(MariaDBTestResource.class)
// public class ReferenceResourceTest {
//     @Test
//     void testStatesEndpoint() throws Throwable {
//
//         Throwable resultException = null;
//
//         final io.restassured.response.Response response = given().when().get("/r/eqStates");
//         response.then().statusCode(Response.Status.OK.getStatusCode())
//             .contentType(MediaType.APPLICATION_JSON);
//
//
//         try {
//             final Map result = given()
//                 .when().get("/r/eqStates")
//                 .then()
//                 .statusCode(Response.Status.OK.getStatusCode())
//                 .contentType(MediaType.APPLICATION_JSON)
//                 // .body(is("Hello RESTEasy"))
//                 //                .body(matchesJson(""))
//                 .extract().as(Map.class);
//             for (Object key : result.keySet()) {
//                 sb.append(key).append(" => ").append(result.get(key)).append("\n");
//             }
//         } catch (Throwable ex) {
//             sb.append(ex);
//             resultException = ex;
//         }
//
//         LogHelpers.writeCharSequenceToFile(sb, "build/answer.txt");
//         if (resultException != null) throw resultException;
//     }
// }
