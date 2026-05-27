package com.pao.repository;

import com.pao.db.DatabaseConnection;
import com.pao.project.model.Cont;
import com.pao.project.model.ContCurent;
import com.pao.project.model.ContEconomii;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContRepository implements Repository<Cont, String> {
    private final DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public Cont save(Cont entity) throws SQLException {
        throw new SQLException("Use saveForClient(Cont, String) so client_cnp is always provided.");
    }

    public Cont saveForClient(Cont entity, String clientCnp) throws SQLException {
        String sql = "INSERT INTO conturi(iban,sold,moneda,client_cnp,tip,limita_descoperit,rata_dobanda) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, entity.getIban());
            ps.setDouble(2, entity.getSold());
            ps.setString(3, entity.getMoneda());
            ps.setString(4, clientCnp);
            if(entity instanceof ContCurent) {
                ps.setString(5, "CUR");
                ps.setDouble(6, ((ContCurent) entity).getLimitaDescoperit());
                ps.setNull(7, Types.DOUBLE);
            } else if(entity instanceof ContEconomii){
                ps.setString(5, "ECON");
                ps.setNull(6, Types.DOUBLE);
                ps.setDouble(7, ((ContEconomii) entity).getRataDobanda());
            } else {
                ps.setString(5, "UNKNOWN");
                ps.setNull(6, Types.DOUBLE);
                ps.setNull(7, Types.DOUBLE);
            }
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<Cont> findById(String id) throws SQLException {
        String sql = "SELECT iban,sold,moneda,tip,limita_descoperit,rata_dobanda FROM conturi WHERE iban = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    String tip = rs.getString("tip");
                    Cont c;
                    if("CUR".equals(tip)){
                        c = new ContCurent(rs.getString("iban"), rs.getDouble("sold"), rs.getDouble("limita_descoperit"));
                    } else if("ECON".equals(tip)){
                        c = new ContEconomii(rs.getString("iban"), rs.getDouble("sold"), rs.getDouble("rata_dobanda"));
                    } else {
                        c = new ContEconomii(rs.getString("iban"), rs.getDouble("sold"), 0.0);
                    }
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    // Find account using provided connection (no closing)
    public Optional<Cont> findByIdWithConnection(String id, Connection conn) throws SQLException {
        String sql = "SELECT iban,sold,moneda,tip,limita_descoperit,rata_dobanda FROM conturi WHERE iban = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    String tip = rs.getString("tip");
                    Cont c;
                    if("CUR".equals(tip)){
                        c = new ContCurent(rs.getString("iban"), rs.getDouble("sold"), rs.getDouble("limita_descoperit"));
                    } else if("ECON".equals(tip)){
                        c = new ContEconomii(rs.getString("iban"), rs.getDouble("sold"), rs.getDouble("rata_dobanda"));
                    } else {
                        c = new ContEconomii(rs.getString("iban"), rs.getDouble("sold"), 0.0);
                    }
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Cont> findAll() throws SQLException {
        String sql = "SELECT iban,sold,moneda,tip,limita_descoperit,rata_dobanda FROM conturi";
        List<Cont> list = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String tip = rs.getString("tip");
                Cont c;
                if("CUR".equals(tip)){
                    c = new ContCurent(rs.getString("iban"), rs.getDouble("sold"), rs.getDouble("limita_descoperit"));
                } else if("ECON".equals(tip)){
                    c = new ContEconomii(rs.getString("iban"), rs.getDouble("sold"), rs.getDouble("rata_dobanda"));
                } else {
                    c = new ContEconomii(rs.getString("iban"), rs.getDouble("sold"), 0.0);
                }
                list.add(c);
            }
        }
        return list;
    }

    public List<com.pao.dto.AccountWithClient> findAccountsWithClients() throws SQLException {
        String sql = "SELECT c.iban, c.sold, cl.cnp, cl.nume FROM conturi c JOIN clienti cl ON c.client_cnp = cl.cnp";
        List<com.pao.dto.AccountWithClient> list = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                list.add(new com.pao.dto.AccountWithClient(rs.getString(1), rs.getDouble(2), rs.getString(3), rs.getString(4)));
            }
        }
        return list;
    }

    @Override
    public Cont update(Cont entity) throws SQLException {
        String sql = "UPDATE conturi SET sold = ?, moneda = ?, tip = ?, limita_descoperit = ?, rata_dobanda = ? WHERE iban = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setDouble(1, entity.getSold());
            ps.setString(2, entity.getMoneda());
            if(entity instanceof ContCurent){
                ps.setString(3, "CUR");
                ps.setDouble(4, ((ContCurent) entity).getLimitaDescoperit());
                ps.setNull(5, Types.DOUBLE);
            } else if(entity instanceof ContEconomii){
                ps.setString(3, "ECON");
                ps.setNull(4, Types.DOUBLE);
                ps.setDouble(5, ((ContEconomii) entity).getRataDobanda());
            } else {
                ps.setString(3, "UNKNOWN");
                ps.setNull(4, Types.DOUBLE);
                ps.setNull(5, Types.DOUBLE);
            }
            ps.setString(6, entity.getIban());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public boolean deleteById(String id) throws SQLException {
        String sql = "DELETE FROM conturi WHERE iban = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Update sold using provided connection (for transactions)
    public void updateSoldWithConnection(String iban, double newSold, Connection conn) throws SQLException {
        String sql = "UPDATE conturi SET sold = ? WHERE iban = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setDouble(1, newSold);
            ps.setString(2, iban);
            ps.executeUpdate();
        }
    }
}



