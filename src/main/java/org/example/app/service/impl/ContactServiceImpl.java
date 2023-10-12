package org.example.app.service.impl;

import org.example.app.repository.impl.ContactRepositoryImpl;
import org.example.app.entity.contact.Contact;
import org.example.app.exceptions.ContactDataException;
import org.example.app.service.BaseService;
import org.example.app.utils.Constants;
import org.example.app.utils.validator.IdValidator;
import org.example.app.utils.validator.PhoneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service("contactService")
public class ContactServiceImpl implements BaseService<Contact> {

    @Autowired
    Contact contact;
    @Autowired
    ContactRepositoryImpl repoImpl;

    Map<String, String> errors = new HashMap<>();

    @Override
    public String create(Contact contact) {
        validateData(contact);
        if (!errors.isEmpty()) {
            try {
                throw new ContactDataException("Check inputs", errors);
            } catch (ContactDataException e) {
                return e.getErrors(errors);
            }
        }

        if (repoImpl.create(contact)) {
            return Constants.DATA_INSERT_MSG;
        } else {
            return Constants.SMTH_WRONG_MSG;
        }
    }

    @Override
    public String getAll() {
        Optional<List<Contact>> optional = repoImpl.getAll();
        if (optional.isPresent()) {
            AtomicInteger count = new AtomicInteger(0);
            StringBuilder stringBuilder = new StringBuilder();
            List<Contact> list = optional.get();
            list.forEach((contact) ->
                    stringBuilder.append(count.incrementAndGet())
                            .append(") ")
                            .append(contact.toString())
            );
            return stringBuilder.toString();
        } else return Constants.DATA_ABSENT_MSG;
    }

    @Override
    public String getById(String id) {
        validateId(id);
        if (!errors.isEmpty()) {
            try {
                throw new ContactDataException("Check inputs", errors);
            } catch (ContactDataException e) {
                return e.getErrors(errors);
            }
        }

        Optional<Contact> optional = repoImpl.getById(Integer.parseInt(id));
        if (optional.isEmpty()) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            Contact contact = optional.get();
            return contact.toString();
        }
    }

    @Override
    public String update(Contact contact) {
        validateData(contact);
        validateId(String.valueOf(contact.getId()));
        if (!errors.isEmpty()) {
            try {
                throw new ContactDataException("Check inputs",
                        errors);
            } catch (ContactDataException e) {
                return e.getErrors(errors);
            }
        }

        if (repoImpl.update(contact)) {
            return Constants.DATA_UPDATE_MSG;
        } else {
            return Constants.SMTH_WRONG_MSG;
        }
    }

    @Override
    public String delete(String id) {
        validateId(id);
        if (!errors.isEmpty()) {
            try {
                throw new ContactDataException("Check inputs", errors);
            } catch (ContactDataException e) {
                return e.getErrors(errors);
            }
        }

        contact.setId(Integer.parseInt(id));
        if (repoImpl.delete(contact)) {
            return Constants.DATA_DELETE_MSG;
        } else {
            return Constants.SMTH_WRONG_MSG;
        }
    }

    private void validateData(Contact contact) {
        if (contact.getFirstName().isEmpty())
            errors.put("first name", Constants.INPUT_REQ_MSG);
        if (contact.getLastName().isEmpty())
            errors.put("last name", Constants.INPUT_REQ_MSG);
        if (PhoneValidator.isPhoneValid(contact.getPhone()))
            errors.put("phone", Constants.PHONE_ERR_MSG);
    }

    private void validateId(String id) {
        if (IdValidator.isIdValid(id))
            errors.put("id", Constants.ID_ERR_MSG);
    }
}
