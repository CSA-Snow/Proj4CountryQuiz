package edu.cs.uga.proj4countryquiz;

import java.util.Random;

public class Question {
    private int qid;
    public String country;
    public String correctAnswer;
    public String wrongAnswer1;
    public String wrongAnswer2;

    public Question(int qid, String country, String correctAnswer) {
        this.country = country;
        this.correctAnswer = correctAnswer;
    }
    public Question(String country, String correctAnswer) {
        this.country = country;
        this.correctAnswer = correctAnswer;
    }


    /**
     * Gets the id of the question. To be used for ensuring unique questions.
     * @return the id, {@code qid} of the question/answer pair
     */
    public int getQid() {
        return qid;
    }

    /**
     * Populates {@code wrongAnswer1} and {@code wrongAnswer2} with strings. They will be unique and
     * not the same as {@code correctAnswer}
     */
    public void makeWrongAnswers() {
        String[] continents = {"Africa", "Asia", "Europe","Oceania","North America", "South America"};
        Random random = new Random();
        while (wrongAnswer1.equals(null)) {
            int contRandIndex = random.nextInt(7);
            if (!continents[contRandIndex].equals(correctAnswer)) {
                wrongAnswer1 = continents[contRandIndex];
            }
        }
        while (wrongAnswer2.equals(null)) {
            int contRandIndex = random.nextInt(7);
            if (!continents[contRandIndex].equals(correctAnswer) && !continents[contRandIndex].equals(wrongAnswer1)) {
                wrongAnswer2 = continents[contRandIndex];
            }
        }
    }

    /**
     * Returns whether an answer is correct or not by comparing the correct answer against the user's
     * answer.
     * @param answer the String that will be compared against {@code correctAnswer}
     * @return true if the answer is correct and false otherwise
     */
    public boolean isCorrect(String answer) {
        if (correctAnswer.equals(answer)) {
            return true;
        }
        return false;
    }



    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCountry() {
        return country;
    }

}