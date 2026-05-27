package com.pao.repository;

import com.pao.db.DatabaseConnection;
import com.pao.project.model.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepository implements Repository<Card, String> {
    private final DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public Card save(Card entity) throws SQLException {
        String sql = "INSERT INTO carduri(numar_card,pin,stare_activa,iban_cont) VALUES (?,?,?,?)";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, entity.getNumarCard());
            ps.setString(2, ""); // pin not stored in model publicly
            ps.setBoolean(3, entity.isStareActiva());
            ps.setString(4, entity.getIbanContCard());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<Card> findById(String id) throws SQLException {
        String sql = "SELECT numar_card,pin,stare_activa,iban_cont FROM carduri WHERE numar_card = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Card c = new Card(rs.getString("numar_card"), rs.getString("pin"), rs.getString("iban_cont"));
                    if(!rs.getBoolean("stare_activa")) c.blocareCard();
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Card> findAll() throws SQLException {
        String sql = "SELECT numar_card,pin,stare_activa,iban_cont FROM carduri";
        List<Card> list = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Card c = new Card(rs.getString("numar_card"), rs.getString("pin"), rs.getString("iban_cont"));
                if(!rs.getBoolean("stare_activa")) c.blocareCard();
                list.add(c);
            }
        }
        return list;
    }

    public List<com.pao.dto.CardDetail> findCardsWithOwner() throws SQLException {
        String sql = "SELECT ca.numar_card, ca.stare_activa, ca.iban_cont, cl.nume FROM carduri ca JOIN conturi c ON ca.iban_cont = c.iban JOIN clienti cl ON c.client_cnp = cl.cnp";
        List<com.pao.dto.CardDetail> list = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                list.add(new com.pao.dto.CardDetail(rs.getString(1), rs.getBoolean(2), rs.getString(3), rs.getString(4)));
            }
        }
        return list;
    }

    @Override
    public Card update(Card entity) throws SQLException {
        String sql = "UPDATE carduri SET pin = ?, stare_activa = ?, iban_cont = ? WHERE numar_card = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "");
            ps.setBoolean(2, entity.isStareActiva());
            ps.setString(3, entity.getIbanContCard());
            ps.setString(4, entity.getNumarCard());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public boolean deleteById(String id) throws SQLException {
        String sql = "DELETE FROM carduri WHERE numar_card = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}


