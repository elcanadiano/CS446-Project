<?php

$baseURL = "https://api.uwaterloo.ca/v2";	// Current version of Open Data API
$format = ".json";							// .json OR .xml format can be returned
$key = "dacd769dd10cb9e4b195989a2be137c8";	// EasyPeasy@gmail.com Open Data API key

// Returns the JSON result of the Open Data API query
function getJSONdata($call) {
	$properURL = $GLOBALS['baseURL'].$call.$GLOBALS['format']."?key=".$GLOBALS['key'];
	
	$response = file_get_contents($properURL);
	$decodedResponse = json_decode($response, true);
	
	return $decodedResponse;
}

// Scrape the response from the UWaterloo Open Data API and return the array
// This ensures unique course entries for a given subject and term
function scrapeCourseInfo($decodedResponse) {
	$array = array();

	foreach ($decodedResponse["data"] as $info => $value) {
		$item = $value["subject"]." ".$value["catalog_number"]." - ".$value["title"];
		
		if (! in_array($item, $array)) {
			array_push($array, $item);
		}
	}
	return $array;
}

// Scrape the subjects returned from the UWaterloo Open Data API and returns an array
// Ensures unique entries
function scrapeSubjects($decodedResponse) {
	$array = array();
	
	foreach ($decodedResponse["data"] as $info => $value) {
		$item = $value["subject"];
	
		if (! in_array($item, $array)) {
			array_push($array, $item);
		}
	}
	
	return $array;
}

// Stores the information from the array (scraped) into a file
function outputter($array, $filePath) {
	$filteredResult =  "";
	
	foreach ($array as $item) {
		$filteredResult .= "$item\n";
	}
	file_put_contents($filePath, $filteredResult);
}

$subjects=array("ACC","ACTSC","AFM","AHS","AMATH","ANTH","APPLS","ARBUS",
"ARCH","ARTS","BE","BET","BIOL","BUS","CHE","CHEM","CHINA","CIVE","CLAS","CM",
"CMW","CO","COGSCI","COMM","CROAT","CS","CT","DAC","DEI","DRAMA","DUTCH",
"EARTH","EASIA","ECE","ECON","ENBUS","ENGL","ENVE","ENVS","ERS","ESL","FINE",
"FR","GBDA","GEMCC","GENE","GEOE","GEOG","GER","GERON","GGOV","GRK","HIST",
"HLTH","HRM","HSG","HUMSC","IAIN","INDEV","INTEG","INTST","ISS","ITAL",
"ITALST","JAPAN","JS","KIN","KOREA","LAT","LED","LS","MATBUS","MATH","MCT",
"ME","MEDVL","MNS","MSCI","MTE","MTHEL","MUSIC","NANO","NE","OPTOM","PACS",
"PHARM","PHIL","PHS","PHYS","PLAN","PMATH","PORT","PS","PSCI","PSYCH","REC",
"REES","RS","RUSS","SCBUS","SCI","SDS","SE","SI","SMF","SOC","SOCWK","SPAN",
"SPCOM","SPD","STAT","STV","SUSM","SWK","SWREN","SYDE","TOUR","TS","UNIV",
"VCULT","WS");

$terms = array(1121, 1125, 1129, 1131, 1135, 1139, 1141, 1145, 1149);

// Iterate through each term and subject
foreach ($terms as $term) {
	foreach ($subjects as $subject) {
		$filePath = "results/".$term."_".$subject;
		$call =  "/terms/$term/$subject/schedule";

		$decodedResponse = getJSONdata($call);
	
		$array = (scrapeCourseInfo($decodedResponse));
		outputter($array,$filePath);
	}
}

?>
