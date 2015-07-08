/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nl.mpi.tg.eg.frinex.model.ExperimentData;
import nl.mpi.tg.eg.frinex.model.ScreenData;
import nl.mpi.tg.eg.frinex.model.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since Jun 30, 2015 11:46:06 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@RestController
public class ExperimentService {

    @Autowired
    ScreenDataRepository screenDataRepository;
    TimeStampRepository timeStampRepository;

    @RequestMapping("/experiment/{name}")
    public String test(@PathVariable String name) {
        return "Experiment: " + name;
    }

    @RequestMapping(value = "/screenChange", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ScreenData>> registerScreenData(@RequestBody List<ScreenData> screenDataList) {
        ArrayList<ScreenData> invalidScreenData = new ArrayList<>();
        for (ScreenData screenData : screenDataList) {
            if (screenData.getExperimentName() == null || screenData.getExperimentName().isEmpty()) {
//                throw new IllegalArgumentException("The 'ExperimentName' parameter is required");
                invalidScreenData.add(screenData);
            } else if (screenData.getScreenName() == null || screenData.getScreenName().isEmpty()) {
//                throw new IllegalArgumentException("The 'ScreenName' parameter is required");
                invalidScreenData.add(screenData);
            } else if (screenData.getViewDate() == null) {
//                throw new IllegalArgumentException("The 'ViewDate' parameter is required");
                invalidScreenData.add(screenData);
            } else if (screenData.getSubmitDate() != null) {
//                throw new IllegalArgumentException("SubmitDate cannot be provided");
                invalidScreenData.add(screenData);
            } else {
                screenData.setSubmitDate(new java.util.Date());
                screenDataRepository.save(screenData);
            }
        }
        final ResponseEntity responseEntity;
        if (invalidScreenData.isEmpty()) {
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(invalidScreenData, HttpStatus.MULTI_STATUS);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/timeStamp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity registerScreenData(@RequestBody TimeStamp timeStamp) {
        timeStamp.setSubmitDate(new java.util.Date());
        timeStampRepository.save(timeStamp);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/experimentData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ExperimentData getOne(@RequestParam(value = "name", required = false, defaultValue = "param12") String name) {
        return new ExperimentData(new Random().nextLong(), name, new Random().nextBoolean() + "");
    }

//    @RequestMapping(value = "/experimentData/csv", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
//    public @ResponseBody
//    ExperimentData getCsv(@RequestParam(value = "name", required = false, defaultValue = "param12") String name) {
//        return new ExperimentData(new Random().nextLong(), name, new Random().nextBoolean() + "");
//    }
}
