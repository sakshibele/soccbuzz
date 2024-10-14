package com.pratiti.soccbuzz.controller;

import com.pratiti.soccbuzz.dto.MatchDTO;
import com.pratiti.soccbuzz.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
@CrossOrigin
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<?> saveMatch(@RequestBody @Valid MatchDTO matchDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("validation failed") ;
        }
        MatchDTO savedMatch = matchService.saveMatch(matchDTO);
        return ResponseEntity.ok(savedMatch);
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllMatches(){
        List<MatchDTO> matches= matchService.getAllMatches();
        if(matches.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matches Doesn't Exist");
        }
        return ResponseEntity.ok("Matches:"+matches);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<?> getMatchById(@PathVariable Integer matchId){
        MatchDTO matchDTO = matchService.getMatchById(matchId);

            return ResponseEntity.ok(matchDTO);
    }

    @PutMapping("/{matchId}")
    public ResponseEntity<?> updateMatchById(@PathVariable Integer matchId,@RequestBody MatchDTO matchDTO){
    MatchDTO updatedMatchDTO = matchService.updateMatch(matchDTO);
    if(updatedMatchDTO==null)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Matches Found with given Id");
    else
        return ResponseEntity.ok(updatedMatchDTO);
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<?> deleteMatchById(@PathVariable Integer matchId){
        matchService.deleteMatchById(matchId);
            return ResponseEntity.ok().body("Match Deleted");


    }
}