package dk.sdu.mmmi.cbse.scoresystem.scoresystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoresystemApplication {
    private int destroyedEnemies;
    private int destroyedAsteroids;

    public static void main(String[] args) {
        SpringApplication.run(ScoresystemApplication.class, args);
    }

    @GetMapping("/incDestroyedEnemies")
    public void incDestroyedEnemies() {
        destroyedEnemies++;
    }

    @GetMapping("/incDestroyedAsteroids")
    public void incDestroyedAsteroids() {
        destroyedAsteroids++;
    }

    @GetMapping("/getDestroyedEnemies")
    public int getDestroyedEnemies(){
        return destroyedEnemies;
    }

    @GetMapping("/getDestroyedAsteroids")
    public int getDestroyedAsteroids(){
        return destroyedAsteroids;
    }
}
