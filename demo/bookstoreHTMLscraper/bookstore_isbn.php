<?php
// Searches the bookstore via ISBN
$baseURL = "https://fortuna.uwaterloo.ca/cgi-bin/cgiwrap/rsic/book/search.html?mv_profile=search_advanced";

// Scrapes the HTML, returning book information
function getCourseInfo($html, $term) {
	$wantNextLine = false;
	$courses = array();

	// Make sure that it found some books
	
	foreach (preg_split("/((\r?\n)|(\r\n?))/", $html) as $line) {
		$str = trim($line, " ");
		if (strpos($str, 'Sorry, no matches for') !== false) {
			echo "FOUND!\n".$str."\n";
			return;
		}
	}
	

	foreach (preg_split("/((\r?\n)|(\r\n?))/", $html) as $line) {
                $str = strip_tags(trim($line, " "));
		if ($wantNextLine == 1) {
			$course = substr($str, 0, strrpos($str, " "));
			
			// Only add course if it doesn't already exist in the array
			if (! in_array($course, $courses)) {
				array_push($courses, $course);
			}
			
			$wantNextLine = 0;
		 }

                if (strpos($str, $term) !== false) {
			$wantNextLine = 1;
                }
        }
	return $courses;
}

// Returns the HTML of the bookstore query
function getHTML($url) {
	$response = file_get_contents($url);
	
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
$filteredResult = "";
$title = "";
$author = "";

$ISBN = "9780262033848";	// FINDS COURSES
//$ISBN = "9780262043848";	// DOESN'T FIND COURSES

$param1 = "&mv_searchspec=";		// Title
$param2 = "&mv_searchspec=";		// Author
$param3 = "&mv_searchspec=".$ISBN;	// ISBN
$URL = $baseURL.$param1.$param2.$param3;

echo "Get HTML for ".$ISBN."\n";
$html = getHTML($URL);
echo "Get HTML success\n";
$results = json_encode(getCourseInfo($html, "(1139)"));
echo $results;
	
?>
