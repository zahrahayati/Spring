package dotin.example.demo.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;

public class AuditCardPrintRequest {
    private static Log log = LogFactory.getLog(AuditCardPrintRequest.class);

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(CardPrintRequest cardPrintRequest) {
        if (cardPrintRequest.getId() == null) {
            log.info("[CardPrintRequest AUDIT] About to add a cardPrintRequest");
        } else {
            log.info("[CardPrintRequest AUDIT] About to update/delete cardPrintRequest , branchCode: " + cardPrintRequest.getId().getBranchCode());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(CardPrintRequest cardPrintRequest) {
        log.info("[CardPrintRequest AUDIT] add/update/delete complete for cardPrintRequest, branchCode: " + cardPrintRequest.getId().getBranchCode());
    }

    @PostLoad
    private void afterLoad(CardPrintRequest cardPrintRequest) {
        log.info("[CardPrintRequest AUDIT] cardPrintRequest loaded from database , branchCode: " + cardPrintRequest.getId().getBranchCode());
    }
}
