const express = require("express");
const https = require("https");
const bodyParser = require("body-parser");
const dataService = require("./db/dataService");
const spotify = require("./plugins/spotify");
const skmeans = require("skmeans");
const listManager = require("./plugins/listManager");

const app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const client_id = "5a7933f0c4da42ae8a33a0041257cbfd";

app.get("/status", (req, res) => {
	res.send("<h1>Server is running<h1>");
});

function printReq(req) {
	console.log("Request body: " + JSON.stringify(req.body));
	console.log("Request headers: " + JSON.stringify(req.headers));
	if (req.query) console.log("Request query: " + JSON.stringify(req.query));
}

app
	.route("/party")
	.put(async (req, res) => {
		console.log("put request on /party");
		let partyName = req.body.name;
		let userId = req.body.userId;
		let token = req.body.token;
		if (!partyName || !userId || !token) {
			res.status = 400;
			res.statusMessage = "Bad Request";
			// console.log(req.body);
			res.send();
		}
		try {
			let partyId = await dataService.createParty(partyName, userId, token);
			let responseData = {};
			responseData.partyId = partyId;
			res.send(JSON.stringify(responseData));
		} catch (e) {
			let status = e.status ? e.status : 500;
			res.status(status).send(e.message);
		}
	})
	.get((req, res) => {
		console.log("get request on /party");
		let partyIds = req.query.ids;
		let info = dataService.getInfo(partyIds);
		if (!info.length) res.status(404).send("Party not found.");
		else res.status(200).send(JSON.stringify(info));
	});

app.get("/search", async (req, res) => {
	console.log("get request on /search");
	let key = req.query.key;
	let id = req.query.partyId;
	let token = dataService.getToken(id);
	let tracks = await spotify.searchTrack(key, token);
	if (tracks.status) res.status(track.status).send(track.statusMessage);
	res.send(tracks);
});

app
	.route("/playlist")
	.post(async (req, res) => {
		console.log("post request on /playlist");
		let partyId = req.body.partyId;
		let trackUri = req.body.trackUri;
		// console.log(req.body);
		let playlistId = dataService.getPlaylistId(partyId);
		let token = dataService.getToken(partyId);
		let result = await spotify.addTrackToPlaylist(playlistId, token, trackUri);
		if (result.status != 200 && result.status != 201)
			res
				.status(result.status)
				.send("Couldn't post the song on the Playlist " + result.statusMessage);

		let data = await spotify.getPlaylistItems(playlistId, token);
		// console.log(data);
		if (data.status)
			res
				.status(data.status)
				.send("Couldn't get songs from playlist: " + data.statusMessage);
		if (data.length < 4) return res.status(200).send();

		let rearrangeResult = await listManager.rearrangePlaylist(
			playlistId,
			token,
			data
		);
		// console.log(rearrangeResult);
		res.status(rearrangeResult.status).send(rearrangeResult.message);
	})
	.get(async (req, res) => {
		console.log("get request on /playlist");
		let partyId = req.query.partyId;
		let playlistId = dataService.getPlaylistId(partyId);
		let token = dataService.getToken(partyId);
		let data = await spotify.getPlaylistItems(playlistId, token);
		if (data.status) res.status(data.status).send(data.statusMessage);
		else res.status(200).send(data);
	});

app.listen(8124, () => {
	console.log("server started on port 8124");
});

module.exports = app;
