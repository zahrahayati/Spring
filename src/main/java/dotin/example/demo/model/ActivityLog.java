package dotin.example.demo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Component
@Table(name = "T_activityLog")
@PropertySource("classpath:application.properties")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_actionDate")
    private Date actionDate;

    @Column(name = "c_cardPAN")
    private String cardPAN;

    @Column(name = "c_actionName")
    private String actionName;

    @Column(name = "c_personnelCode")
    private String personnelCode;

    @Value("${activityLog.applicationType}")
    @Column(name = "c_applicationType")
    private String applicationType;

    public ActivityLog(Date actionDate, String cardPAN, String actionName, String personnelCode, String applicationType) {
        this.actionDate = actionDate;
        this.cardPAN = cardPAN;
        this.actionName = actionName;
        this.personnelCode = personnelCode;
        this.applicationType = applicationType;
    }

    public ActivityLog() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getCardPAN() {
        return cardPAN;
    }

    public void setCardPAN(String cardPAN) {
        this.cardPAN = cardPAN;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }
}
