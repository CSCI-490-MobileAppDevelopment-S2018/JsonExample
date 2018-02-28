package com.stingersof.jsonexample;

/**
 * Created by kbriggs on 2/19/18.
 */

public class GitReference {

    private String command;
    private String example;
    private String explanation;
    private String section;

    public GitReference() {}

    public GitReference(String command, String example, String explanation, String section) {
        this.command = command;
        this.example = example;
        this.explanation = explanation;
        this. section = section;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

}
