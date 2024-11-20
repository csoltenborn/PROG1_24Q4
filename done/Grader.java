package done;

public class Grader {

    static String grade(final int points) {
        if (points > 100 || points < 0) {
            return "Ungültige Punktzahl";
        }
        if (points < 50) {
            return "5,0";
        }
        if (points < 59) {
            return "4,0";
        }
        if (points < 67) {
            return "3,7";
        }
        if (points < 72) {
            return "3,3";
        }
        if (points < 77) {
            return "3,0";
        }
        if (points < 81) {
            return "2,7";
        }
        if (points < 85) {
            return "2,3";
        }
        if (points < 89) {
            return "2,0";
        }
        if (points < 92) {
            return "1,7";
        }
        if (points < 97) {
            return "1,3";
        }
        return "1,0";
    }

}