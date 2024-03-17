package com.dillonboardman.agevalidator.app.model;

import lombok.Getter;

@Getter
public class Machine {

  private final long age;
  private final String id;

  private long convertAgeToDays(String age) {
    var parts = age.split(" ");
    var unit = parts[1].toLowerCase();
    long value = Integer.parseInt(parts[0]);

    return switch (unit) {
      case "year", "years" -> value * 365;
      case "month", "months" -> value * 30;
      case "week", "weeks" -> value * 7;
      case "day", "days" -> value;
      default -> throw new IllegalArgumentException("invalid time unit: " + unit);
    };
  }

  /**
   * Converts string representation of a machine to an object, converts age unit to `days`
   *
   * @param machine requires the following format: {machine ID, age string}
   */
  public Machine(String machine) {
    var parts = machine.split(",");
    var machineId = parts[0].strip();
    var machineAge = parts[1].strip();

    this.id = machineId;
    this.age = convertAgeToDays(machineAge);
  }
}
