package com.pratiti.soccbuzz.service;

import com.pratiti.soccbuzz.dto.LeagueDTO;
import com.pratiti.soccbuzz.dto.MatchDTO;
import com.pratiti.soccbuzz.dto.ScheduleDTO;
import com.pratiti.soccbuzz.dto.TeamDTO;
import com.pratiti.soccbuzz.entity.LeagueEntity;
import com.pratiti.soccbuzz.entity.MatchEntity;
import com.pratiti.soccbuzz.entity.ScheduleEntity;
import com.pratiti.soccbuzz.entity.TeamEntity;
import com.pratiti.soccbuzz.exception.MatchNotFoundException;
import com.pratiti.soccbuzz.repository.LeagueRepository;
import com.pratiti.soccbuzz.repository.MatchRepository;
import com.pratiti.soccbuzz.repository.ScheduleRepository;
import com.pratiti.soccbuzz.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private  final LeagueRepository leagueRepository;

    private final ScheduleRepository scheduleRepository;

    private final TeamRepository teamRepository;

    private final ModelMapper mapper;

    public MatchDTO saveMatch(MatchDTO matchDTO) {


        MatchEntity savedMatch=matchRepository.save(convertToMatchEntity(matchDTO));

        return convertToMatchDTO(savedMatch);
    }

    public MatchEntity convertToMatchEntity(MatchDTO matchDTO){
        MatchEntity matchEntity = new MatchEntity();
        if(matchDTO.getMatchId()!=null){
            matchEntity.setMatchId(matchDTO.getMatchId());
        }
        matchEntity.setMatchName(matchDTO.getMatchName());

        Optional<LeagueEntity> leagueEntity = leagueRepository.findById(matchDTO.getLeagueDTO().getLeagueId());
        leagueEntity.ifPresent(matchEntity::setLeague);

        Optional<TeamEntity> team1Entity = teamRepository.findById(matchDTO.getTeam1DTO().getTeamId());
        team1Entity.ifPresent(matchEntity::setTeam1);

        Optional<TeamEntity> team2Entity = teamRepository.findById(matchDTO.getTeam2DTO().getTeamId());
        team2Entity.ifPresent(matchEntity::setTeam2);

        Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findById(matchDTO.getScheduleDTO().getScheduleId());
        scheduleEntity.ifPresent(matchEntity::setSchedule);

        matchEntity.setTeam1Score(matchDTO.getTeam1Score());
        matchEntity.setTeam2Score(matchDTO.getTeam2Score());
        return matchEntity;
    }

    @Cacheable("matches")
    public List<MatchDTO> getAllMatches() {
        Sort sort = Sort.by(Sort.Direction.ASC, "matchName");
        List<MatchEntity> matchEntities = matchRepository.findAll(sort);

        return matchEntities.stream()
                .map(this::convertToMatchDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert MatchEntity to MatchDTO
    private MatchDTO convertToMatchDTO(MatchEntity matchEntity) {
       MatchDTO matchDTO = new MatchDTO();
       matchDTO.setMatchId(matchEntity.getMatchId());
       matchDTO.setMatchName(matchEntity.getMatchName());
       matchDTO.setLeagueDTO(mapper.map(matchEntity.getLeague(), LeagueDTO.class));
       matchDTO.setTeam1DTO(mapper.map(matchEntity.getTeam1(), TeamDTO.class));
        matchDTO.setTeam2DTO(mapper.map(matchEntity.getTeam2(), TeamDTO.class));
        matchDTO.setScheduleDTO(mapper.map(matchEntity.getSchedule(), ScheduleDTO.class));
       matchDTO.setTeam1Score(matchEntity.getTeam1Score());
       matchDTO.setTeam2Score(matchEntity.getTeam2Score());

       return matchDTO;
    }


    public MatchDTO getMatchById(Integer matchId) {
        if(matchRepository.findById(matchId).isEmpty()){
          throw  new MatchNotFoundException("Match Not Found");
        }
        return convertToMatchDTO(matchRepository.findById(matchId). get());
        }

    @CacheEvict(value = "matches", allEntries = true)  // Clears cache when a match is updated
    public MatchDTO updateMatch(MatchDTO matchDTO) {
        if(matchRepository.findById(matchDTO.getMatchId()).isEmpty()){
            throw  new MatchNotFoundException("Match Not Found");
        }
        return convertToMatchDTO(matchRepository.save(convertToMatchEntity(matchDTO)));
    }

    public boolean deleteMatchById(Integer matchId) {
        if(matchRepository.findById(matchId).isEmpty()){
            throw  new MatchNotFoundException("Match Not Found");
        }
        matchRepository.deleteById(matchId);
        return true;
    }

    public List<MatchDTO> searchMatchesByTeamName(String teamName) {
        List<MatchEntity> matchesByTeam = matchRepository.findByTeamName(teamName);

        return matchesByTeam.stream()
                .map(this::convertToMatchDTO)
                .collect(Collectors.toList());
    }

    // Search matches by date
    public List<MatchDTO> searchMatchesByDate(LocalDateTime date) {
        List<MatchEntity> matchesByDate = matchRepository.findByMatchDate(date);

        return matchesByDate.stream()
                .map(this::convertToMatchDTO)
                .collect(Collectors.toList());
    }
    public List<MatchDTO> searchMatchesByTeamAndDateRange(String teamName, LocalDateTime startDate, LocalDateTime endDate) {
        List<MatchEntity> matches = List.of();

        // If both teamName and date range are provided
        if (teamName != null && startDate != null && endDate != null) {
            matches = matchRepository.findByTeamNameAndMatchDateBetween(teamName, startDate, endDate);
        }
        // If only the teamName is provided
        else if (teamName != null) {
            matches = matchRepository.findByTeamName(teamName);
        }
//        // If only the date range is provided
//        else if (startDate != null && endDate != null) {
//            matches = matchRepository.findByMatchDateBetween(startDate, endDate);
//        } else {
//            throw new IllegalArgumentException("Either teamName or date range must be provided.");
//        }

        return matches.stream()
                .map(this::convertToMatchDTO)
                .collect(Collectors.toList());
    }
}
