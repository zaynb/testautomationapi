package apiTests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class ResfulContactApisTests {
    String contactID;

    // create contact
    @Test(priority = 1)
    public void testCreateValidContact() {
        String endpoint = "http://3.13.86.142:3000/contacts";
        String body = """
                {
                     "firstName": "Zaynab",
                       "lastName": "Ragab",
                        "email": "zrgab@thinkingtester.com",
                            "location": {
                                "city": "Boston, MA",
                                "country": "USA"
                            },
                            "employer": {
                            	"jobTitle": "Software Tester",
                            	"company": "Microsoft"
                            }
                }""";
        var responseToValidate = given().body(body).header("Content-Type", "application/json")
                .log().all().when().post(endpoint).then();

        responseToValidate.body("firstName", equalTo("Zaynab"));
        responseToValidate.statusCode(200);

        Response response = responseToValidate.extract().response();
        JsonPath jsonPath = response.jsonPath();
        contactID = jsonPath.getString("_id");

        responseToValidate.log().all();
    }
    // read booking
    @Test(priority = 2)
    public void testGetContact ()
    {
        String endpoint = "http://3.13.86.142:3000/contacts/" +contactID;
        var responseToValidate = given()
                .header("Content-Type", "application/json")
                .log().all().when().get(endpoint).then();

        responseToValidate.body("firstName", equalTo("Zaynab"));
        responseToValidate.statusCode(200);

    }
    @Test(priority = 3)
    public void testEditContact() {
        String endpoint = "http://3.13.86.142:3000/contacts/" +contactID;

        String body = """
                {
                     "firstName": "Zayna",
                       "lastName": "Ragab",
                        "email": "zrgab@thinkingtester.com",
                            "location": {
                                "city": "Boston, MA",
                                "country": "USA"
                            },
                            "employer": {
                            	"jobTitle": "Software Tester",
                            	"company": "Microsoft"
                            }
                }""";

        var responseToValidate = given().body(body).header("Content-Type", "application/json")
                .header("Accept" , "*/*")
                .log().all().when().put(endpoint).then();


        //   responseToValidate.body("firstName" , equalTo("Zayna"));
        responseToValidate.statusCode(204);




    }
    // delete contact
    @Test(priority = 4)
    public void testDeleteContact ()
    {
        String endpoint = "http://3.13.86.142:3000/contacts/" +contactID;
        var responseToValidate = given()
                .header("Content-Type", "application/json")
                .log().all().when().delete(endpoint).then();

        responseToValidate.statusCode(204);
       /* Response response = responseToValidate.extract().response();
        Assert.assertEquals(response.asString() , "Created");*/
    }


}
