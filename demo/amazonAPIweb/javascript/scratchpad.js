function invokeRequest() {
	var accessKeyId = getAccessKeyId();
	var secretAccessKey = getSecretAccessKey();
	var associateTag = getAssociateTag();
	var locale = getLocale();
	var ISBN = getISBN();

	if (accessKeyId == "AWS Access Key ID") {
		alert("Please provide an AWS Access Key ID");
		return;
	}

	if (secretAccessKey == "AWS Secret Access Key") {
		alert("Please provide an AWS Secret Access Key");
		return;
	}

	if (associateTag == "Associate Tag") {
		alert("Please provide an Associate Tag");
		return;
	}

	if (locale == "") {
		alert("Please select a locale");
		return;
	}

	if (ISBN == "") {
		alert("Please enter an ISBN");
		return;
	}

	if (! (ISBN.length == 10 || ISBN.length == 13)) {
		alert("ISBNs must be either 10 or 13 digits");
		return;
	}

	if (! is_numeric(ISBN) ) {
		alert("Only digits are allowed in ISBN");
		return;
	}

	var host;

	if (locale == "CA") {
		host = "webservices.amazon.ca";
	} else if (locale == "CN") {
		host = "webservices.amazon.cn";
	} else if (locale == "DE") {
		host = "webservices.amazon.de";
	} else if (locale == "ES") {
		host = "webservices.amazon.es";
	} else if (locale == "FR") {
		host = "webservices.amazon.fr";
	} else if (locale == "IT") {
		host = "webservices.amazon.it";
	} else if (locale == "JP") {
		host = "webservices.amazon.jp";
	} else if (locale == "UK") {
		host = "webservices.amazon.co.uk";
	} else if (locale == "US") {
		host = "webservices.amazon.com";
	} else {
		alert("Could not resolve locale");
		return;
	}

	query = "Service=AWSECommerceService&Operation=ItemLookup&ResponseGroup=Large&SearchIndex=All&IdType=ISBN&ItemId=" + ISBN;

	// split the query into its constituent parts
	var pairs = query.split("&");

	// remove signature if already there
	// remove access key id if already present and replace with the one user provided above
	// add timestamp if not already present
	pairs = cleanupRequest(pairs);

	// show it
	document.getElementById("NameValuePairs").value = pairs.join("\n");
        
	// encode the name and value in each pair
	pairs = encodeNameValuePairs(pairs);
        
	// sort them and put them back together to get the canonical query string
	pairs.sort();
	document.getElementById("OrderedPairs").value = pairs.join("\n");

	var canonicalQuery = pairs.join("&");
	var stringToSign = "GET\n" + host + "\n/onca/xml\n" + canonicalQuery;

	// calculate the signature
	var secret = getSecretAccessKey();
	var signature = sign(secret, stringToSign);
        
	// assemble the signed url
	var signedUrl = "https://" + host + "/onca/xml?" + canonicalQuery + "&Signature=" + signature;
        
	// update the UI
	var stringToSignArea = document.getElementById("StringToSign");
	stringToSignArea.value = stringToSign;
        
	var signedURLArea = document.getElementById("SignedURL");
	signedURLArea.value = signedUrl;
}

function encodeNameValuePairs(pairs) {
	for (var i = 0; i < pairs.length; i++) {
		var name = "";
		var value = "";        
		var pair = pairs[i];
		var index = pair.indexOf("=");

		// take care of special cases like "&foo&", "&foo=&" and "&=foo&" 
		if (index == -1) {
			name = pair;
		} else if (index == 0) {
			value = pair;
		} else {
			name = pair.substring(0, index);
			if (index < pair.length - 1) {
				value = pair.substring(index + 1);
			}
		}
          
		// decode and encode to make sure we undo any incorrect encoding
		name = encodeURIComponent(decodeURIComponent(name));

		value = value.replace(/\+/g, "%20");
		value = encodeURIComponent(decodeURIComponent(value));

		pairs[i] = name + "=" + value;
	}
	return pairs;
}

function cleanupRequest(pairs) {
	var haveTimestamp = false;
	var haveAwsId = false;
	var haveTag = false;
	var accessKeyId =  getAccessKeyId();
	var associateTag = getAssociateTag();
	var nPairs = pairs.length;
	var i = 0;
	
	while (i < nPairs) {
		var p = pairs[i];
		
		if (p.search(/^Timestamp=/) != -1) {
			haveTimestamp = true;
		} else if (p.search(/^(AWSAccessKeyId|SubscriptionId)=/) != -1) {
			pairs.splice(i, 1, "AWSAccessKeyId=" + accessKeyId);
			haveAwsId = true;
		} else if (p.search(/^Signature=/) != -1) {
			pairs.splice(i, 1);
			i--;
			nPairs--;
		} else if (p.search(/^AssociateTag=/) != -1) {
			pairs.splice(i, 1, "AssociateTag=" + associateTag);
			haveTag = true;
		}
		i++;
	}

	if (!haveTimestamp) {
		pairs.push("Timestamp=" + getNowTimeStamp());
	}

	if (!haveAwsId) {
		pairs.push("AWSAccessKeyId=" + accessKeyId);
	}
	
	if (!haveTag) {
		pairs.push("AssociateTag=" + associateTag);
	}

	return pairs;
}
      
function sign(secret, message) {
	var messageBytes = str2binb(message);
	var secretBytes = str2binb(secret);
        
	if (secretBytes.length > 16) {
		secretBytes = core_sha256(secretBytes, secret.length * chrsz);
	}
        
	var ipad = Array(16), opad = Array(16);
	for (var i = 0; i < 16; i++) { 
		ipad[i] = secretBytes[i] ^ 0x36363636;
		opad[i] = secretBytes[i] ^ 0x5C5C5C5C;
	}

	var imsg = ipad.concat(messageBytes);
	var ihash = core_sha256(imsg, 512 + message.length * chrsz);
	var omsg = opad.concat(ihash);
	var ohash = core_sha256(omsg, 512 + 256);
	var b64hash = binb2b64(ohash);
	var urlhash = encodeURIComponent(b64hash);
	
	return urlhash;
}
      
Date.prototype.toISODate =
	new Function("with (this)\n    return " +
		"getFullYear()+'-'+addZero(getMonth()+1)+'-'" +
		"+addZero(getDate())+'T'+addZero(getHours())+':'" +
		"+addZero(getMinutes())+':'+addZero(getSeconds())+'.000Z'");

function addZero(n) {
	return ( n < 0 || n > 9 ? "" : "0" ) + n;
}

function getNowTimeStamp() {
	var time = new Date();
	var gmtTime = new Date(time.getTime() + (time.getTimezoneOffset() * 60000));
	return gmtTime.toISODate() ;
}

function getAccessKeyId() {
	return document.getElementById('AWSAccessKeyId').value;
}
      
function getSecretAccessKey() {
	return document.getElementById('AWSSecretAccessKey').value;        
}

function getAssociateTag() {
	return document.getElementById('AssociateTag').value;
}

function getLocale() {
	return document.getElementById('locale_selector').value;
}

function getISBN() {
	return document.getElementById("ISBN").value;
}

function is_numeric(str){
    return /^\d+$/.test(str);
}
