package dotin.example.demo.service;

import dotin.example.demo.exception.ResourceNotFoundException;
import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.model.CardPrintRequestEmbeddedId;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ICardPrintRequestService {
    List<CardPrintRequest> getCardPrintRequestByCardPAN(String cardPAN);

    void updateCardPrintRequest(CardPrintRequest cardPrintRequest);

    void addCardPrintRequest(String personnelCode, String cardPAN, String ipAddress, String branchCode);

    void deleteCardPrintRequest(CardPrintRequestEmbeddedId id) throws ResourceNotFoundException;

    CardPrintRequest saveCardPrintRequest(CardPrintRequest cardPrintRequest);
}
