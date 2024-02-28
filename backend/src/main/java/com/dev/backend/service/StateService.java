package com.dev.backend.service;

import com.dev.backend.entity.State;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dev.backend.repository.StateRepository;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<State> getAll() {
        return stateRepository.findAll();
    }

    public State addState(State state) {
        state.setCreationDate(new Date());
        return stateRepository.saveAndFlush(state);
    }

    public State updateState(State state, Long id) throws NotFoundException {
        try {
            state.setId(id);

            @SuppressWarnings("null")
            Optional<State> optionalState = stateRepository.findById(id);
            if (optionalState.isPresent()) {
                State existingState = optionalState.get();
                Date recoveryCreationDate = existingState.getCreationDate();
                state.setCreationDate(recoveryCreationDate);
                state.setUpdateDate(new Date());
                return stateRepository.saveAndFlush(state);
            } else {
                throw new RuntimeException("State not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating state with id: " + id, e);
        }
    }

    @SuppressWarnings("null")
    public void deleteState(Long id) {
        State state = stateRepository.findById(id).get();
        stateRepository.delete(state);
    }
}
