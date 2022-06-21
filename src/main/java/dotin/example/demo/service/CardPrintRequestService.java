package dotin.example.demo.service;

import dotin.example.demo.exception.ResourceNotFoundException;
import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.model.CardPrintRequestEmbeddedId;
import dotin.example.demo.repository.CardPrintRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class CardPrintRequestService implements ICardPrintRequestService {

    @Autowired
    CardPrintRequestRepository cardPrintRequestRepository;

    @Override
    public List<CardPrintRequest> getCardPrintRequestByCardPAN(String cardPAN) {
        return cardPrintRequestRepository.findCardPrintRequestByCardPAN(cardPAN);
    }

    @Override
    public void updateCardPrintRequest(CardPrintRequest cardPrintRequest) {
        cardPrintRequestRepository.save(cardPrintRequest);
    }

    @Override
    public void addCardPrintRequest(String personnelCode, String cardPAN, String ipAddress, String branchCode) {
        CardPrintRequest cardPrintRequest = null;
        try {
            cardPrintRequest = new CardPrintRequest(new CardPrintRequestEmbeddedId(branchCode, ipAddress), personnelCode, cardPAN);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cardPrintRequestRepository.save(cardPrintRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCardPrintRequest(CardPrintRequestEmbeddedId id) throws ResourceNotFoundException {
        CardPrintRequest cardPrintRequest = cardPrintRequestRepository.findCardPrintRequestsById_BranchCodeAndId_IpAddress(id.getBranchCode(), id.getIpAddress());
        if (cardPrintRequest == null)
            throw new ResourceNotFoundException("card print request not found");
        else
            cardPrintRequestRepository.delete(cardPrintRequest);

    }

    @Override
    public CardPrintRequest saveCardPrintRequest(CardPrintRequest cardPrintRequest) {
        cardPrintRequest.setIssuedDate(new Date());
        cardPrintRequestRepository.save(cardPrintRequest);
        return cardPrintRequest;

    }
}
