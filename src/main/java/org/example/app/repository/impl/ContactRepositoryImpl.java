package org.example.app.repository.impl;

import org.example.app.repository.BaseRepository;
import org.example.app.entity.contact.Contact;
import org.example.app.entity.contact.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

// Positional parameters are used in SQL-queries
@Repository("contactRepository")
public class ContactRepositoryImpl implements BaseRepository<Contact> {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean create(Contact contact) {
        String sql = "INSERT INTO contacts (first_name, last_name, phone) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(),
                contact.getPhone()) > 0;
    }

    @Override
    public Optional<List<Contact>> getAll() {
        String sql = "SELECT * FROM contacts";
        Optional<List<Contact>> optional;
        try {
            optional = Optional.of(jdbcTemplate
                    .query(sql, new ContactMapper()));
        } catch (Exception ex) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public Optional<Contact> getById(Integer id) {
        String sql = "SELECT * FROM contacts WHERE id = ? LIMIT 1";
        Optional<Contact> optional;
        try {
            optional = Optional.ofNullable(jdbcTemplate
                    .queryForObject(sql, new ContactMapper(), id));
        } catch (Exception ex) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public boolean update(Contact contact) {
        Optional<Contact> optional = getById(contact.getId());
        if (optional.isEmpty()) return false;
        else {
            String sql = "UPDATE contacts SET first_name = ?, last_name = ?, phone = ? WHERE id = ?";
            return jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getPhone(),
                    contact.getId()) > 0;
        }
    }

    @Override
    public boolean delete(Contact contact) {
        Optional<Contact> optional = getById(contact.getId());
        if (optional.isEmpty()) return false;
        else {
            String sql = "DELETE FROM contacts WHERE id = ?";
            return jdbcTemplate.update(sql, contact.getId()) > 0;
        }
    }

}
