package com.dev.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dev.backend.entity.State;
import com.dev.backend.service.StateService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/state")
public class StateController {
    @Autowired
    private StateService stateService;

    @GetMapping("/")
    public List<State> getAll() {
        return stateService.getAll();
    }

    @PostMapping("/")
    public State addState(@RequestBody State state) {
        return stateService.addState(state);
    }

    @PutMapping("/{id}")
    public State updateState(@RequestBody State state, @PathVariable Long id) throws Exception {
        return stateService.updateState(state, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        stateService.deleteState(id);
        return ResponseEntity.ok().build();
    }
}
