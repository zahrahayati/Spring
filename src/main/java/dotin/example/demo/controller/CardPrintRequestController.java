package dotin.example.demo.controller;

import dotin.example.demo.exception.ResourceNotFoundException;
import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.repository.CardPrintRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CardPrintRequestController {
    @Autowired
    private CardPrintRequestRepository cardPrintRequestRepository;


    @GetMapping("/requests/{branchCode}/{ipAddress}/")
    public ResponseEntity<CardPrintRequest> getRequestById(@PathVariable(value = "branchCode") String branchCode, @PathVariable(value = "ipAddress") String ipAddress)
            throws ResourceNotFoundException {
        CardPrintRequest cardPrintRequest = cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress(branchCode, ipAddress);
        if (cardPrintRequest == null)
            throw new ResourceNotFoundException("cardPrintRequest not found for this branchCode :: " + branchCode);
        return ResponseEntity.ok().body(cardPrintRequest);
    }

    @PostMapping("/request")
    public CardPrintRequest createRequest(@Valid @RequestBody CardPrintRequest cardPrintRequest) {
        return cardPrintRequestRepository.save(cardPrintRequest);
    }

    @PutMapping("/requests")
    public CardPrintRequest updateRequest(@Valid @RequestBody CardPrintRequest cardPrintRequest) throws ResourceNotFoundException {
        CardPrintRequest cardPrintRequest1 = cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress(cardPrintRequest.getId().getBranchCode(), cardPrintRequest.getId().getIpAddress());
        if (cardPrintRequest1 == null)
            throw new ResourceNotFoundException("cardPrintRequest not found for this branchCode : " + cardPrintRequest.getId().getBranchCode());

        cardPrintRequest1.setPersonnelCode(cardPrintRequest.getPersonnelCode());
        cardPrintRequest1.setCardPAN(cardPrintRequest.getCardPAN());
        return cardPrintRequestRepository.save(cardPrintRequest1);

    }

    @DeleteMapping("/requests/{branchCode}/{ipAddress}/")
    public Map<String, Boolean> deleteRequest(@PathVariable(value = "branchCode") String branchCode, @PathVariable(value = "ipAddress") String ipAddress)
            throws ResourceNotFoundException {
        CardPrintRequest cardPrintRequest = cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress(branchCode, ipAddress);
        if (cardPrintRequest == null)
            throw new ResourceNotFoundException("cardPrintRequest not found for this branchCode : " + branchCode);


        cardPrintRequestRepository.delete(cardPrintRequest);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CardPrintRequest>> getAllRequests(@RequestHeader Map<String, String> headers) {
        headers.forEach((s, s2) -> {
            System.out.println("Header =" + s + " Value =" + s2);
        });
        return ResponseEntity.ok(cardPrintRequestRepository.findAll());
    }
}
