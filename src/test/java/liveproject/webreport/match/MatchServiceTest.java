package liveproject.webreport.match;

import liveproject.webreport.season.Season;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {
    public static final String SEASON_STR = "1910-1911";
    public static final String WINSTON_CHURCHILL = "Winston Churchill";
    private final MatchRepository matchRepository = Mockito.mock(MatchRepository.class);
    private MatchService matchService;

    @BeforeEach
    public void setup() {
        matchService = new MatchService(matchRepository);
        Match theGame = Match.builder()
                .id(1L)
                .awayTeam("Manchester United")
                .homeTeam("Leeds United")
                .fullTimeAwayGoals(0)
                .fullTimeHomeGoals(7)
                .halfTimeAwayGoals(0)
                .halfTimeHomeGoals(4)
                .referee(WINSTON_CHURCHILL)
                .fullTimeResult(Match.Result.HOME_WIN)
                .season(SEASON_STR)
                .build();
        when(matchRepository.findBySeason(SEASON_STR)).thenReturn(Collections.singletonList(theGame));
    }

    @Test
    public void test_aggregateSeason() {
        Season season = matchService.aggregateSeason(SEASON_STR);
        assertThat(season.getAwayWins()).isEqualTo(0);
        assertThat(season.getHomeWins()).isEqualTo(1);
        assertThat(season.getRefereeResults().size()).isEqualTo(1);
        Season.RefereeResults refereeResults = (Season.RefereeResults) season.getRefereeResults().toArray()[0];
        assertThat(refereeResults.getName()).isEqualTo(WINSTON_CHURCHILL);
        assertThat(refereeResults.getHomeWins()).isEqualTo(1);
        assertThat(refereeResults.getAwayWins()).isEqualTo(0);
    }
}
