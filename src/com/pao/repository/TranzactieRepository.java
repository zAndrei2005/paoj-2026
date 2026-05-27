package com.pao.repository;

import com.pao.db.DatabaseConnection;
import com.pao.project.model.Tranzactie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TranzactieRepository implements Repository<Tranzactie, Integer> {
    private final DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public Tranzactie save(Tranzactie entity) throws SQLException {
        try (Connection conn = db.getConnection()){
            return saveWithConnection(entity, conn);
        }
    }

    public Tranzactie saveWithConnection(Tranzactie entity, Connection conn) throws SQLException {
        String sql = "INSERT INTO tranzactii(iban_cont,suma,tip) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, entity.getIbanCont());
            ps.setDouble(2, entity.getSuma());
            ps.setString(3, entity.getTip());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    // could set generated id if Tranzactie had a setter
                }
            }
            return entity;
        }
    }

    @Override
    public Optional<Tranzactie> findById(Integer id) throws SQLException {
        String sql = "SELECT id,iban_cont,suma,tip,data FROM tranzactii WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Tranzactie t = new Tranzactie(rs.getInt("id"), rs.getDouble("suma"), rs.getString("tip"));
                    return Optional.of(t);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Tranzactie> findAll() throws SQLException {
        String sql = "SELECT id,iban_cont,suma,tip,data FROM tranzactii ORDER BY data DESC";
        List<Tranzactie> list = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Tranzactie t = new Tranzactie(rs.getInt("id"), rs.getDouble("suma"), rs.getString("tip"));
                list.add(t);
            }
        }
        return list;
    }

    public List<com.pao.dto.TranzactieDetail> findRecentTranzactiiWithAccount(int limit) throws SQLException {
        String sql = "SELECT t.id, t.iban_cont, t.suma, t.tip, t.data FROM tranzactii t ORDER BY t.data DESC LIMIT ?";
        List<com.pao.dto.TranzactieDetail> list = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    java.time.LocalDateTime dt = rs.getTimestamp(5).toLocalDateTime();
                    list.add(new com.pao.dto.TranzactieDetail(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), dt));
                }
            }
        }
        return list;
    }

    @Override
    public Tranzactie update(Tranzactie entity) throws SQLException {
        // minimal implementation: no updates needed
        throw new UnsupportedOperationException("Update not supported for Tranzactie");
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM tranzactii WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}



