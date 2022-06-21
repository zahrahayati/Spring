package dotin.example.demo.repository;

import java.util.List;

public interface CustomCardPrintRequestRepository {

    List<String> getIpAddressByBranchCode(String branchCode);
}
