package com.pao.project.service;

import com.pao.audit.AuditService;
import com.pao.db.DatabaseConnection;
import com.pao.project.model.Cont;
import com.pao.project.model.Tranzactie;
import com.pao.repository.ContRepository;
import com.pao.repository.TranzactieRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcBancaService {
    private final DatabaseConnection db = DatabaseConnection.getInstance();
    private final ContRepository contRepo = new ContRepository();
    private final TranzactieRepository tranzRepo = new TranzactieRepository();
    private final AuditService audit = AuditService.getInstance();

    public void transfer(String ibanSursa, String ibanDest, double suma, String performedBy) throws SQLException {
        try (Connection conn = db.getConnection()){
            conn.setAutoCommit(false);
            try {
                Optional<Cont> srcOpt = contRepo.findByIdWithConnection(ibanSursa, conn);
                Optional<Cont> dstOpt = contRepo.findByIdWithConnection(ibanDest, conn);
                if(!srcOpt.isPresent() || !dstOpt.isPresent()){
                    throw new SQLException("One of the accounts not found");
                }
                Cont src = srcOpt.get();
                Cont dst = dstOpt.get();
                if(src.getSold() < suma) throw new SQLException("Insufficient funds");

                // update balances
                contRepo.updateSoldWithConnection(ibanSursa, src.getSold() - suma, conn);
                contRepo.updateSoldWithConnection(ibanDest, dst.getSold() + suma, conn);

                // record transactions
                Tranzactie t1 = new Tranzactie(0, ibanSursa, -suma, "Transfer-out to " + ibanDest);
                Tranzactie t2 = new Tranzactie(0, ibanDest, suma, "Transfer-in from " + ibanSursa);
                tranzRepo.saveWithConnection(t1, conn);
                tranzRepo.saveWithConnection(t2, conn);

                conn.commit();

                audit.audit("TRANSFER", "from=" + ibanSursa + ";to=" + ibanDest + ";amount=" + suma + ";by=" + performedBy);
            } catch (SQLException ex){
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}

