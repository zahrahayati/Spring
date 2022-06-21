package dotin.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import dotin.example.demo.controller.CardPrintRequestController;
import dotin.example.demo.exception.ResourceNotFoundException;
import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.model.CardPrintRequestEmbeddedId;
import dotin.example.demo.repository.CardPrintRequestJDBCTemplate;
import dotin.example.demo.repository.CardPrintRequestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardPrintRequestController.class)
public class CardPrintRequestRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    CardPrintRequestRepository cardPrintRequestRepository;

    @MockBean
    CardPrintRequestJDBCTemplate cardPrintRequestJDBCTemplate;

    CardPrintRequest cardPrintRequest = new CardPrintRequest(new CardPrintRequestEmbeddedId("60", "10.20.152.15"), "6063731098404032", "74100");
    CardPrintRequest cardPrintRequest1 = new CardPrintRequest(new CardPrintRequestEmbeddedId("6071", "10.20.152.18"), "6063731098404055", "75120");
    CardPrintRequest cardPrintRequest2 = new CardPrintRequest(new CardPrintRequestEmbeddedId("6070", "10.20.152.18"), "6063731098404055", "7450");


    public CardPrintRequestRestControllerTest() throws ParseException {
    }
    @Test
    public void getAllRecords_success() throws Exception {
        List<CardPrintRequest> records = new ArrayList<>(Arrays.asList(cardPrintRequest,cardPrintRequest1,cardPrintRequest2));

        Mockito.when(cardPrintRequestRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].cardPAN", is("6063731098404055")));
    }

    @Test
    public void getRequestById_success() throws Exception {
        Mockito.when(cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress(cardPrintRequest.getId().getBranchCode(), cardPrintRequest.getId().getIpAddress())).thenReturn(cardPrintRequest);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/requests/60/10.20.152.15/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.personnelCode", is("74100")));
    }@Test
    public void createRequest_success() throws Exception {
        CardPrintRequest request = new CardPrintRequest(new CardPrintRequestEmbeddedId("36390", "10.20.152.58"), "6063731098404044", "99999");

        Mockito.when(cardPrintRequestRepository.save(Mockito.any(CardPrintRequest.class))).thenReturn(request);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/request")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.personnelCode", is("99999")));
    }
    @Test
    public void updateCardPrintRequest_success() throws Exception {
        CardPrintRequest request = new CardPrintRequest(new CardPrintRequestEmbeddedId("6071", "10.20.152.18"), "6063731098404044", "99910");

        Mockito.when(cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress(cardPrintRequest1.getId().getBranchCode(), cardPrintRequest1.getId().getIpAddress())).thenReturn(cardPrintRequest1);
        Mockito.when(cardPrintRequestRepository.save(Mockito.any(CardPrintRequest.class))).thenReturn(request);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.cardPAN", is("6063731098404044")))
                .andExpect(jsonPath("$.personnelCode", is("99910")));
    }
    @Test
    public void updateCardPrintRequest_requestNotFound() throws Exception {
        CardPrintRequest request = new CardPrintRequest(new CardPrintRequestEmbeddedId("36390", "10.20.152.58"), "6063731098404044", "99999");

        Mockito.when(cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress(request.getId().getBranchCode(),request.getId().getIpAddress())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request));

        mockMvc.perform(mockRequest)
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result ->
                        assertEquals("cardPrintRequest not found for this branchCode : 36390", result.getResolvedException().getMessage()));
    }
    @Test
    public void deleteRequestById_success() throws Exception {
        Mockito.when(cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress(cardPrintRequest1.getId().getBranchCode(),cardPrintRequest1.getId().getIpAddress())).thenReturn(cardPrintRequest1);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/requests/6071/10.20.152.18/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRequestById_notFound() throws Exception {
        Mockito.when(cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress("3020","10.30.154.23")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/requests/3020/10.30.154.23/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result ->
                        assertEquals("cardPrintRequest not found for this branchCode : 3020", result.getResolvedException().getMessage()));
    }

}

