#!/usr/bin/env node
/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics
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


/**
 * @since April 3, 2018 10:40 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */

/*
 * This script publishes FRINEX experiments that are found in the configuration GIT repository after being triggered by a GIT hooks.
 * 
 * Prerequisites for this script:
 *        npm install request
 *        npm install maven
 *        npm install properties-reader
 */

const PropertiesReader = require('properties-reader');
const properties = PropertiesReader('publish.properties');
const request = require('request');
const execSync = require('child_process').execSync;
const http = require('http');
const fs = require('fs');
const path = require('path');
const m2Settings = properties.get('settings.m2Settings');
const configDirectory = properties.get('settings.configDirectory');
const buildResultsFile = properties.get('settings.buildResultsFile');
const configServer = properties.get('webservice.configServer');
const stagingServer = properties.get('staging.serverName');
const stagingServerUrl = properties.get('staging.serverUrl');
const stagingGroupsSocketUrl = properties.get('staging.groupsSocketUrl');
const productionServer = properties.get('production.serverName');
const productionServerUrl = properties.get('production.serverUrl');
const productionGroupsSocketUrl = properties.get('production.groupsSocketUrl');

var resultsFile = fs.createWriteStream(buildResultsFile, {flags: 'w'})

function startResult(listing) {
    resultsFile.write("<style>table, th, td {border: 1px solid #d4d4d4; border-spacing: 0px;}</style>");
    resultsFile.write("<div id='_buildNextExperiment'>Building...</div>");
    resultsFile.write("<table>");
    resultsFile.write("<tr><td>experiment</td><td>last update</td><td>web</td><td>android</td><td>desktop</td><td>admin</td><tr>");
    for (let currentEntry of listing) {
        resultsFile.write("<tr>");
        resultsFile.write("<td id='" + currentEntry.buildName + "_experiment'>" + currentEntry.buildName + "</td>");
        resultsFile.write("<td id='" + currentEntry.buildName + "_date'/>");
        resultsFile.write("<td id='" + currentEntry.buildName + "_web'/>");
        resultsFile.write("<td id='" + currentEntry.buildName + "_android'/>");
        resultsFile.write("<td id='" + currentEntry.buildName + "_desktop'/>");
        resultsFile.write("<td id='" + currentEntry.buildName + "_admin'/>");
        resultsFile.write("</tr>");
    }
    resultsFile.write("</table>");
}


function storeResult(name, message, stage, isError) {
    resultsFile.write("<script>");
    resultsFile.write("document.getElementById('" + name + "_" + stage + "').innerHTML = '" + message + "';");
    resultsFile.write("document.getElementById('" + name + "_date').innerHTML = '" + new Date().toISOString() + "';");
    if (isError) {
        resultsFile.write("document.getElementById('" + name + "_" + stage + "').style='background: #F3C3C3';");
    } else {
        resultsFile.write("document.getElementById('" + name + "_" + stage + "').style='background: #C3F3C3';");
    }
    resultsFile.write("</script>");
}

function deployStagingGui(listing, currentEntry) {
    // we create a new mvn instance for each child pom
    var mvngui = require('maven').create({
        cwd: __dirname + "/gwt-cordova",
        settings: m2Settings
    });
    mvngui.execute(['clean', 'install'], {
//    mvngui.execute(['clean', 'gwt:run'], {
        'skipTests': true, '-pl': 'frinex-gui',
        'experiment.configuration.name': currentEntry.buildName,
        'experiment.configuration.displayName': currentEntry.experimentDisplayName,
        'experiment.webservice': configServer,
        'experiment.configuration.path': configDirectory,
        'experiment.destinationServer': stagingServer,
        'experiment.destinationServerUrl': stagingServerUrl,
        'experiment.groupsSocketUrl': stagingGroupsSocketUrl,
        'experiment.isScaleable': currentEntry.isScaleable,
        'experiment.defaultScale': currentEntry.defaultScale
//                    'experiment.scriptSrcUrl': stagingServerUrl,
//                    'experiment.staticFilesUrl': stagingServerUrl
    }).then(function (value) {
        console.log("frinex-gui finished");
        storeResult(currentEntry.buildName, "frinex-gui finished", "web", false);
        // build cordova 
//                    buildApk();
//                    console.log("buildApk finished");
//        buildElectron();
//        deployStagingAdmin(listing, currentEntry);
        buildNextExperiment(listing);
    }, function (reason) {
        console.log(reason);
        console.log("frinex-gui staging failed");
        console.log(currentEntry.experimentDisplayName);
        storeResult(currentEntry.buildName, "frinex-gui failed", "web", true);
        buildNextExperiment(listing);
    });
}
function deployStagingAdmin(listing, currentEntry) {
    var mvnadmin = require('maven').create({
        cwd: __dirname + "/registration",
        settings: m2Settings
    });
    mvnadmin.execute(['clean', 'install'], {
        'skipTests': true, '-pl': 'frinex-admin',
        'experiment.configuration.name': currentEntry.buildName,
        'experiment.configuration.displayName': currentEntry.experimentDisplayName,
        'experiment.webservice': configServer,
        'experiment.configuration.path': configDirectory,
        'experiment.destinationServer': stagingServer,
        'experiment.destinationServerUrl': stagingServerUrl
    }).then(function (value) {
        console.log(value);
//                        fs.createReadStream(__dirname + "/registration/target/"+currentEntry.buildName+"-frinex-admin-0.1.50-testing.war").pipe(fs.createWriteStream(currentEntry.buildName+"-frinex-admin-0.1.50-testing.war"));
        console.log("frinex-admin finished");
        storeResult(currentEntry.buildName, "frinex-admin finished", "admin", false);
//        deployProductionGui(listing, currentEntry);
        buildNextExperiment(listing);
    }, function (reason) {
        console.log(reason);
        console.log("frinex-admin staging failed");
        console.log(currentEntry.experimentDisplayName);
        storeResult(currentEntry.buildName, "frinex-admin failed", "admin", true);
//                        buildNextExperiment(listing);
    });
}
function deployProductionGui(listing, currentEntry) {
    console.log(productionServerUrl + '/' + currentEntry.buildName);
    http.get(productionServerUrl + '/' + currentEntry.buildName, function (response) {
        if (response.statusCode !== 404) {
            console.log("existing frinex-gui production found, aborting build!");
            console.log(response.statusCode);
            storeResult(currentEntry.buildName, "existing frinex-gui production found, aborting build!", "production_web", true);
        } else {
            console.log(response.statusCode);
            var mvngui = require('maven').create({
                cwd: __dirname + "/gwt-cordova",
                settings: m2Settings
            });
            mvngui.execute(['clean', 'tomcat7:deploy'/*, 'gwt:run'*/], {
                'skipTests': true, '-pl': 'frinex-gui',
//                    'altDeploymentRepository.snapshot-repo.default.file': '~/Desktop/FrinexAPKs/',
//                    'altDeploymentRepository': 'default:file:file://~/Desktop/FrinexAPKs/',
//                            'altDeploymentRepository': 'snapshot-repo::default::file:./FrinexWARs/',
//                    'maven.repo.local': '~/Desktop/FrinexAPKs/',
                'experiment.configuration.name': currentEntry.buildName,
                'experiment.configuration.displayName': currentEntry.experimentDisplayName,
                'experiment.webservice': configServer,
                'experiment.configuration.path': configDirectory,
                'experiment.destinationServer': productionServer,
                'experiment.destinationServerUrl': productionServerUrl,
                'experiment.groupsSocketUrl': productionGroupsSocketUrl,
                'experiment.isScaleable': currentEntry.isScaleable,
                'experiment.defaultScale': currentEntry.defaultScale
//                            'experiment.scriptSrcUrl': productionServerUrl,
//                            'experiment.staticFilesUrl': productionServerUrl
            }).then(function (value) {
                console.log("frinex-gui production finished");
                storeResult(currentEntry.buildName, "frinex-gui production finished", "production_web", false);
//                deployProductionAdmin(listing, currentEntry);
                buildNextExperiment(listing);
            }, function (reason) {
                console.log(reason);
                console.log("frinex-gui production failed");
                console.log(currentEntry.experimentDisplayName);
                storeResult(currentEntry.buildName, "frinex-gui production failed", "production_web", true);
//                            buildNextExperiment(listing);
            });
        }
    });
}
function deployProductionAdmin(listing, currentEntry) {
    var mvnadmin = require('maven').create({
        cwd: __dirname + "/registration",
        settings: m2Settings
    });
    mvnadmin.execute(['clean', 'tomcat7:deploy'], {
        'skipTests': true, '-pl': 'frinex-admin',
//                                'altDeploymentRepository': 'snapshot-repo::default::file:./FrinexWARs/',
        'experiment.configuration.name': currentEntry.buildName,
        'experiment.configuration.displayName': currentEntry.experimentDisplayName,
        'experiment.webservice': configServer,
        'experiment.configuration.path': configDirectory,
        'experiment.destinationServer': productionServer,
        'experiment.destinationServerUrl': productionServerUrl
    }).then(function (value) {
//        console.log(value);
//                        fs.createReadStream(__dirname + "/registration/target/"+currentEntry.buildName+"-frinex-admin-0.1.50-testing.war").pipe(fs.createWriteStream(currentEntry.buildName+"-frinex-admin-0.1.50-testing.war"));
        console.log("frinex-admin production finished");
        storeResult(currentEntry.buildName, "frinex-admin production finished", "production_admin", false);
        buildNextExperiment(listing);
    }, function (reason) {
        console.log(reason);
        console.log("frinex-admin production failed");
        console.log(currentEntry.experimentDisplayName);
        storeResult(currentEntry.buildName, "frinex-admin production failed", "production_admin", true);
//                                buildNextExperiment(listing);
    });
}

function buildApk(buildName) {
    console.log("starting cordova build");
    execSync('bash gwt-cordova/target/setup-cordova.sh');
    console.log("build cordova finished");
    storeResult(buildName, "build cordova finished", "android", false);
}

function buildElectron(buildName) {
    console.log("starting electron build");
    execSync('bash gwt-cordova/target/setup-electron.sh');
    console.log("build electron finished");
    storeResult(buildName, "build electron finished", "desktop", false);
}

function buildNextExperiment(listing) {
    if (listing.length > 0) {
        var currentEntry = listing.pop();
        console.log(currentEntry);
//                console.log("starting generate stimulus");
//                execSync('bash gwt-cordova/target/generated-sources/bash/generateStimulus.sh');
        deployStagingGui(listing, currentEntry);
    } else {
        console.log("build process from listing completed");
        storeResult("", "build process from listing completed", "buildNextExperiment", false);
    }
}

function buildFromListing() {
    fs.readdir(configDirectory, function (error, list) {
        if (error) {
            console.error(error);
        } else {
            var listing = [];
            var remainingFiles = list.length;

            list.forEach(function (filename) {
                console.log(filename);
                console.log(path.extname(filename));
                if (path.extname(filename) !== ".xml") {
                    remainingFiles--;
                } else {
                    filename = path.resolve(configDirectory, filename);
                    console.log(filename);
                    listing.push({
//                    configPath: path,
                        buildName: path.parse(filename).name,
                        experimentDisplayName: path.parse(filename).name
                    });
                    remainingFiles--;
                    if (remainingFiles <= 0) {
                        console.log(JSON.stringify(listing));
                        startResult(listing);
                        buildNextExperiment(listing);
                    }
                }
            });
        }
    });
}

buildFromListing();