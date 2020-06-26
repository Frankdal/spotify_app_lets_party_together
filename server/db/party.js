const startCounter = 6453;
let counter = startCounter;

class Party {
	constructor(name, creatorUserId, token) {
		if (!creatorUserId || !name || !token) {
			throw new Error("Party constructor: a param or more wasn't supplied");
		}
		this.creatorUserId = creatorUserId;
		this.name = name;
		this.id = counter++;
		this.token = token;
		this.playlistId;
	}
}

module.exports = {
	Party,
	startCounter,
};
