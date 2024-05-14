package dk.sdu.mmmi.cbse.score;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ScoreController {
    private int destroyedAsteroids;
    private int destroyedEnemies;

    @PostMapping("/score")
    public void updateScore(@RequestBody Map<String, Integer> score) {
        this.destroyedAsteroids = score.get("destroyedAsteroids");
        this.destroyedEnemies = score.get("destroyedEnemies");
    }

    @GetMapping("/score")
    public Map<String, Integer> getScore() {
        Map<String, Integer> score = new HashMap<>();
        score.put("destroyedAsteroids", this.destroyedAsteroids);
        score.put("destroyedEnemies", this.destroyedEnemies);
        return score;
    }
}