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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<?> getAllMatches(){
        List<MatchDTO> matches= matchService.getAllMatches();
        if(matches.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matches Doesn't Exist");
        }
        return ResponseEntity.ok(matches);
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


    //either search by date or team
    @GetMapping("/search")
    public ResponseEntity<?> searchMatchesByTeamName(@RequestParam(required = false) String teamName, @RequestParam(required = false) String date) {
        if (teamName != null && !teamName.isEmpty()) {
            List<MatchDTO> matchesByTeam = matchService.searchMatchesByTeamName(teamName);
            if (matchesByTeam.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Matches Found for Team: " + teamName);
            }
            return ResponseEntity.ok(matchesByTeam);
        }

        // Search matches by date
        if (date != null && !date.isEmpty()) {
            try {
                // Define the date format; adjust to your needs
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime parsedDate = LocalDateTime.parse(date, formatter);

                List<MatchDTO> matchesByDate = matchService.searchMatchesByDate(parsedDate);
                if (matchesByDate.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("No Matches Found on Date: " + date);
                }
                return ResponseEntity.ok(matchesByDate);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Invalid date format. Please use 'yyyy-MM-dd HH:mm'.");
            }
        }

        return ResponseEntity.badRequest().body("Please provide a valid team name or date for search.");
    }

    @GetMapping("/searchByTeamAndDateRange")
    public ResponseEntity<?> searchMatchesByTeamAndDateRange(@RequestParam(required = false) String teamName,
                                                             @RequestParam(required = false) String startDate,
                                                             @RequestParam(required = false) String endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime startDateTime = (startDate != null && !startDate.isEmpty()) ? LocalDateTime.parse(startDate, formatter) : null;
            LocalDateTime endDateTime = (endDate != null && !endDate.isEmpty()) ? LocalDateTime.parse(endDate, formatter) : null;

            List<MatchDTO> matches = matchService.searchMatchesByTeamAndDateRange(teamName, startDateTime, endDateTime);
            if (matches.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Matches Found for the provided search criteria.");
            }
            return ResponseEntity.ok(matches);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format. Please use 'yyyy-MM-dd HH:mm'.");
        }
    }
}