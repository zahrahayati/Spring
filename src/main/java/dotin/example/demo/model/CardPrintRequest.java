package dotin.example.demo.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "T_CardPrintRequest")
@EntityListeners(AuditCardPrintRequest.class)
public class CardPrintRequest {

    @EmbeddedId
    @Valid
    private CardPrintRequestEmbeddedId id;

    @Size(min = 5, max = 10)
    @NotEmpty
    @Column(name = "c_personnelCode")
    private String personnelCode;

    @Column(name = "c_cardPAN")
    private String cardPAN;

    @Column(name = "c_issuedDate")
    @JsonFormat(pattern = "dd-MM-yyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date issuedDate;

    public CardPrintRequest(CardPrintRequestEmbeddedId id, String cardPAN, String personnelCode) throws ParseException {
        this.id = id;
        this.personnelCode = personnelCode;
        this.cardPAN = cardPAN;
        this.issuedDate = getDateWithoutTimeUsingFormat();
    }

    public CardPrintRequest() {

    }

    public CardPrintRequest(CardPrintRequestEmbeddedId id, String cardPAN) throws ParseException {
        this.id = id;
        this.cardPAN = cardPAN;
        this.issuedDate = getDateWithoutTimeUsingFormat();
    }


    public CardPrintRequestEmbeddedId getId() {
        return id;
    }

    public void setId(CardPrintRequestEmbeddedId id) {
        this.id = id;
    }

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }

    public String getCardPAN() {
        return cardPAN;
    }

    public void setCardPAN(String cardPAN) {
        this.cardPAN = cardPAN;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public static Date getDateWithoutTimeUsingFormat() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        return formatter.parse(formatter.format(new Date()));
    }
}
