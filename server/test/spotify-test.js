const server = require("./../server");
const chai = require("chai");
const chaiHttp = require("chai-http");
const should = chai.should();
const assert = chai.assert;
const removeDuplicates = require("../plugins/listManager").removeDuplicates;
const party = require("../db/party");

chai.use(chaiHttp);

let token =
	"BQAq3un_pbRIu9m0wp6YsF_qgfBeysiVeqSMtcma0byxciB26Xxr7aXrZPeNSuGJw96Hmstrs8-KEPU9PoUhD2wkNilgLmQJcKo-SAKd36RTzqIeuei-dHx4_JD5CQARtZP7EtlhKdEbzv5_QXjCsXKTmt5UOfkywSX2YYKaXwf-W_UJjKDpBYtVeI8BmgsHUZjvhaQ";

describe("/GET status", () => {
	it("it should create a new party", async () => {
		const res = await chai.request(server).get("/status");
		if (res.status != 200)
			console.log("Error, response status: " + JSON.stringify(res.text));
		res.should.have.status(200);
	});
});

describe("/PUT party", () => {
	it("it should create a new party", async () => {
		let toSend = {
			name: "Party di Lore",
			userId: "plsdontstopthemusic",
			token: token,
		};
		const res = await chai.request(server).put("/party").send(toSend);

		if (res.status != 200 && res.status != 201) console.log(res.text);
		res.should.have.status(200);
	});
});

describe("/GET party", () => {
	it("it should get the info about the party", async () => {
		const res = await chai
			.request(server)
			.get("/party")
			.query({ ids: [party.startCounter] });
		if (res.status != 200) console.log(res.text);
		res.should.have.status(200);
	});
});

describe("/GET party", () => {
	it("it should get a 404 because there is no party", async () => {
		const res = await chai
			.request(server)
			.get("/party")
			.query({ ids: [2] });
		if (res.status != 404) console.log(res.text);
		res.should.have.status(404);
	});
});

describe("/GET track", () => {
	it("it should get the search elements", async () => {
		const res = await chai
			.request(server)
			.get("/search")
			.query({ key: "Can't Hold Us", partyId: party.startCounter });
		if (res.status != 200) console.log(res.text);
		res.should.have.status(200);
		assert.equal(res.body[0].name, "Can't Hold Us (feat. Ray Dalton)");
		track = res.body[0];
	});
});

describe("/POST playlist", () => {
	it("it should add a song to the playlist", async () => {
		let toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:31qgVdvSqTQ7unwQQngycB", //Can't Hold Us
		};
		const res = await chai.request(server).post("/playlist").send(toSend);

		if (res.status != 200) console.log(res.text);
		res.should.have.status(200);
	});
});

describe("/GET playlist", () => {
	it("it should get the songs of the playlist", async () => {
		const res = await chai
			.request(server)
			.get("/playlist")
			.query({ partyId: party.startCounter });

		if (res.status != 200) console.log(res.text);
		res.should.have.status(200);
		assert.equal(res.body[0].track.name, "Can't Hold Us (feat. Ray Dalton)");
	});
});

describe("/PUT playlist", () => {
	it("it should add a group of songs to the playlist and rearrange the playlist", async () => {
		let toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:05RgAMGypEvqhNs5hPCbMS", //Panama
		};
		let res = await chai.request(server).post("/playlist").send(toSend);

		toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:1bVlFkO4UyfNYWurqCghT7", //Follaton Wood
		};
		res = await chai.request(server).post("/playlist").send(toSend);

		toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:4YMqbFcDIFiCBd02PzUBcM", //Thrift Shop
		};
		res = await chai.request(server).post("/playlist").send(toSend);

		toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:5AeoHJUx0PJXAzN425xryh", //Dandelion Wine
		};
		res = await chai.request(server).post("/playlist").send(toSend);

		toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:33ZjZqqFuGRTPjNVqO0h8o", //Nothing Else
		};
		res = await chai.request(server).post("/playlist").send(toSend);

		toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:4p1y6JyyuirS0y6YE4ppyS", //Tom Cat Blues
		};
		res = await chai.request(server).post("/playlist").send(toSend);

		toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:0b36Qy1tiyJGv3rPaQTplR", //Sold It To The Devil
		};
		res = await chai.request(server).post("/playlist").send(toSend);

		toSend = {
			partyId: party.startCounter,
			trackUri: "spotify:track:4N0TP4Rmj6QQezWV88ARNJ", //Superstition
		};
		res = await chai.request(server).post("/playlist").send(toSend);

		if (res.status != 201) console.log(res.text);
		res.should.have.status(201);
	}).timeout(10000);
});

// describe("remove_duplicates", () => {
// 	it("should remove the duplicates from the array", () => {
// 		array = ["John", "Beppe", "Jack", "Beppe"];
// 		expected = ["John", "Beppe", "Jack"];

// 		actual = removeDuplicates(array);
// 		// console.log(actual);
// 		assert.equal(actual.length, expected.length);
// 	});
// });
