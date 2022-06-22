package dotin.example.demo.repository;

import dotin.example.demo.model.CardPrintRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CardPrintRequestJDBCTemplate {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(CardPrintRequest cardPrintRequest) {
        return jdbcTemplate.update(
                "INSERT INTO T_card_print_request(c_branch_code, c_ip_address, c_cardpan, c_personnel_code, c_issued_date) VALUES(?,?,?,?,?)",
                cardPrintRequest.getId().getBranchCode(), cardPrintRequest.getId().getIpAddress(),
                cardPrintRequest.getCardPAN(), cardPrintRequest.getPersonnelCode(), cardPrintRequest.getIssuedDate());
    }
}
