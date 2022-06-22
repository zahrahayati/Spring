package dotin.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class CardPrintRequestEmbeddedId implements Serializable {

    @Size(min = 3)
    @NotEmpty
    @Column(name = "c_branchCode")
    private String branchCode;

    @Pattern(regexp = "^([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])(\\.([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$")
    @Column(name = "c_ipAddress")
    private String ipAddress;

    public CardPrintRequestEmbeddedId(String branchCode, String ipAddress) {
        this.branchCode = branchCode;
        this.ipAddress = ipAddress;
    }

    public CardPrintRequestEmbeddedId() {

    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
