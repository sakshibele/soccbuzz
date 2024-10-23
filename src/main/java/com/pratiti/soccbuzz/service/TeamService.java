package com.pratiti.soccbuzz.service;

import com.pratiti.soccbuzz.dto.LeagueDTO;
import com.pratiti.soccbuzz.dto.TeamDTO;
import com.pratiti.soccbuzz.entity.LeagueEntity;
import com.pratiti.soccbuzz.entity.TeamEntity;
import com.pratiti.soccbuzz.exception.LeagueNotFoundException;
import com.pratiti.soccbuzz.exception.TeamNotFoundException;
import com.pratiti.soccbuzz.repository.LeagueRepository;
import com.pratiti.soccbuzz.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    public TeamDTO createTeam(TeamDTO teamDTO) {
        LeagueEntity league = leagueRepository.findById(teamDTO.getLeagueDTO().getLeagueId())
                .orElseThrow(() -> new LeagueNotFoundException("League not found"));

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamName(teamDTO.getTeamName());
        teamEntity.setLeague(league);
        teamEntity = teamRepository.save(teamEntity);

        return new TeamDTO(teamEntity.getTeamId(), teamEntity.getTeamName(),
                new LeagueDTO(teamEntity.getLeague().getLeagueId(), teamEntity.getLeague().getLeagueName()));
    }

    @Cacheable("teams")
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(team -> new TeamDTO(team.getTeamId(), team.getTeamName(),
                        new LeagueDTO(team.getLeague().getLeagueId(), team.getLeague().getLeagueName())))
                .collect(Collectors.toList());
    }

    public TeamDTO getTeamById(Integer id) {
        return teamRepository.findById(id)
                .map(team -> new TeamDTO(team.getTeamId(), team.getTeamName(),
                        new LeagueDTO(team.getLeague().getLeagueId(), team.getLeague().getLeagueName())))
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));
    }

    public TeamDTO updateTeam(Integer id, TeamDTO teamDTO) {
        TeamEntity teamEntity = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));

        LeagueEntity league = leagueRepository.findById(teamDTO.getLeagueDTO().getLeagueId())
                .orElseThrow(() -> new LeagueNotFoundException("League not found"));

        teamEntity.setTeamName(teamDTO.getTeamName());
        System.out.println(league);

        teamEntity.setLeague(league);

        teamRepository.save(teamEntity);

        return new TeamDTO(teamEntity.getTeamId(), teamEntity.getTeamName(),
                new LeagueDTO(teamEntity.getLeague().getLeagueId(), teamEntity.getLeague().getLeagueName()));
    }

    public void deleteTeam(Integer id) {
        if (!teamRepository.existsById(id)) {
            throw new TeamNotFoundException("Team not found");
        }
        teamRepository.deleteById(id);
    }
}
