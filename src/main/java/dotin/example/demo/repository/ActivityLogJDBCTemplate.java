package dotin.example.demo.repository;

import dotin.example.demo.model.ActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityLogJDBCTemplate {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(ActivityLog activityLog) {
        return jdbcTemplate.update(
                "INSERT INTO T_activity_log(id,c_action_date, c_application_type, c_cardpan, c_personnel_code, c_action_name) VALUES(?,?,?,?,?,?)",
                activityLog.getId(), activityLog.getActionDate(), activityLog.getApplicationType(),
                activityLog.getCardPAN(), activityLog.getPersonnelCode(), activityLog.getActionName());
    }

}
