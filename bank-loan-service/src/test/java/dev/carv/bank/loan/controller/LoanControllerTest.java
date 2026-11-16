package dev.carv.bank.loan.controller;

import dev.carv.bank.commons.constant.Message;
import dev.carv.bank.commons.dto.ResponseDto;
import dev.carv.bank.loan.constant.LoanType;
import dev.carv.bank.loan.dto.LoanDto;
import dev.carv.bank.loan.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoanControllerTest {

    @Mock
    private LoanService service;

    @InjectMocks
    private LoanController controller;

    private MockMvc mockMvc;

    private static final String MOBILE_NUMBER = "525512345678";
    private static final String LOAN_NUMBER = "75255123456758";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("createLoan should return 201 Created with ResponseDto")
    void createLoan_Success() throws Exception {
        doNothing().when(service).createLoan(MOBILE_NUMBER);

        ResponseEntity<ResponseDto> response = controller.createLoan(MOBILE_NUMBER);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().message()).isEqualTo(Message.RESOURCE_CREATED.format("Loan"));
        verify(service).createLoan(MOBILE_NUMBER);

        mockMvc.perform(post("/api/loan")
                .param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value(Message.RESOURCE_CREATED.format("Loan")));
    }

    @Test
    @DisplayName("fetchLoan should return 200 OK with LoanDto")
    void fetchLoan_Success() throws Exception {
        LoanDto dto = createLoanDto();
        when(service.fetchLoan(MOBILE_NUMBER)).thenReturn(dto);

        ResponseEntity<LoanDto> response = controller.fetchLoan(MOBILE_NUMBER);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto);
        verify(service).fetchLoan(MOBILE_NUMBER);

        mockMvc.perform(get("/api/loan")
                .param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.mobileNumber").value(MOBILE_NUMBER))
            .andExpect(jsonPath("$.loanNumber").value(LOAN_NUMBER))
            .andExpect(jsonPath("$.type").value(LoanType.HOME.name()));
    }

    @Test
    @DisplayName("updateLoan should return 200 OK when update succeeds")
    void updateLoan_Success() throws Exception {
        LoanDto dto = createLoanDto();
        when(service.updateLoan(any(LoanDto.class))).thenReturn(true);

        ResponseEntity<ResponseDto> response = controller.updateLoan(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().message()).isEqualTo(Message.SUCCESS.getText());
        verify(service).updateLoan(dto);

        String json = """
            {
              "mobileNumber": "%s",
              "loanNumber": "%s",
              "type": "HOME",
              "totalLoan": 100000.00,
              "paidLoan": 10000.00,
              "outstandingLoan": 90000.00
            }
            """.formatted(MOBILE_NUMBER, LOAN_NUMBER);

        mockMvc.perform(put("/api/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value(Message.SUCCESS.getText()));
    }

    @Test
    @DisplayName("updateLoan should return 500 Internal Server Error when update fails")
    void updateLoan_Failure() {
        LoanDto dto = createLoanDto();
        when(service.updateLoan(dto)).thenReturn(false);

        ResponseEntity<ResponseDto> response = controller.updateLoan(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().message()).isEqualTo(Message.INTERNAL_ERROR.getText());
        verify(service).updateLoan(dto);
    }

    @Test
    @DisplayName("deleteLoan should return 200 OK when delete succeeds")
    void deleteLoan_Success() throws Exception {
        when(service.deleteLoan(MOBILE_NUMBER)).thenReturn(true);

        ResponseEntity<ResponseDto> response = controller.deleteLoan(MOBILE_NUMBER);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().message()).isEqualTo(Message.SUCCESS.getText());
        verify(service).deleteLoan(MOBILE_NUMBER);

        mockMvc.perform(delete("/api/loan")
                .param("mobileNumber", MOBILE_NUMBER))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value(Message.SUCCESS.getText()));
    }

    @Test
    @DisplayName("deleteLoan should return 500 Internal Server Error when delete fails")
    void deleteLoan_Failure() {
        when(service.deleteLoan(MOBILE_NUMBER)).thenReturn(false);

        ResponseEntity<ResponseDto> response = controller.deleteLoan(MOBILE_NUMBER);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().message()).isEqualTo(Message.INTERNAL_ERROR.getText());
        verify(service).deleteLoan(MOBILE_NUMBER);
    }

    private LoanDto createLoanDto() {
        return new LoanDto(
            MOBILE_NUMBER,
            LOAN_NUMBER,
            LoanType.HOME.name(),
            new BigDecimal("100000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("90000.00")
        );
    }
}
