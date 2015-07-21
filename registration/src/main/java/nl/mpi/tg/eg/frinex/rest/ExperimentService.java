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

import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import nl.mpi.tg.eg.frinex.model.DataSubmissionResult;
import nl.mpi.tg.eg.frinex.model.ExperimentData;
import nl.mpi.tg.eg.frinex.model.Participant;
import nl.mpi.tg.eg.frinex.model.ScreenData;
import nl.mpi.tg.eg.frinex.model.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @Autowired
    TimeStampRepository timeStampRepository;
    @Autowired
    ParticipantRepository participantRepository;

    @RequestMapping("/experiment/{name}")
    public String test(@PathVariable String name) {
        return "Experiment: " + name;
    }

//    @RequestMapping(value = "/registerUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseEntity<String> registerUserData(
//            @RequestHeader("Accept-Language") String acceptLang,
//            @RequestHeader("User-Agent") String userAgent,
//            HttpServletRequest request,
//            @RequestBody Participant participant) {
//        System.out.println(request.getRemoteAddr());
//        System.out.println(acceptLang);
//        System.out.println(userAgent);
//        final ResponseEntity<String> responseEntity = new ResponseEntity<>(request.getRemoteAddr() + "<br>" + acceptLang + "<br>" + userAgent, HttpStatus.OK);
//        return responseEntity;
//    }
//    @RequestMapping(value = "/getScreenChanges", method = RequestMethod.GET, produces = "text/csv")
//    public @ResponseBody
//    ResponseEntity<List<ScreenData>> getScreenChanges() {
//               CsvMapper mapper = new CsvMapper();
//        CsvSchema schema = mapper.schemaFor(ScreenData.class);
//
//        ObjectWriter writer = mapper.writer(schema.withLineSeparator("\n"));
//
//        File greeterCSV = new File("greeterCSV.csv");
//
//        try {
//            writer.writeValue(greeterCSV, greeter);
//        } catch (JsonGenerationException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(screenDataRepository.findAll(), HttpStatus.OK);
//    }
    @RequestMapping(value = "/screenChange", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<DataSubmissionResult> registerScreenData(@RequestBody List<ScreenData> screenDataList) {
//        ArrayList<ScreenData> invalidScreenData = new ArrayList<>();
        for (ScreenData screenData : screenDataList) {
            if (screenData.getExperimentName() == null || screenData.getExperimentName().isEmpty()) {
                System.out.println("The 'ExperimentName' parameter is required");
//                invalidScreenData.add(screenData);
            }
            if (screenData.getScreenName() == null || screenData.getScreenName().isEmpty()) {
                System.out.println("The 'ScreenName' parameter is required");
//                invalidScreenData.add(screenData);
            }
            if (screenData.getViewDate() == null) {
                System.out.println("The 'ViewDate' parameter is required");
//                invalidScreenData.add(screenData);
            }
            if (screenData.getSubmitDate() != null) {
                System.out.println("SubmitDate cannot be provided");
//                invalidScreenData.add(screenData);
            }
            screenData.setSubmitDate(new java.util.Date());
            screenDataRepository.save(screenData);
        }
        final ResponseEntity responseEntity;
        if (screenDataList.isEmpty()) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            responseEntity = new ResponseEntity<>(new DataSubmissionResult(screenDataList.get(0).getUserId(), true), HttpStatus.OK);
        }
//        } else {
//            responseEntity = new ResponseEntity<>(new DataSubmissionResult(null, true, invalidScreenData), HttpStatus.MULTI_STATUS);
//        }
        return responseEntity;
    }

    @RequestMapping(value = "/timeStamp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<DataSubmissionResult> registerTimeStamp(@RequestBody List<TimeStamp> timeStampList) {
        final ResponseEntity responseEntity;
        if (timeStampList.isEmpty()) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            for (TimeStamp timeStamp : timeStampList) {
                timeStamp.setSubmitDate(new java.util.Date());
                timeStampRepository.save(timeStamp);
            }
            responseEntity = new ResponseEntity<>(new DataSubmissionResult(timeStampList.get(0).getUserId(), true), HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/metadata", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<DataSubmissionResult> storeMetadataData(@RequestBody Participant participant) {
        participant.setSubmitDate(new java.util.Date());
        participantRepository.save(participant);
        return new ResponseEntity<>(new DataSubmissionResult(participant.getUserId(), true), HttpStatus.OK);
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
