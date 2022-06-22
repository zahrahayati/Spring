package dotin.example.demo.repository;

import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.model.CardPrintRequestEmbeddedId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CardPrintRequestRepository extends JpaRepository<CardPrintRequest, CardPrintRequestEmbeddedId>, CustomCardPrintRequestRepository {

    @Query(value = "SELECT c FROM CardPrintRequest c where c.cardPAN = ?1",
            countQuery = "SELECT count(c) FROM CardPrintRequest c where c.cardPAN = ?1")
    Page<CardPrintRequest> findCardPrintRequestByCardPANPagination(String cardPan, Pageable pageable);

    @Query(value = "SELECT c FROM CardPrintRequest c where c.cardPAN = :cardPan")
    List<CardPrintRequest> findCardPrintRequestByCardPAN(@Param("cardPan") String cardPan);

    @Query(value = "SELECT c FROM CardPrintRequest c where c.personnelCode = :personnelCode")
    List<CardPrintRequest> findCardPrintRequestByPersonnelCode(@Param("personnelCode") String personnelCode);

    CardPrintRequest findCardPrintRequestsById_BranchCodeAndId_IpAddress(String branchCode, String ipAddress);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM CardPrintRequest c WHERE c.cardPAN = :cardPan")
    void deleteByCardPAN(@Param("cardPan") String cardPAN);

    @Query(value = "SELECT c.id.ipAddress FROM CardPrintRequest c where c.id.branchCode = :branchCode")
    List<String> getIpAddressByBranchCode(@Param("branchCode") String branchCode);


}
