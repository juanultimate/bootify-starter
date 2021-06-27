package liveproject.webreport.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class MatchController {

    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("season-report")
    @ResponseStatus(value = HttpStatus.OK)
    public String account(@ModelAttribute MatchForm matchForm, Model model) {
        model.addAttribute("seasons", matchService.getAllSeasons());
        if (matchForm.getSeasonSelected() != null) {
            model.addAttribute("season", matchService.aggregateSeason(matchForm.getSeasonSelected()));
        }
        return "reports/SeasonReport";
    }
}
