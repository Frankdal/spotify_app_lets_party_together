const https = require("https");
const http = require("http");
const dataService = require("../db/dataService");
const querystring = require("querystring");
const { put } = require("../server");
const Party = require(".././db/party").Party;

const clientId = "5a7933f0c4da42ae8a33a0041257cbfd";
const clientSecretKey = "bace58c889a849bfb9fc11fcd4b587b3";

function getUserId(token) {
	console.log("Requesting user id to spotify.");
	const options = {
		headers: {
			Authorization: getAuthTokenHeaderValue(token),
		},
	};

	https.get("https://api.spotify.com/v1/me", options, (res) => {
		console.log(res.statusCode);
		console.log(res.statusMessage);
		res.on("data", function (data) {
			let jsonData = JSON.parse(data);
			userId = jsonData.id;
			console.log("user_id retrieved: " + userId);
		});
	});

	return userId;
}

function getAuthTokenHeaderValue(token) {
	let authValue = "Bearer " + token;
	// console.log("authValue: " + authValue);
	return authValue;
}

function getAuthHeaderValue() {
	let authValueString = clientId + ":" + clientSecretKey;
	let buff = Buffer.from(authValueString);
	let authValueBase64 = buff.toString("base64");
	let authValue = "Basic " + authValueBase64;
	return authValue;
}

function refreshToken(token) {
	console.log("Refreshing token");
	const options = {
		hostname: "accounts.spotify.com",
		path: "api/token?grant_type=refresh_token&refresh_token=" + token,
		method: "POST",
		headers: {
			Authorization: getAuthHeaderValue(),
		},
	};

	const req = https.request(options, (res) => {
		console.log(res.statusCode);
		res.on("data", function (chunk) {
			console.log(chunk);
		});
	});

	req.on("error", (error) => console.error(error));
}

async function createPlaylist(party) {
	return new Promise((resolve, reject) => {
		let name = party.name;
		let userId = party.creatorUserId;
		let token = party.token;

		// console.log("--- Creating party's playlist---");
		const options = {
			method: "POST",
			headers: {
				Authorization: getAuthTokenHeaderValue(token),
				"Content-Type": "application/json",
			},
		};

		let body = {};
		body.name = name;
		body.public = false;
		body.collaborative = false;
		body.description = "";

		let url = "https://api.spotify.com/v1/users/" + userId + "/playlists";

		let req = https.request(url, options, (res) => {
			// console.log(userId);
			// console.log("\tcreate playlist request:" + res.statusCode);
			// console.log("\tcreate playlist request:" + res.statusMessage);

			let result = "";
			res.on("data", (data) => {
				result += data;
			});

			res.on("end", () => {
				let jsonData = JSON.parse(result.toString());
				// console.log(jsonData);
				party.playlistId = jsonData.id;
				// console.log("\tplaylist id:" + jsonData.id);
				resolve({
					statusCode: res.statusCode,
					statusMessage: res.statusMessage,
				});
			});
		});

		req.write(JSON.stringify(body));
		req.end();
	});
}

function addTrackToPlaylist(playlistId, token, trackUri) {
	return new Promise(async (resolve, reject) => {
		const options = {
			method: "POST",
			headers: {
				Authorization: getAuthTokenHeaderValue(token),
				"Content-Type": "application/json",
			},
		};

		let url =
			"https://api.spotify.com/v1/playlists/" +
			playlistId +
			"/tracks?uris=" +
			trackUri;

		let request = https.request(url, options, (res) => {
			// console.log("\tadd track to playlist status:" + res.statusCode);
			// console.log("\tadd track to playlist status:" + res.statusMessage);

			res.on("data", (data) => {
				// console.log(data.toString());
				resolve({ status: res.statusCode, statusMessage: res.statusMessage });
			});
		});
		request.end();
	});
}

function getPromiseSearchTrack(key, token) {
	return new Promise((resolve, reject) => {
		const options = {
			headers: {
				Authorization: getAuthTokenHeaderValue(token),
			},
		};
		let queryKey = querystring.stringify({ q: key });
		// console.log("key: " + key);
		let url =
			"https://api.spotify.com/v1/search?" +
			queryKey +
			"&type=track&market=from_token&limit=10";
		// console.log("url search: " + url);
		let tracks;
		https.get(url, options, (res) => {
			let result = "";
			res.on("data", (chunk) => {
				result += chunk;
			});
			res.on("end", () => {
				let jsonData = JSON.parse(result);
				tracks = jsonData.tracks.items;
				// console.log("res body");
				// console.log(tracks);
				if (res.statusCode != 200)
					resolve({ status: res.statusCode, statusMessage: res.statusMessage });
				resolve(tracks);
			});
		});
	});
}

async function searchTrack(key, token) {
	let result;

	try {
		let https_promise = getPromiseSearchTrack(key, token);
		let response_body = await https_promise;
		result = response_body;
		return result;
	} catch (error) {
		// Promise rejected
		console.log(error);
	}
}

function getPromisePlaylistItems(playlistId, token) {
	return new Promise((resolve, reject) => {
		const options = {
			headers: {
				Authorization: getAuthTokenHeaderValue(token),
			},
		};

		let url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";

		https.get(url, options, (res) => {
			let result = "";
			res.on("data", (chunk) => {
				result += chunk;
			});

			res.on("end", () => {
				let jsonData = JSON.parse(result.toString());
				// console.log(jsonData);
				if (res.statusCode != 200)
					resolve({ status: res.statusCode, statusMessage: res.statusMessage });
				else resolve(jsonData.items);
			});
		});
	});
}

async function getPlaylistItems(playlistId, token) {
	try {
		return await getPromisePlaylistItems(playlistId, token);
	} catch (error) {
		// Promise rejected
		console.log(error);
	}
}

async function getFeaturesOfTracks(tracksIds, token) {
	return new Promise((resolve, reject) => {
		const options = {
			headers: {
				Authorization: getAuthTokenHeaderValue(token),
			},
		};
		let url =
			"https://api.spotify.com/v1/audio-features/?ids=" + tracksIds.join();
		// console.log("url search: " + url);
		let features;
		https.get(url, options, (res) => {
			let result = "";
			res.on("data", (chunk) => {
				result += chunk;
			});
			res.on("end", () => {
				let jsonData = JSON.parse(result);
				features = jsonData;
				if (res.statusCode != 200)
					resolve({ status: res.statusCode, statusMessage: res.statusMessage });
				resolve(features);
			});
		});
	});
}

async function replacePlaylistSongs(playlistId, tracksUri, token) {
	return new Promise((resolve, reject) => {
		const options = {
			method: "PUT",
			headers: {
				Authorization: getAuthTokenHeaderValue(token),
				"Content-Type": "application/json",
			},
		};
		let url =
			"https://api.spotify.com/v1/playlists/" +
			playlistId +
			"/tracks?uris=" +
			tracksUri.join();

		let req = https.request(url, options, (res) => {
			let result = "";
			res.on("data", (chunk) => {
				result += chunk;
			});

			res.on("end", () => {
				resolve({ status: res.statusCode, statusMessage: res.statusMessage });
			});
		});

		req.end();
	});
}

module.exports = {
	getUserId,
	createPlaylist,
	searchTrack,
	addTrackToPlaylist,
	getPlaylistItems,
	getFeaturesOfTracks,
	replacePlaylistSongs,
};
