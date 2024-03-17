package com.dillonboardman.agevalidator.endpoint;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.dillonboardman.agevalidator.app.model.Machine;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AgeValidatorEndpoint {

  /**
   * Ingests a list of machine ages and identifies any outliers using a z-score outlier.
   * <a href="https://towardsdatascience.com/5-outlier-detection-methods-that-every-data-enthusiast-must-know-f917bf439210">more info</a>
   */
  @PostMapping("v1/validate")
  public ResponseEntity<List<String>> validateMachineAges(@RequestBody List<Machine> machines) {
    log.debug("ingested new machine objects");

    var outliers = new ArrayList<String>();
    var stats = new DescriptiveStatistics();

    // calculate mean and standard deviation
    machines.stream().mapToDouble(Machine::getAge).forEach(stats::addValue);
    var mean = stats.getMean();
    var stdDeviation = stats.getStandardDeviation();

    // define threshold
    var zScoreThreshold = 3;

    // identify outliers using z-score
    machines.forEach(machine -> {
      var zScore = (machine.getAge() - mean) / stdDeviation;
      if (Math.abs(zScore) > zScoreThreshold) {
        outliers.add(machine.getId());
        log.info("outlier detected: {}", machine.getId());
      }
    });

    if (outliers.isEmpty()) {
      return ResponseEntity.ok().body(null);
    } else {
      return ResponseEntity.status(BAD_REQUEST).body(outliers);
    }
  }
}
