package itroadlabs.rnd.validation.ports.adapters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("aop-way")
public class RESTfulApiShoppingCartHandlerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void invalidInputBringsHttpError() throws Exception {
        MockHttpServletRequestBuilder requestBuilder =
                post("/shopping-cart/checkout")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"items\":[\n" +
                                "    {\n" +
                                "      \"productCatalogCode\": \"F000222001\",\n" +
                                "      \"quantity\": 10\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"productCatalogCode\": \"F000222003\",\n" +
                                "      \"quantity\": 0\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}");

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(422))
                .andReturn();

        JsonNode node = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JsonNode.class);
        ArrayNode errorsNode = (ArrayNode) node.get("errors");

        assertEquals(2, errorsNode.size());
        assertTrue(containsFieldError(errorsNode, "items[1].quantity"));
        assertTrue(containsFieldError(errorsNode, "items[0].productCatalogCode"));
    }

    private boolean containsFieldError(ArrayNode errorsNode, String fieldPath) {
        for (JsonNode node : errorsNode) {
            if (fieldPath.equals(node.get("field").asText())) {
                return true;
            }
        }
        return false;
    }
}
