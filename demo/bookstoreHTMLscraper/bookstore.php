<?php
// This is what a proper URL would look like: https://fortuna.uwaterloo.ca/cgi-bin/cgiwrap/rsic/book/search.html?mv_profile=search_course&mv_searchspec=1141&mv_searchspec=CS&mv_searchspec=135&mv_searchspec=&mv_searchspec=

$baseURL = "https://fortuna.uwaterloo.ca/cgi-bin/cgiwrap/rsic/book/search.html?mv_profile=search_course";

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

$terms = array("1141");		// All terms that we want books for
$section = "";
$instructor = "";

// Book object for storing and accessing of Book information
class Book {
	public $author;
	public $title;
	public $price;
	public $isbn;
}

// Scans the files in results folder to get individual course numbers
function getCourseNumbers($term, $subject) {
	$courses = array();
	$fileToRead = "results/".$term."_".$subject;
	echo "Grabbing course numbers from file".$fileToRead."\n";
	$response = file_get_contents($fileToRead);

	foreach (preg_split("/((\r?\n)|(\r\n?))/", $response) as $line) {
		$str = explode("-", $line);
		$subLen = strlen($subject) + 1;
		$course = substr($str[0], $subLen, strlen($str[0]) - $subLen - 1);
	
		if (! strlen($course) == 0) {
			array_push($courses, $course);
		}
	}

	return $courses;
}

// Scrapes the HTML, returning book information
function getBooks($html) {
	
	$books = array();
	$title = "";
	$author = "";
	$price = "";
	$isbn = "";
	
	foreach (preg_split("/((\r?\n)|(\r\n?))/", $html) as $line) {
		$str = trim($line, " ");

		// Find the info that we need and strip unnecessary information
		if (strpos($str, '<span class="author">') != false) {
			$ta = explode(":", strip_tags(preg_replace('/[ ]{2,}|[\t]/', ' ', trim($str))));
			$author = $ta[0];
			$title = $ta[1];
		} else if (strpos($str, '<span class="sku">') != false) {
			$isbn = substr(strip_tags($str), 5);
		} else if (strpos($str, '<span class="price">') != false) {
			$price = substr(strip_tags($str), 7);
		}
		
		if ($author != "" && $title != "" && $price != "" && $isbn != "") {
			$newBook = new Book();
			
			$newBook->author = $author;
			$newBook->title = $title;
			$newBook->price = $price;
			$newBook->isbn = $isbn;

			// Only add book if it doesn't already exist in the array		
			if (! in_array($newBook, $books)) {
				array_push($books, $newBook);
			}

			$author = "";
			$title = "";
			$price = "";
			$isbn = "";
		}		
	}

	return $books;
}

// Returns the HTML of the bookstore query
function getHTML($url, $delay) {
	$response = file_get_contents($url);
	
	if ($response === false) {
		echo "Get HTML failed. Delay for ".$delay. " seconds\n";
		sleep($delay);
		return getHTML($url, 30);
	}
	return $response;
}

// Stores the book information (array) into a file
function output($books, $filePath) {
	$filteredResult = "";
	foreach ($books as $book) {
		$filteredResult .= "$item\n";
	}
	file_put_contents($filePath, $filteredResult);
}

// Iterate through each term and subject
foreach ($terms as $term) {
	foreach ($subjects as $subject) {
		$courses = getCourseNumbers($term, $subject);
		$filteredResult = "";
		$filePath = "books/".$subject."_".$term;	
		
		foreach ($courses as $course) {
			$param1 = "&mv_searchspec=".$term;	// Term that we are searching (e.g. 1141)
			$param2 = "&mv_searchspec=".$subject;	// Subject that we are searching (e.g. CS)
			$param3 = "&mv_searchspec=".$course;	// Course number that we are searching (e.g. 135)
			$param4 = "&mv_searchspec=".$section;	// Optional parameter (e.g. 101)
			$param5 = "&mv_searchspec=".$instructor;// Optional parameter
			$URL = $baseURL.$param1.$param2.$param3.$param4.$param5;

			echo "Get HTML for ".$subject." ".$course."\n";
			$html = getHTML($URL, 420);
			echo "Get HTML success";
			echo "Getting books for ".$subject." ".$course."\n";
			$books = getBooks($html);
			
			foreach ($books as $book) {
				echo "Adding ".$subject." ".$course." book to the list of books\n";
				$filteredResult .= $subject." ".$course.", ";
				$filteredResult .= $book->author.", ";
				$filteredResult .= $book->title.", ";
				$filteredResult .= $book->price.", ";
				$filteredResult .= $book->isbn."\n";
			}
			
		}
		file_put_contents($filePath, $filteredResult);
	}
}	
?>
