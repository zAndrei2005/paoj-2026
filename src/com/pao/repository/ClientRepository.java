package com.pao.repository;

import com.pao.db.DatabaseConnection;
import com.pao.project.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements Repository<Client, String> {
    private final DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public Client save(Client entity) throws SQLException {
        String sql = "INSERT INTO clienti(cnp,nume,adresa) VALUES (?,?,?)";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getCnp());
            ps.setString(2, entity.getNume());
            ps.setString(3, entity.getAdresa());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<Client> findById(String id) throws SQLException {
        String sql = "SELECT cnp,nume,adresa FROM clienti WHERE cnp = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Client c = new Client(rs.getString("nume"), rs.getString("adresa"), rs.getString("cnp"));
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() throws SQLException {
        String sql = "SELECT cnp,nume,adresa FROM clienti";
        List<Client> all = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Client c = new Client(rs.getString("nume"), rs.getString("adresa"), rs.getString("cnp"));
                all.add(c);
            }
        }
        return all;
    }

    @Override
    public Client update(Client entity) throws SQLException {
        String sql = "UPDATE clienti SET nume = ?, adresa = ? WHERE cnp = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, entity.getNume());
            ps.setString(2, entity.getAdresa());
            ps.setString(3, entity.getCnp());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public boolean deleteById(String id) throws SQLException {
        String sql = "DELETE FROM clienti WHERE cnp = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}

