package gr.nikolis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class TestParkApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllCustomers() throws Exception {
        mockMvc.perform(get("/customers/getAllCustomers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getPhone() throws Exception {
        mockMvc.perform(get("/customers/phoneTest/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getPhoneNotFound() throws Exception {
        mockMvc.perform(get("/customers/phoneTest/12")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addNewCustomer() throws Exception {
        String customer = "  {\n" +
                "        \"firstName\": \"Nikos\",\n" +
                "        \"lastName\": \"Mpelogiannis\",\n" +
                "        \"age\": 38,\n" +
                "        \"phones\": \n" +
                "        [\n" +
                "            {\n" +
                "                \"phoneNumber\": 210617485\n" +
                "            },\n" +
                "            {\n" +
                "                \"phoneNumber\": 694563258\n" +
                "            }\n" +
                "        ],\n" +
                "        \"cars\": \n" +
                "        [\n" +
                "            {\n" +
                "                \"car\":\n" +
                "                    {\n" +
                "                        \"brandName\": \"Mersedes\",\n" +
                "                        \"color\": \"White\",\n" +
                "                        \"plate\": \"EEA8525\",\n" +
                "                        \"licenceNumber\": 3254342\n" +
                "                    },\n" +
                "                     \"driverLicenceNumber\": 48834223423\n" +
                "            },\n" +
                "\n" +
                "            {\n" +
                "                \"car\":\n" +
                "                    {\n" +
                "                        \"brandName\": \"BMW\",\n" +
                "                        \"color\": \"Black\",\n" +
                "                        \"plate\": \"JJI8987\",\n" +
                "                        \"licenceNumber\": 3432423\n" +
                "                    },\n" +
                "                    \"driverLicenceNumber\": 48834223423\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n";
        mockMvc.perform(post("/customers/updateCustomer/45")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
