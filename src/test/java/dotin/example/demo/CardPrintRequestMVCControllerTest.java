package dotin.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import dotin.example.demo.controller.CardPrintRequestMVCController;
import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.model.CardPrintRequestEmbeddedId;
import dotin.example.demo.repository.CardPrintRequestJDBCTemplate;
import dotin.example.demo.repository.CardPrintRequestRepository;
import dotin.example.demo.service.CardPrintRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardPrintRequestMVCController.class)
@AutoConfigureMockMvc
public class CardPrintRequestMVCControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CardPrintRequestService cardPrintRequestService;


    @MockBean
    private CardPrintRequestRepository cardPrintRequestRepository;

    @MockBean
    CardPrintRequestJDBCTemplate cardPrintRequestJDBCTemplate;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/login").with(user("admin").password("admin").roles("USER", "ADMIN")))
                .apply(springSecurity())
                .build();
    }


    CardPrintRequest cardPrintRequest = new CardPrintRequest(new CardPrintRequestEmbeddedId("600", "10.20.152.15"), "6063731098404032", "74100");
    CardPrintRequest cardPrintRequest1 = new CardPrintRequest(new CardPrintRequestEmbeddedId("6071", "10.20.152.18"), "6063731098404055", "75120");
    CardPrintRequest cardPrintRequest2 = new CardPrintRequest(new CardPrintRequestEmbeddedId("6070", "10.20.152.18"), "6063731098404055", "74501");

    public CardPrintRequestMVCControllerTest() throws ParseException {
    }

    @Test
    public void findAll_Test() throws Exception {

        Mockito.when(cardPrintRequestRepository.findAll()).thenReturn(Arrays.asList(cardPrintRequest1, cardPrintRequest2, cardPrintRequest));

        mockMvc.perform(get("/list-requests"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-requests"))
                .andExpect(forwardedUrl("/WEB-INF/JSP/list-requests.jsp"))
                .andExpect(model().attribute("requests", hasSize(3)))
                .andExpect(model().attribute("requests", hasItem(
                        allOf(
                                hasProperty("cardPAN", is("6063731098404032")),
                                hasProperty("personnelCode", is("74100"))
                        )
                )))
                .andExpect(model().attribute("requests", hasItem(
                        allOf(
                                hasProperty("cardPAN", is("6063731098404055")),
                                hasProperty("personnelCode", is("75120"))

                        )
                )))
                .andExpect(model().attribute("requests", hasItem(
                        allOf(
                                hasProperty("cardPAN", is("6063731098404055")),
                                hasProperty("personnelCode", is("7450"))

                        )
                )));

        verify(cardPrintRequestRepository, times(1)).findAll();
        verifyNoMoreInteractions(cardPrintRequestRepository);
    }

    @Test
    public void add_RequestAndPersonnelCodeIsMin_ShouldRenderFormViewAndReturnValidationErrorsForPersonnelCode() throws Exception {

        mockMvc.perform(post("/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("personnelCode", "741")
                .param("cardPAN", "5022295630236")
                .param("id.branchCode", "200")
                .param("id.ipAddress", "10.30.152.26")
                .sessionAttr("request", new CardPrintRequest())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("new_request"))
                .andExpect(forwardedUrl("/WEB-INF/JSP/new_request.jsp"))
                .andExpect(model().attributeHasFieldErrors("request", "personnelCode"))
                .andExpect(model().attribute("request", hasProperty("cardPAN", is("5022295630236"))));

    }

    @Test
    public void add_RequestAndPersonnelCodeIsNull_ShouldRenderFormViewAndReturnValidationErrorsForPersonnelCode() throws Exception {

        mockMvc.perform(post("/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardPAN", "5022295630236")
                .param("id.branchCode", "200")
                .param("id.ipAddress", "10.30.152.26")
                .sessionAttr("request", new CardPrintRequest())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("new_request"))
                .andExpect(forwardedUrl("/WEB-INF/JSP/new_request.jsp"))
                .andExpect(model().attributeHasFieldErrors("request", "personnelCode"))
                .andExpect(model().attribute("request", hasProperty("cardPAN", is("5022295630236"))));

    }

    @Test
    public void add_RequestAndPersonnelCodeIsMax_ShouldRenderFormViewAndReturnValidationErrorsForPersonnelCode() throws Exception {

        mockMvc.perform(post("/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("personnelCode", "505050505050505020103")
                .param("cardPAN", "5022295630236")
                .param("id.branchCode", "200")
                .param("id.ipAddress", "10.30.152.26")
                .sessionAttr("request", new CardPrintRequest())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("new_request"))
                .andExpect(forwardedUrl("/WEB-INF/JSP/new_request.jsp"))
                .andExpect(model().attributeHasFieldErrors("request", "personnelCode"))
                .andExpect(model().attribute("request", hasProperty("cardPAN", is("5022295630236"))));

    }

    @Test
    public void add_Request_Success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("personnelCode", "565920")
                .param("cardPAN", "5022295630236")
                .param("id.branchCode", "201")
                .param("id.ipAddress", "10.30.152.26")
                .sessionAttr("request", new CardPrintRequest())
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/JSP/show_request.jsp"))
                .andExpect(model().attribute("request", hasProperty("cardPAN", is("5022295630236"))));


    }
}

