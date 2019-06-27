package de.shgruppe.bartender.model;

public enum Emotion
{
  HAPPY ("Happy"),
  SURPRISED ("Surprised"),
  DISGUSTED ("Disgusted"),
  SAD ("Sad"),
  CALM ("Calm"),
  ANGRY ("Angry"),
  CONFUSED ("Confused");

  private String value;

  private Emotion(String value) {
    this.value = value;
  }
}
