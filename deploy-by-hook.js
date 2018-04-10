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
const incomingDirectory = properties.get('settings.incomingDirectory');
const configDirectory = properties.get('settings.configDirectory');
const targetDirectory = properties.get('settings.targetDirectory');
const configServer = properties.get('webservice.configServer');
const stagingServer = properties.get('staging.serverName');
const stagingServerUrl = properties.get('staging.serverUrl');
const stagingGroupsSocketUrl = properties.get('staging.groupsSocketUrl');
const productionServer = properties.get('production.serverName');
const productionServerUrl = properties.get('production.serverUrl');
const productionGroupsSocketUrl = properties.get('production.groupsSocketUrl');

var resultsFile = fs.createWriteStream(targetDirectory + "/index.html", {flags: 'w', mode: 0o755})
var updatesFile = fs.createWriteStream(targetDirectory + "/updates.js", {flags: 'a', mode: 0o755})

function startResult() {
    resultsFile.write("<style>table, th, td {border: 1px solid #d4d4d4; border-spacing: 0px;}</style>\n");
    resultsFile.write("<div id='buildLabel'>Building...</div>\n");
    resultsFile.write("<div id='buildDate'></div>\n");
    resultsFile.write("<table id='buildTable'>\n");
    resultsFile.write("<tr><td>experiment</td><td>last update</td><td>staging web</td><td>staging android</td><td>staging desktop</td><td>staging admin</td><td>production web</td><td>production android</td><td>production desktop</td><td>production admin</td><tr>\n");
//    for (let currentEntry of listing) {
//        resultsFile.write("<tr>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_experiment'>" + currentEntry.buildName + "</td>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_date'>queued</td>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_staging_web'/>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_staging_android'/>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_staging_desktop'/>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_staging_admin'/>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_production_web'/>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_production_android'/>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_production_desktop'/>");
//        resultsFile.write("<td id='" + currentEntry.buildName + "_production_admin'/>");
//        resultsFile.write("</tr>");
//    }
    resultsFile.write("</table>\n");
    resultsFile.write("<script>\n");
    resultsFile.write("function doUpdate() {\n");
//    resultsFile.write("<script  type='text/javascript' id='updateScript' src='updates.js'/>");
    resultsFile.write("var headTag = document.getElementsByTagName('head')[0];\n");
    resultsFile.write("var updateScriptTag = document.getElementById('updateScript');\n");
    resultsFile.write("if (updateScriptTag) headTag.removeChild(updateScriptTag);\n");
    resultsFile.write("var scriptTag = document.createElement('script');\n");
    resultsFile.write("scriptTag.type = 'text/javascript';\n");
    resultsFile.write("scriptTag.id = 'updateScript';\n");
    resultsFile.write("scriptTag.src = 'updates.js?date='+ new Date().getTime();\n");
    resultsFile.write("headTag.appendChild(scriptTag);\n");
//    resultsFile.write("document.getElementById('updateScript').src = 'updates.js?date='+ new Date().getTime();\n");
    resultsFile.write("}\n");
    resultsFile.write("var updateTimer = window.setTimeout(doUpdate, 1000);\n");
    resultsFile.write("var headTag = document.getElementsByTagName('head')[0];\n");
    resultsFile.write("var scriptTag = document.createElement('script');\n");
    resultsFile.write("scriptTag.type = 'text/javascript';\n");
    resultsFile.write("scriptTag.id = 'updateScript';\n");
    resultsFile.write("scriptTag.src = 'updates.js?date='+ new Date().getTime();\n");
    resultsFile.write("headTag.appendChild(scriptTag);\n");
    resultsFile.write("</script>\n");
    updatesFile.write("updateTimer = window.setTimeout(doUpdate, 1000);\n");
}


function storeIsQueued(name) {
    updatesFile.write("var experimentRow = document.getElementById('" + name + "_row');\n");
    updatesFile.write("if (experimentRow) document.getElementById('buildTable').removeChild(experimentRow);\n");
    updatesFile.write("var tableRow = document.createElement('tr');\n");
    updatesFile.write("tableRow.id = '" + name + "_row';\n");
    updatesFile.write("document.getElementById('buildTable').appendChild(tableRow);\n");
    for (var column of ["_experiment", "_date", "_staging_web", "_staging_android", "_staging_desktop", "_staging_admin", "_production_web", "_production_android", "_production_desktop", "_production_admin"]) {
        updatesFile.write("var tableCell = document.createElement('td');\n");
        updatesFile.write("tableCell.id = '" + name + column + "';\n");
        updatesFile.write("tableRow.appendChild(tableCell);\n");
    }
    updatesFile.write("document.getElementById('" + name + "_experiment').innerHTML = '" + name + "';\n");
    updatesFile.write("document.getElementById('" + name + "_date').innerHTML = 'queued';\n");
}

function storeResult(name, message, stage, type, isError, isBuilding) {
    updatesFile.write("document.getElementById('buildLabel').innerHTML = 'Building " + name + "';\n");
    updatesFile.write("document.getElementById('buildDate').innerHTML = '" + new Date().toISOString() + "';\n");
    updatesFile.write("document.getElementById('" + name + "_" + stage + "_" + type + "').innerHTML = '" + message + "';\n");
    updatesFile.write("document.getElementById('" + name + "_date').innerHTML = '" + new Date().toISOString() + "';\n");
    if (isError) {
        updatesFile.write("document.getElementById('" + name + "_" + stage + "_" + type + "').style='background: #F3C3C3';\n");
    } else if (isBuilding) {
        updatesFile.write("document.getElementById('" + name + "_" + stage + "_" + type + "').style='background: #C3C3F3';\n");
    } else {
        updatesFile.write("document.getElementById('" + name + "_" + stage + "_" + type + "').style='background: #C3F3C3';\n");
    }
}

function stopUpdatingResults() {
    updatesFile.write("document.getElementById('buildLabel').innerHTML = 'Build process complete';\n");
    updatesFile.write("document.getElementById('buildDate').innerHTML = '" + new Date().toISOString() + "';\n");
    updatesFile.write("window.clearTimeout(updateTimer);\n");
}


function deployStagingGui(listing, currentEntry) {
    // we create a new mvn instance for each child pom
    var mvngui = require('maven').create({
        cwd: __dirname + "/gwt-cordova",
        settings: m2Settings
    });
    var mavenLog = fs.createWriteStream(targetDirectory + "/" + currentEntry.buildName + "_staging.txt", {mode: 0o755});
    process.stdout.write = process.stderr.write = mavenLog.write.bind(mavenLog);
    storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_staging.txt">building</a>', "staging", "web", false, true);
    mvngui.execute(['clean', 'install'], {
//    mvngui.execute(['clean', 'gwt:run'], {
        'skipTests': true, '-pl': 'frinex-gui',
        'experiment.configuration.name': currentEntry.buildName,
        'experiment.configuration.displayName': currentEntry.experimentDisplayName,
        'experiment.webservice': configServer,
        'experiment.configuration.path': configDirectory,
        'versionCheck.allowSnapshots': 'false',
        'versionCheck.buildType': 'stable',
        'experiment.destinationServer': stagingServer,
        'experiment.destinationServerUrl': stagingServerUrl,
        'experiment.groupsSocketUrl': stagingGroupsSocketUrl,
        'experiment.isScaleable': currentEntry.isScaleable,
        'experiment.defaultScale': currentEntry.defaultScale
//                    'experiment.scriptSrcUrl': stagingServerUrl,
//                    'experiment.staticFilesUrl': stagingServerUrl
    }).then(function (value) {
        console.log("frinex-gui finished");
        storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_staging.war">download</a>&nbsp;<a href="http://ems13.mpi.nl/' + currentEntry.buildName + '">browse</a>', "staging", "web", false, false);
//        var successFile = fs.createWriteStream(targetDirectory + "/" + currentEntry.buildName + "_staging.html", {flags: 'w'});
//        successFile.write(currentEntry.experimentDisplayName + ": " + JSON.stringify(value, null, 4));
//        console.log(targetDirectory);
//        console.log(value);
        // build cordova 
        buildApk(currentEntry.buildName, "staging");
        buildElectron(currentEntry.buildName, "staging");
        deployStagingAdmin(listing, currentEntry);
//        buildNextExperiment(listing);
    }, function (reason) {
//        console.log(targetDirectory);
//        console.log(JSON.stringify(reason, null, 4));
        console.log("frinex-gui staging failed");
        console.log(currentEntry.experimentDisplayName);
        storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_staging.txt">failed</a>', "staging", "web", true, false);
//        var errorFile = fs.createWriteStream(targetDirectory + "/" + currentEntry.buildName + "_staging.html", {flags: 'w'});
//        errorFile.write(currentEntry.experimentDisplayName + ": " + JSON.stringify(reason, null, 4));
        buildNextExperiment(listing);
    });
}
function deployStagingAdmin(listing, currentEntry) {
    var mvnadmin = require('maven').create({
        cwd: __dirname + "/registration",
        settings: m2Settings
    });
    var mavenLog = fs.createWriteStream(targetDirectory + "/" + currentEntry.buildName + "_staging_admin.txt", {mode: 0o755});
    process.stdout.write = process.stderr.write = mavenLog.write.bind(mavenLog);
    storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_staging_admin.txt">building</a>', "staging", "admin", false, true);
    mvnadmin.execute(['clean', 'install'], {
        'skipTests': true, '-pl': 'frinex-admin',
        'experiment.configuration.name': currentEntry.buildName,
        'experiment.configuration.displayName': currentEntry.experimentDisplayName,
        'experiment.webservice': configServer,
        'experiment.configuration.path': configDirectory,
        'versionCheck.allowSnapshots': 'false',
        'versionCheck.buildType': 'stable',
        'experiment.destinationServer': stagingServer,
        'experiment.destinationServerUrl': stagingServerUrl
    }).then(function (value) {
        console.log(value);
//                        fs.createReadStream(__dirname + "/registration/target/"+currentEntry.buildName+"-frinex-admin-0.1.50-testing.war").pipe(fs.createWriteStream(currentEntry.buildName+"-frinex-admin-0.1.50-testing.war"));
        console.log("frinex-admin finished");
//        storeResult(currentEntry.buildName, "deployed", "staging", "admin", false, false);
        storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_staging_admin.war">download</a>&nbsp;<a href="http://ems13.mpi.nl/' + currentEntry.buildName + '-admin">browse</a>', "staging", "admin", false, false);
        deployProductionGui(listing, currentEntry);
//        buildNextExperiment(listing);
    }, function (reason) {
        console.log(reason);
        console.log("frinex-admin staging failed");
        console.log(currentEntry.experimentDisplayName);
//        storeResult(currentEntry.buildName, "failed", "staging", "admin", true, false);
        storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_staging_admin.txt">failed</a>', "staging", "admin", true, false);
        buildNextExperiment(listing);
    });
}
function deployProductionGui(listing, currentEntry) {
    console.log(productionServerUrl + '/' + currentEntry.buildName);
    storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_production.txt">building</a>', "production", "web", false, true);
    http.get(productionServerUrl + '/' + currentEntry.buildName, function (response) {
        if (response.statusCode !== 404) {
            console.log("existing frinex-gui production found, aborting build!");
            console.log(response.statusCode);
            storeResult(currentEntry.buildName, "existing production found, aborting build!", "production", "web", true, false);
            buildNextExperiment(listing);
        } else {
            console.log(response.statusCode);
            var mvngui = require('maven').create({
                cwd: __dirname + "/gwt-cordova",
                settings: m2Settings
            });
            var mavenLog = fs.createWriteStream(targetDirectory + "/" + currentEntry.buildName + "_production.txt", {mode: 0o755});
            process.stdout.write = process.stderr.write = mavenLog.write.bind(mavenLog);
            mvngui.execute(['clean', 'install'], {
                'skipTests': true, '-pl': 'frinex-gui',
//                    'altDeploymentRepository.snapshot-repo.default.file': '~/Desktop/FrinexAPKs/',
//                    'altDeploymentRepository': 'default:file:file://~/Desktop/FrinexAPKs/',
//                            'altDeploymentRepository': 'snapshot-repo::default::file:./FrinexWARs/',
//                    'maven.repo.local': '~/Desktop/FrinexAPKs/',
                'experiment.configuration.name': currentEntry.buildName,
                'experiment.configuration.displayName': currentEntry.experimentDisplayName,
                'experiment.webservice': configServer,
                'experiment.configuration.path': configDirectory,
                'versionCheck.allowSnapshots': 'false',
                'versionCheck.buildType': 'stable',
                'experiment.destinationServer': productionServer,
                'experiment.destinationServerUrl': productionServerUrl,
                'experiment.groupsSocketUrl': productionGroupsSocketUrl,
                'experiment.isScaleable': currentEntry.isScaleable,
                'experiment.defaultScale': currentEntry.defaultScale
//                            'experiment.scriptSrcUrl': productionServerUrl,
//                            'experiment.staticFilesUrl': productionServerUrl
            }).then(function (value) {
                console.log("frinex-gui production finished");
                storeResult(currentEntry.buildName, "skipped", "production", "web", false, false);
//                storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_production.war">download</a>&nbsp;<a href="http://ems12.mpi.nl/' + currentEntry.buildName + '">browse</a>', "production", "web", false, false);
                buildApk(currentEntry.buildName, "production");
                buildElectron(currentEntry.buildName, "production");
                deployProductionAdmin(listing, currentEntry);
//                buildNextExperiment(listing);
            }, function (reason) {
                console.log(reason);
                console.log("frinex-gui production failed");
                console.log(currentEntry.experimentDisplayName);
//                storeResult(currentEntry.buildName, "failed", "production", "web", true, false);
                storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_production.txt">failed</a>', "production", "web", true, false);
                buildNextExperiment(listing);
            });
        }
    });
}
function deployProductionAdmin(listing, currentEntry) {
    var mvnadmin = require('maven').create({
        cwd: __dirname + "/registration",
        settings: m2Settings
    });
    var mavenLog = fs.createWriteStream(targetDirectory + "/" + currentEntry.buildName + "_production_admin.txt", {mode: 0o755});
    process.stdout.write = process.stderr.write = mavenLog.write.bind(mavenLog);
    storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_production_admin.txt">building</a>', "production", "admin", false, true);
    mvnadmin.execute(['clean', 'install'], {
        'skipTests': true, '-pl': 'frinex-admin',
//                                'altDeploymentRepository': 'snapshot-repo::default::file:./FrinexWARs/',
        'experiment.configuration.name': currentEntry.buildName,
        'experiment.configuration.displayName': currentEntry.experimentDisplayName,
        'experiment.webservice': configServer,
        'experiment.configuration.path': configDirectory,
        'versionCheck.allowSnapshots': 'false',
        'versionCheck.buildType': 'stable',
        'experiment.destinationServer': productionServer,
        'experiment.destinationServerUrl': productionServerUrl
    }).then(function (value) {
//        console.log(value);
//                        fs.createReadStream(__dirname + "/registration/target/"+currentEntry.buildName+"-frinex-admin-0.1.50-testing.war").pipe(fs.createWriteStream(currentEntry.buildName+"-frinex-admin-0.1.50-testing.war"));
        console.log("frinex-admin production finished");
        storeResult(currentEntry.buildName, "skipped", "production", "admin", false, false);
//        storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_production_admin.war">download</a>&nbsp;<a href="http://ems12.mpi.nl/' + currentEntry.buildName + '-admin">browse</a>', "production", "admin", false, false);
        buildNextExperiment(listing);
    }, function (reason) {
        console.log(reason);
        console.log("frinex-admin production failed");
        console.log(currentEntry.experimentDisplayName);
//        storeResult(currentEntry.buildName, "failed", "production", "admin", true, false);
        storeResult(currentEntry.buildName, '<a href="' + currentEntry.buildName + '_production_admin.txt">failed</a>', "production", "admin", true, false);
        buildNextExperiment(listing);
    });
}

function buildApk(buildName, stage) {
    console.log("starting cordova build");
    storeResult(buildName, "building", stage, "android", false, true);
//    execSync('bash gwt-cordova/target/setup-cordova.sh');
    console.log("build cordova finished");
    storeResult(buildName, "skipped", stage, "android", false, false);
}

function buildElectron(buildName, stage) {
    console.log("starting electron build");
    storeResult(buildName, "building", stage, "desktop", false, true);
//    execSync('bash gwt-cordova/target/setup-electron.sh');
    console.log("build electron finished");
    storeResult(buildName, "skipped", stage, "desktop", false, false);
}

function buildNextExperiment(listing) {
    if (listing.length > 0) {
        var currentEntry = listing.pop();
//        console.log(currentEntry);
//                console.log("starting generate stimulus");
//                execSync('bash gwt-cordova/target/generated-sources/bash/generateStimulus.sh');
        deployStagingGui(listing, currentEntry);
    } else {
        console.log("build process from listing completed");
        stopUpdatingResults();
    }
}

function convertJsonToXml() {
    resultsFile.write("<div>Converting JSON to XML, '" + new Date().toISOString() + "'</div>");
    var mvnConvert = require('maven').create({
        cwd: __dirname + "/ExperimentDesigner",
        settings: m2Settings
    });
    mvnConvert.execute(['clean', 'package', 'exec:exec'], {
        'skipTests': true,
        'exec.executable': 'java',
        'exec.classpathScope': 'runtime',
        'exec.args': '-classpath %classpath nl.mpi.tg.eg.experimentdesigner.util.JsonToXml ' + incomingDirectory + ' ' + incomingDirectory
    }).then(function (value) {
        console.log("convert JSON to XML finished");
        resultsFile.write("<div>Conversion from JSON to XML finished, '" + new Date().toISOString() + "'</div>");
        fs.readdir(incomingDirectory, function (error, list) {
            if (error) {
                console.error(error);
            } else {
                var remainingFiles = list.length;
                list.forEach(function (filename) {
                    incomingFile = path.resolve(incomingDirectory, filename);
                    if (path.extname(filename) === ".json") {
                        fs.unlinkSync(incomingFile);
                    } else if (path.extname(filename) === ".xml") {
                        filename = path.resolve(configDirectory, filename);
                        fs.rename(incomingFile, filename, (error) => {
                            if (error) {
                                throw error;
                            }
                            console.log('moved incoming: ' + filename);
                        });
                    }
                    remainingFiles--;
                    if (remainingFiles <= 0) {
                        buildFromListing();
                    }
                });
            }
        });
    }, function (reason) {
        console.log(reason);
        console.log("convert JSON to XML failed");
        resultsFile.write("<div>Conversion from JSON to XML failed, '" + new Date().toISOString() + "'</div>");
    });
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
                    storeIsQueued(path.parse(filename).name);
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

convertJsonToXml();